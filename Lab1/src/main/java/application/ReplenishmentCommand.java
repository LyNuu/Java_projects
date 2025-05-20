package application;

import dataBase.Service;
import models.User;

import java.math.BigDecimal;

/**
 * Команда для пополнения баланса пользователя.
 * <p>
 * Реализует интерфейс {@link Command} и выполняет операцию пополнения баланса
 * пользователя в базе данных. Использует сервис {@link Service} для взаимодействия
 * с базой данных и модель {@link User} для хранения данных пользователя.
 * </p>
 *
 * <p><b>Пример использования:</b>
 * <pre>{@code
 * command replenishmentCommand = new replenishmentCommand("username", "password", 100.0);
 * replenishmentCommand.Execute();
 * }</pre>
 *
 * @see Command
 * @see Service
 * @see User
 */
public class ReplenishmentCommand implements Command {

    private final double amount;

    private final User user;

    public ReplenishmentCommand(String name, String password, double amount) {
        this.user = new User(0, name, password, new BigDecimal("0.0") );
        this.amount = amount;
    }
    @Override
    public void execute() throws Exception {

        var service = new Service();
        service.replenishmentofAccount(user, amount);
    }
}
