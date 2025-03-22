package presentation;

import application.WithdrawalCommand;

import java.util.Objects;

/**
 * дочерний класс parseCommand
 * принемает на вход строку и если указано команда "withdrawal" - создание счета передается экземпляру класса withdrawalCommand
 */
public class WithdrawalParse extends ParseCommand {


    public WithdrawalParse(ParseCommand nextParseCommand) {
        super(nextParseCommand);
    }

    @Override
    public String parse(String command) throws Exception {
        String[] commandPars = command.split(" ");
        if(Objects.equals(commandPars[0], "withdrawal") && commandPars.length == 4){
            var withdrawalCommand = new WithdrawalCommand(commandPars[1], commandPars[2], Double.parseDouble(commandPars[3]));
            withdrawalCommand.execute();
        }
        return super.parse(command);
    }

}
