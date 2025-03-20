package presentation;

import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;

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
public abstract class parseCommand {
    protected parseCommand nextCommand;

    protected parseCommand(parseCommand next_command){

        nextCommand = next_command;

    }

    public String Parse(String command) throws SQLException, Exception {

        if(nextCommand != null)
            return nextCommand.Parse(command);

        return String.format("Unknown command: %s", command);
    }
}
