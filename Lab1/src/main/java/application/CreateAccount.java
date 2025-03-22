package application;

import dataBase.Service;
import models.User;

import java.math.BigDecimal;

/**
 * Команда для создания нового пользовательского аккаунта.
 * <p>
 * Реализует интерфейс {@link Command} и выполняет операцию создания аккаунта
 * в базе данных. Использует сервис {@link Service} для взаимодействия с базой данных.
 * </p>
 *
 * <p><b>Пример использования:</b>
 * <pre>{@code
 * command createCommand = new createAccount(1, "username", "password");
 * createCommand.Execute();
 * }</pre>
 *
 * @see Command
 * @see Service
 * @see User
 */
public class CreateAccount implements Command {

    private final User user;
    public CreateAccount(int id, String name, String password){
        this.user = new User(id, name, password, new BigDecimal("0.0"));
    }
    @Override
    public void execute() throws Exception {

        var service = new Service();
        service.createAccount(user);
    }
}
