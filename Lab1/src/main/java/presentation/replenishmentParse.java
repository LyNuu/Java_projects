package presentation;

import application.replenishmentCommand;
import application.viewBalance;

import java.sql.SQLException;
import java.util.Objects;

/**
 * дочерний класс parseCommand
 * принемает на вход строку и если указано команда "replenishment" - пополнение счета передается экземпляру класса replenishmentCommand
 */
public class replenishmentParse extends parseCommand{

    public replenishmentParse(parseCommand nextParseCommand){
        super(nextParseCommand);
    }

    @Override
    public String Parse(String command) throws SQLException , Exception{

        String[] commandPars = command.split(" ");
        if(Objects.equals(commandPars[0], "replenishment") && commandPars.length == 4){

            var replenishment = new replenishmentCommand(commandPars[1], commandPars[2], Double.parseDouble(commandPars[3]));
            replenishment.Execute();
        }
        return super.Parse(command);
    }
}
