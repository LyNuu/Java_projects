package application;

import dataBase.Service;
import models.User;

import java.math.BigDecimal;

/**
 * Команда для снятия средств с баланса пользователя.
 * <p>
 * Реализует интерфейс {@link Command} и выполняет операцию снятия средств
 * с баланса пользователя в базе данных. Использует сервис {@link Service} для взаимодействия
 * с базой данных и модель {@link User} для хранения данных пользователя.
 * </p>
 *
 * <p><b>Пример использования:</b>
 * <pre>{@code
 * command withdrawalCommand = new withdrawalCommand("username", "password", 50.0);
 * withdrawalCommand.Execute();
 * }</pre>
 *
 * @see Command
 * @see Service
 * @see User
 */
public class WithdrawalCommand implements Command {

    private final double amount;

    private final User user;

    public WithdrawalCommand(String name, String password, double amount) {
        this.user = new User(0, password, name, new BigDecimal("0.0") );
        this.amount = amount;
    }
    @Override
    public void execute() throws Exception {

        var service = new Service();
        service.withdrawalofAccount(user, amount);
    }
}
