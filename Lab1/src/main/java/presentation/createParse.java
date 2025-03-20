package presentation;

import application.createAccount;

import java.sql.SQLException;
import java.util.Objects;

/**
 * дочерний класс parseCommand
 * принемает на вход строку и если указано команда "create" - создание счета передается экземпляру класса createAccount
 */

public class createParse extends parseCommand{

    public createParse(parseCommand nextCommand){
        super(nextCommand);
    }

    @Override
    public String Parse(String command) throws SQLException, Exception {
        String[] commandPars = command.split(" ");
        if(Objects.equals(commandPars[0], "create") && commandPars.length == 3){
            var createCommand = new createAccount(0, commandPars[1],commandPars[2]);
            createCommand.Execute();
        }
        return super.Parse(command);
    }
}
