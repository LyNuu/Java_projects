package application;

import dataBase.service;
import models.User;

import java.sql.SQLException;
/**
 * Команда для просмотра баланса пользователя.
 * <p>
 * Реализует интерфейс {@link command} и выполняет операцию получения текущего баланса
 * пользователя из базы данных. Использует сервис {@link service} для взаимодействия
 * с базой данных и модель {@link User} для хранения данных пользователя.
 * </p>
 *
 * <p><b>Пример использования:</b>
 * <pre>{@code
 * command viewBalanceCommand = new viewBalance("username", "password");
 * viewBalanceCommand.Execute();
 * }</pre>
 *
 * @see command
 * @see service
 * @see User
 */
public class viewBalance implements command{

    User user;
    public viewBalance(String name, String password){
        this.user = new User(0,name, password,0);
    }
    @Override
    public void Execute() throws SQLException, Exception {


        var service = new service();
        service.ViewBalance(user);
    }
}
