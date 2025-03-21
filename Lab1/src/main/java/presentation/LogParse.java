package presentation;
import application.LogCommand;
import java.util.Objects;

/**
 * дочерний класс parseCommand
 * принемает на вход строку и если указано команда "log" - создание log передается экземпляру класса logCommand
 */

public class LogParse extends ParseCommand {

    public LogParse(ParseCommand nextParseCommand) {
        super(nextParseCommand);
    }

    @Override
    public String parse(String command) throws Exception {
        String[] commandPars = command.split(" ");
        if(Objects.equals(commandPars[0], "log") && commandPars.length == 2){
            var log = new LogCommand(commandPars[1]);
            log.execute();
        }
        return super.parse(command);
    }
}
