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
import java.sql.Statement;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceTest {

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
        service = new Service(dataSource);
    }

     static DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(postgres.getJdbcUrl());
        dataSource.setUser(postgres.getUsername());
        dataSource.setPassword(postgres.getPassword());
        return dataSource;
    }

    @BeforeEach
     void drop() throws Exception {

        String sql = "DROP TABLE IF EXISTS users;";
        Connection connection = dataSource.getConnection();
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

        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    @Test
    @DisplayName("create account")
    void createDuplicateUserThrowsNoError() throws Exception{

        var user = new User(1,"BOB","123", new BigDecimal("0.0"));
        var service = new Service(dataSource);
        service.createAccount(user);
        Assertions.assertThrows(Exception.class, () -> service.createAccount(user));
    }

    @Test
    @DisplayName("view balance account")
    void viewbalancewitherrorInfo() throws Exception{

        var user = new User(1,"BOB","123", new BigDecimal("0.0"));
        var service = new Service(dataSource);
        Assertions.assertThrows(Exception.class, () -> service.viewBalance(user));
    }

    @Test
    @DisplayName("withdrawal negative balance")
    void withdrawalNegativeBalance() throws Exception{

        var user = new User(1,"BOB","123", new BigDecimal("0.0"));
        var service = new Service(dataSource);
        service.createAccount(user);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.withdrawalofAccount(user, -55));
    }

    @Test
    @DisplayName("replenishment with error info")
    void replenishmentWitherrorInfo() throws Exception{

        var user = new User(1,"BOB","123", new BigDecimal("0.0"));
        var service = new Service();
        Assertions.assertThrows(Exception.class, () -> service.replenishmentofAccount(user, 55));
    }

    @Test
    @DisplayName("view history account")
    void viewhistoryAccount() throws Exception{

        var user = new User(1,"BOB","123", new BigDecimal("0.0"));
        var user2 = new User(1,"BO","123", new BigDecimal("0.0"));
        var service = new Service(dataSource);
        service.createAccount(user);
        Assertions.assertThrows(Exception.class, () -> service.viewHistory(user2.name()));
    }


}