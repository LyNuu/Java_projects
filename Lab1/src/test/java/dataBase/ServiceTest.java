package dataBase;

import models.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class ServiceTest {

    final String url = "jdbc:postgresql://localhost:5432/ATM_DETAILS";
    final String user = "postgres";
    final String password = "mysecretpassword";

    @BeforeEach
    void Drop() throws SQLException {

        String sql = "DROP TABLE IF EXISTS users;";

        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();

        statement.execute(sql);
    }

    @BeforeEach
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

    @Test
    @DisplayName("create account")
    void createDuplicateUser_ThrowsNoError() throws SQLException, Exception{

        var user = new User(1,"BOB","123", 0.0);
        var service = new service();
        service.CreateAccount(user);
        Assertions.assertThrows(SQLException.class, () -> service.CreateAccount(user));
    }

    @Test
    @DisplayName("view balance account")
    void view_balance_with_error_info() throws SQLException, Exception{

        var user = new User(1,"BOB","123", 0.0);
        var service = new service();
        Assertions.assertThrows(SQLException.class, () -> service.ViewBalance(user));
    }

    @Test
    @DisplayName("withdrawal negative balance")
    void withdrawal_negative_balance() throws SQLException, Exception{

        var user = new User(1,"BOB","123", 0.0);
        var service = new service();
        service.CreateAccount(user);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.Withdrawal_of_Account(user, -55));
    }

    @Test
    @DisplayName("replenishment with error info")
    void replenishment_with_error_info() throws SQLException, Exception{

        var user = new User(1,"BOB","123", 0.0);
        var service = new service();
        Assertions.assertThrows(SQLException.class, () -> service.Replenishment_of_Account(user, 55));
    }

    @Test
    @DisplayName("view history account")
    void view_history_account() throws SQLException, Exception{

        var user = new User(1,"BOB","123", 0.0);
        var user2 = new User(1,"BO","123", 0.0);
        var service = new service();
        service.CreateAccount(user);
        Assertions.assertThrows(Exception.class, () -> service.ViewHistory(user2.name()));
    }


}