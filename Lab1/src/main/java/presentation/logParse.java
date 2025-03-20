package presentation;

import application.logCommand;

import java.sql.SQLException;
import java.util.Objects;

/**
 * дочерний класс parseCommand
 * принемает на вход строку и если указано команда "log" - создание log передается экземпляру класса logCommand
 */

public class logParse extends parseCommand{

    public logParse(parseCommand nextParseCommand){
        super(nextParseCommand);
    }

    @Override
    public String Parse(String command) throws Exception, SQLException{

        String[] commandPars = command.split(" ");
        if(Objects.equals(commandPars[0], "log") && commandPars.length == 2){

            var log = new logCommand(commandPars[1]);
            log.Execute();
        }
        return super.Parse(command);
    }
}
