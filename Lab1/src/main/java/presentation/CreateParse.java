package presentation;
import application.CreateAccount;
import java.util.Objects;

/**
 * дочерний класс parseCommand
 * принемает на вход строку и если указано команда "create" - создание счета передается экземпляру класса createAccount
 */

public class CreateParse extends ParseCommand {

    public CreateParse(ParseCommand nextCommand){
        super(nextCommand);
    }

    @Override
    public String parse(String command) throws Exception {
        String[] commandPars = command.split(" ");
        if(Objects.equals(commandPars[0], "create") && commandPars.length == 3){
            var createCommand = new CreateAccount(0, commandPars[1],commandPars[2]);
            createCommand.execute();
        }
        return super.parse(command);
    }
}
