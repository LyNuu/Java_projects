package application;

import dataBase.service;
import models.User;

import java.sql.SQLException;
/**
 * Команда для снятия средств с баланса пользователя.
 * <p>
 * Реализует интерфейс {@link command} и выполняет операцию снятия средств
 * с баланса пользователя в базе данных. Использует сервис {@link service} для взаимодействия
 * с базой данных и модель {@link User} для хранения данных пользователя.
 * </p>
 *
 * <p><b>Пример использования:</b>
 * <pre>{@code
 * command withdrawalCommand = new withdrawalCommand("username", "password", 50.0);
 * withdrawalCommand.Execute();
 * }</pre>
 *
 * @see command
 * @see service
 * @see User
 */
public class withdrawalCommand implements command{

    private final double _amount;
    User user;
    public withdrawalCommand(String name, String password, double amount){
        this.user = new User(0,password,name,0 );
        _amount = amount;
    }
    @Override
    public void Execute() throws SQLException, Exception {

        var service = new service();
        service.Withdrawal_of_Account(user, _amount);
    }
}
