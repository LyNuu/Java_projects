package presentation;

import application.viewBalance;

import java.sql.SQLException;
import java.util.Objects;

/**
 * дочерний класс parseCommand
 * принемает на вход строку и если указано команда "view_balance" - просмотр баланса счета передается экземпляру класса viewBalance
 */
public class viewBalanceParse extends parseCommand{

    public viewBalanceParse(parseCommand nextParseCommand){
        super(nextParseCommand);
    }

    @Override
    public String Parse(String command) throws SQLException,Exception {
        String[] commandPars = command.split(" ");
        if(Objects.equals(commandPars[0], "view_balance") && commandPars.length == 3){

            var view = new viewBalance(commandPars[1],commandPars[2]);
            view.Execute();
        }
        return super.Parse(command);
    }
}
