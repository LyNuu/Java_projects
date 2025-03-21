package application;

import dataBase.Service;

/**
 * Команда для просмотра истории операций пользователя.
 * <p>
 * Реализует интерфейс {@link Command} и выполняет операцию получения и отображения
 * истории операций пользователя из базы данных. Использует сервис {@link Service}
 * для взаимодействия с базой данных.
 * </p>
 *
 * <p><b>Пример использования:</b>
 * <pre>{@code
 * command logCommand = new logCommand("username");
 * logCommand.Execute();
 * }</pre>
 *
 * @see Command
 * @see Service
 */
public class LogCommand implements Command {

    private final String username;

    public LogCommand(String username){
        this.username = username;
    }

    @Override
    public void execute() throws Exception {

        var service = new Service();
        service.viewHistory(username);
    }
}
