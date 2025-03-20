package presentation;

import application.withdrawalCommand;

import java.sql.SQLException;
import java.util.Objects;

/**
 * дочерний класс parseCommand
 * принемает на вход строку и если указано команда "withdrawal" - создание счета передается экземпляру класса withdrawalCommand
 */
public class withdrawalParse extends parseCommand {


    public withdrawalParse(parseCommand nextParseCommand){
        super(nextParseCommand);
    }

    @Override
    public String Parse(String command) throws SQLException, Exception {

        String[] commandPars = command.split(" ");
        if(Objects.equals(commandPars[0], "withdrawal") && commandPars.length == 4){

            var withdrawalCommand = new withdrawalCommand(commandPars[1], commandPars[2], Double.parseDouble(commandPars[3]));
            withdrawalCommand.Execute();

        }
        return super.Parse(command);
    }

}
