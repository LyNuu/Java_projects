package presentation;
import application.ViewBalance;
import java.util.Objects;

/**
 * дочерний класс parseCommand
 * принемает на вход строку и если указано команда "view_balance" - просмотр баланса счета передается экземпляру класса viewBalance
 */
public class ViewBalanceParse extends ParseCommand {

    public ViewBalanceParse(ParseCommand nextParseCommand) {
        super(nextParseCommand);
    }

    @Override
    public String parse(String command) throws Exception {
        String[] commandPars = command.split(" ");
        if(Objects.equals(commandPars[0], "view_balance") && commandPars.length == 3){
            var view = new ViewBalance(commandPars[1], commandPars[2]);
            view.execute();
        }
        return super.parse(command);
    }
}
