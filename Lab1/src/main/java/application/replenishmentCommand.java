package application;

import dataBase.service;
import models.User;

import java.sql.SQLException;
/**
 * Команда для пополнения баланса пользователя.
 * <p>
 * Реализует интерфейс {@link command} и выполняет операцию пополнения баланса
 * пользователя в базе данных. Использует сервис {@link service} для взаимодействия
 * с базой данных и модель {@link User} для хранения данных пользователя.
 * </p>
 *
 * <p><b>Пример использования:</b>
 * <pre>{@code
 * command replenishmentCommand = new replenishmentCommand("username", "password", 100.0);
 * replenishmentCommand.Execute();
 * }</pre>
 *
 * @see command
 * @see service
 * @see User
 */
public class replenishmentCommand implements command{

    private final double _amount;
    User user;
    public replenishmentCommand(String name, String password, double amount){
        this.user = new User(0,name,password,0 );
        _amount = amount;
    }
    @Override
    public void Execute() throws SQLException, Exception {

        var service = new service();
        service.Replenishment_of_Account(user, _amount);
    }
}
