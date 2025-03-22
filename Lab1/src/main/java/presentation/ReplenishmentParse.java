package presentation;

import application.ReplenishmentCommand;
import java.util.Objects;

/**
 * дочерний класс parseCommand
 * принемает на вход строку и если указано команда "replenishment" - пополнение счета передается экземпляру класса replenishmentCommand
 */
public class ReplenishmentParse extends ParseCommand {

    public ReplenishmentParse(ParseCommand nextParseCommand) {
        super(nextParseCommand);
    }

    @Override
    public String parse(String command) throws Exception {
        String[] commandPars = command.split(" ");
        if(Objects.equals(commandPars[0], "replenishment") && commandPars.length == 4){
            var replenishment = new ReplenishmentCommand(commandPars[1], commandPars[2], Double.parseDouble(commandPars[3]));
            replenishment.execute();
        }
        return super.parse(command);
    }
}
