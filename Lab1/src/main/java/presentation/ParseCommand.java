package presentation;

import lombok.Getter;
import lombok.Setter;


/**
 * классы вида Parse будут наследоваться от данного класса и каждый класс будет отвечать за выполнение одного из метода:
 * create account
 * check balance
 * withdrawal of money from an account
 * replenishment account
 * view transaction history
 */

@Getter
@Setter
public abstract class ParseCommand {

    protected ParseCommand nextCommand;

    protected ParseCommand(ParseCommand next_command) {
        nextCommand = next_command;
    }

    public String parse(String command) throws Exception {

        if(nextCommand != null)
            return nextCommand.parse(command);

        return String.format("Unknown command: %s", command);
    }
}
