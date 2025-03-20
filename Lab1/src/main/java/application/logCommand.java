package application;

import dataBase.service;

import java.sql.SQLException;
/**
 * Команда для просмотра истории операций пользователя.
 * <p>
 * Реализует интерфейс {@link command} и выполняет операцию получения и отображения
 * истории операций пользователя из базы данных. Использует сервис {@link service}
 * для взаимодействия с базой данных.
 * </p>
 *
 * <p><b>Пример использования:</b>
 * <pre>{@code
 * command logCommand = new logCommand("username");
 * logCommand.Execute();
 * }</pre>
 *
 * @see command
 * @see service
 */
public class logCommand implements command{

    String username;
    public logCommand(String username){
        this.username = username;
    }
    @Override
    public void Execute() throws Exception, SQLException{
        var service = new service();
        service.ViewHistory(username);
    }
}
