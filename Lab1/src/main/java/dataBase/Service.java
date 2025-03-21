package dataBase;

import models.User;

import javax.sql.DataSource;
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
 * @see OperationType
 */

public class Service {

    private final DataSource dataSource;

    public Service(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Service(){
        dataSource = null;
    }

    final String url = "jdbc:postgresql://localhost:5432/ATM_DETAILS";
    final String username = "postgres";
    final String password = "mysecretpassword";

    private final static Map<String, List<Logging>> userActivities = new HashMap<>();



    public void init() throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS Users (
                    id SERIAL PRIMARY KEY,
                    username VARCHAR(50) NOT NULL,
                    password VARCHAR(50) NOT NULL,
                    count_money NUMERIC(15,2) DEFAULT 0.0
                );
                """;

        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        statement.execute(sql);
        connection.close();
        statement.close();
    }

    public void drop() throws Exception {

        String sql = "DROP TABLE IF EXISTS users;";
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        statement.execute(sql);
        connection.close();
        statement.close();
    }

    public void createAccount(User user) throws Exception {

        String checkSql = "SELECT username FROM users WHERE username = ? AND password = ?";

        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement checkStmt = connection.prepareStatement(checkSql);
        checkStmt.setString(1, user.name());
        checkStmt.setString(2, user.password());
        ResultSet resultSet = checkStmt.executeQuery();
            if (resultSet.next()) {
                throw new Exception();
            }
        String sql = "INSERT INTO users (username, password, count_money) VALUES (?,?,?);";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.name());
        statement.setString(2, user.password());
        statement.setBigDecimal(3, user.countMoney());
        statement.executeUpdate();
        userActivities.put(user.name(), Collections.singletonList(new Logging(OperationType.CREATE, LocalDateTime.now(), user.countMoney())));
        connection.close();
        statement.close();
    }

    public void viewBalance(User user) throws Exception {

        String sql = "SELECT count_money FROM users WHERE username = ? AND password = ?";
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.name());
        statement.setString(2, user.password());
        ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    BigDecimal balance = resultSet.getBigDecimal("count_money");
                    System.out.println("Баланс: " + balance.setScale(2, RoundingMode.HALF_UP));
                    userActivities.computeIfAbsent(user.name(), k -> new ArrayList<>()).add(new Logging(OperationType.VIEW, LocalDateTime.now(), user.countMoney()));
                } else {
                    throw new Exception();
                }
    }

    public void withdrawal_of_Account(User user, double amount) throws Exception {

        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма снятия должна быть положительной");
        }

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            connection.setAutoCommit(false);
            String authSql = "SELECT count_money FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement authStmt = connection.prepareStatement(authSql)) {
                authStmt.setString(1, user.name());
                authStmt.setString(2, user.password());

                try (ResultSet rs = authStmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Ошибка: Неверный логин или пароль");
                        return;
                    }

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
            statement.setString(2, user.name());
            statement.setString(3, user.password());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            System.out.println("Balance:" + resultSet.getDouble("count_money"));
            userActivities.put(user.name(), Collections.singletonList(new Logging(OperationType.WITHDRAW, LocalDateTime.now(), user.countMoney())));
            connection.close();
            statement.close();
        }
    }

    public void replenishment_of_Account(User user, double amount) throws Exception{

        Connection connection = DriverManager.getConnection(url, username, password);
        String authSql = "SELECT count_money FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement authStmt = connection.prepareStatement(authSql)) {
            authStmt.setString(1, user.name());
            authStmt.setString(2, user.password());

            try (ResultSet rs = authStmt.executeQuery()) {
                if (!rs.next()) {
                    throw new Exception("Ошибка: Неверный логин или пароль");
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
        statement.setString(2, user.name());
        statement.setString(3, user.password());
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        System.out.println("Balance:" + resultSet.getDouble("count_money"));
        userActivities.put(user.name(), Collections.singletonList(new Logging(OperationType.DEPOSIT, LocalDateTime.now(), user.countMoney())));
        connection.close();
        statement.close();
    }

    public void viewHistory(String username) throws Exception{
        if(!userActivities.containsKey(username)){
            throw new Exception("Пользоватеть не найден!");
        }
        System.out.println("\n===== История операций пользователя '" + username + "' =====");

        for (int i = 0; i < userActivities.get(username).size(); i++) {

            System.out.printf("%d. [%s] %s - %s%n",
                    i + 1,
                    userActivities.get(username).get(i).operationType(),
                    userActivities.get(username).get(i).timestamp(),
                    userActivities.get(username).get(i).amount());
        }
        System.out.println("=".repeat(50) + "\n");
    }
}
