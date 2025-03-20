package dataBase;

import models.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Сервис для работы с базой данных пользователей и их операциями.
 * <p>
 * Этот класс предоставляет методы для управления учетными записями пользователей,
 * выполнения операций (пополнение, снятие, просмотр баланса) и логирования действий.
 * </p>
 *
 * <p><b>Пример использования:</b>
 * <pre>{@code
 * service dbService = new service();
 * dbService.Init(); // Инициализация таблицы
 * User user = new User(1, "username", "password", 100.0);
 * dbService.CreateAccount(user); // Создание аккаунта
 * }</pre>
 *
 * @see User
 * @see logging
 */
public class service {

    final String url = "jdbc:postgresql://localhost:5432/ATM_DETAILS";
    final String user = "postgres";
    final String password = "mysecretpassword";

    private final static Map<String, List<logging>> userActivities = new HashMap<>();

    public void Init() throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS Users (
                    id SERIAL PRIMARY KEY,
                    username VARCHAR(50) NOT NULL,
                    password VARCHAR(50) NOT NULL,
                    count_money NUMERIC(15,2) DEFAULT 0.0
                );
                """;

        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();

        statement.execute(sql);
    }

    public void Drop() throws SQLException {

        String sql = "DROP TABLE IF EXISTS users;";

        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();

        statement.execute(sql);
    }

    public void CreateAccount(User _user) throws SQLException {

        String checkSql = "SELECT username FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {

            checkStmt.setString(1, _user.name());
            checkStmt.setString(2, _user.password());
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                throw new SQLException();
            }
        }
        String sql = "INSERT INTO users (username, password, count_money) VALUES (?,?,?);";

        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,_user.name());
        statement.setString(2,_user.password());
        statement.setDouble(3, (double) _user.countMoney());


        statement.executeUpdate();

        userActivities.put(_user.name(), Collections.singletonList(new logging("CREATE", LocalDateTime.now(), 0.0, "DETAILS: 123")));
    }

    public void ViewBalance(User _user) throws SQLException {

        String sql = "SELECT count_money FROM users WHERE username = ? AND password = ?";

        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, _user.name());
            statement.setString(2, _user.password());

            ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    BigDecimal balance = resultSet.getBigDecimal("count_money");
                    System.out.println("Баланс: " + balance.setScale(2, RoundingMode.HALF_UP));

                    userActivities.computeIfAbsent(_user.name(), k -> new ArrayList<>())
                            .add(new logging("VIEW", LocalDateTime.now(),
                                    balance.doubleValue(),
                                    "Просмотр баланса"));
                } else {
                    throw new SQLException();
                }
    }

    public void Withdrawal_of_Account(User _user, double amount) throws SQLException {

        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма снятия должна быть положительной");
        }

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            connection.setAutoCommit(false); // Начало транзакции

            // 1. Проверка существования пользователя и пароля
            String authSql = "SELECT count_money FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement authStmt = connection.prepareStatement(authSql)) {
                authStmt.setString(1, _user.name());
                authStmt.setString(2, _user.password());

                try (ResultSet rs = authStmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Ошибка: Неверный логин или пароль");
                        return;
                    }

                    // 2. Проверка достаточности средств
                    BigDecimal currentBalance = rs.getBigDecimal("count_money");
                    if (currentBalance.doubleValue() < amount) {
                        System.out.println("Ошибка: Недостаточно средств на счету");
                        return;
                    }
                }
            }
            String sql = """
                    UPDATE users
                    SET count_money = count_money - ?
                    WHERE
                        username = ?
                        AND password = ?
                    RETURNING count_money;
                    """;


            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDouble(1, amount);
            statement.setString(2, _user.name());
            statement.setString(3, _user.password());

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            System.out.println("Balance:" + resultSet.getDouble("count_money"));

            userActivities.put(_user.name(), Collections.singletonList(new logging("WITHDRAWAL", LocalDateTime.now(), amount, "DETAILS: 123")));


        }
    }

    public void Replenishment_of_Account(User _user, double amount) throws SQLException{
        Connection connection = DriverManager.getConnection(url, user, password);

        String authSql = "SELECT count_money FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement authStmt = connection.prepareStatement(authSql)) {
            authStmt.setString(1, _user.name());
            authStmt.setString(2, _user.password());

            try (ResultSet rs = authStmt.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Ошибка: Неверный логин или пароль");
                }
            }
        }
        String sql = """
        UPDATE users
        SET count_money = count_money + ?
        WHERE
            username = ?
            AND password = ?
        RETURNING count_money;
        """;


        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, amount);
        statement.setString(2, _user.name());
        statement.setString(3, _user.password());

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        System.out.println("Balance:" + resultSet.getDouble("count_money"));

        userActivities.put(_user.name(), Collections.singletonList(new logging("REPLENISHMENT", LocalDateTime.now(),amount , "DETAILS: 123")));



    }

    public void ViewHistory(String username) throws Exception{
        if(!userActivities.containsKey(username)){
            throw new Exception("Пользоватеть не найден!");
        }

        System.out.println("\n===== История операций пользователя '" + username + "' =====");

        for (int i = 0; i < userActivities.get(username).size(); i++) {

            System.out.printf("%d. [%s] %s - %s%n",
                    i + 1,
                    userActivities.get(username).get(i).operationType(),
                    userActivities.get(username).get(i).timestamp(),
                    userActivities.get(username).get(i).amount(),
                    userActivities.get(username).get(i).details());
        }

        System.out.println("=".repeat(50) + "\n");
    }
}
