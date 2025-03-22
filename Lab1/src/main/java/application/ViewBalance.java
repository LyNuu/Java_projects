package application;

import dataBase.Service;
import models.User;

import java.math.BigDecimal;

/**
 * Команда для просмотра баланса пользователя.
 * <p>
 * Реализует интерфейс {@link Command} и выполняет операцию получения текущего баланса
 * пользователя из базы данных. Использует сервис {@link Service} для взаимодействия
 * с базой данных и модель {@link User} для хранения данных пользователя.
 * </p>
 *
 * <p><b>Пример использования:</b>
 * <pre>{@code
 * command viewBalanceCommand = new viewBalance("username", "password");
 * viewBalanceCommand.Execute();
 * }</pre>
 *
 * @see Command
 * @see Service
 * @see User
 */
public class ViewBalance implements Command {

    private final User user;

    public ViewBalance(String name, String password){
        this.user = new User(0, name, password, new BigDecimal("0.0"));
    }

    @Override
    public void execute() throws Exception {

        var service = new Service();
        service.viewBalance(user);
    }
}
