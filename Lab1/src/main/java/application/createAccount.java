package application;

import dataBase.service;
import models.User;

import java.sql.SQLException;
/**
 * Команда для создания нового пользовательского аккаунта.
 * <p>
 * Реализует интерфейс {@link command} и выполняет операцию создания аккаунта
 * в базе данных. Использует сервис {@link service} для взаимодействия с базой данных.
 * </p>
 *
 * <p><b>Пример использования:</b>
 * <pre>{@code
 * command createCommand = new createAccount(1, "username", "password");
 * createCommand.Execute();
 * }</pre>
 *
 * @see command
 * @see service
 * @see User
 */
public class createAccount implements command {

    User user;
    public createAccount(int id, String name, String password){
        this.user = new User(id, name, password, 0.0);
    }
    @Override
    public void Execute() throws SQLException, Exception {

        var service = new service();
        service.CreateAccount(user);
    }
}
