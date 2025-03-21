package dataBase;

import models.User;
import org.junit.jupiter.api.*;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceTest {

    final String url = "jdbc:postgresql://localhost:5432/ATM_DETAILS";
    final String user = "postgres";
    final String password = "mysecretpassword";

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("ATM_DETAILS")
                    .withUsername("postgres")
                    .withPassword("mysecretpassword");

    private static DataSource dataSource;
    private static Service service;

    @BeforeAll
    static void setup() throws Exception {
        dataSource = createDataSource();
        service = new Service(dataSource); // Ошибка исчезнет после исправления Service
        //init();
    }

    private static DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(postgres.getJdbcUrl());
        dataSource.setUser(postgres.getUsername());
        dataSource.setPassword(postgres.getPassword());
        return dataSource;
    }

    @BeforeEach
     void drop() throws Exception {

        String sql = "DROP TABLE IF EXISTS users;";
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }


    @BeforeEach
    public void init() throws Exception {

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
    void createDuplicateUser_ThrowsNoError() throws Exception{

        var user = new User(1,"BOB","123", new BigDecimal("0.0"));
        var service = new Service();
        service.createAccount(user);
        Assertions.assertThrows(Exception.class, () -> service.createAccount(user));
    }

    @Test
    @DisplayName("view balance account")
    void view_balance_with_error_info() throws Exception{

        var user = new User(1,"BOB","123", new BigDecimal("0.0"));
        var service = new Service();
        Assertions.assertThrows(Exception.class, () -> service.viewBalance(user));
    }

    @Test
    @DisplayName("withdrawal negative balance")
    void withdrawal_negative_balance() throws Exception{

        var user = new User(1,"BOB","123", new BigDecimal("0.0"));
        var service = new Service();
        service.createAccount(user);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.withdrawal_of_Account(user, -55));
    }

    @Test
    @DisplayName("replenishment with error info")
    void replenishment_with_error_info() throws Exception{

        var user = new User(1,"BOB","123", new BigDecimal("0.0"));
        var service = new Service();
        Assertions.assertThrows(Exception.class, () -> service.replenishment_of_Account(user, 55));
    }

    @Test
    @DisplayName("view history account")
    void view_history_account() throws Exception{

        var user = new User(1,"BOB","123", new BigDecimal("0.0"));
        var user2 = new User(1,"BO","123", new BigDecimal("0.0"));
        var service = new Service();
        service.createAccount(user);
        Assertions.assertThrows(Exception.class, () -> service.viewHistory(user2.name()));
    }


}