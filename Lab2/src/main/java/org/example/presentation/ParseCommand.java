package org.example.presentation;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class ParseCommand {

    protected ParseCommand nextCommand;

    public ParseCommand(ParseCommand nextCommand) {
        this.nextCommand = nextCommand;
    }

    public String parse(String command) throws Exception {

        if (nextCommand != null)
            return nextCommand.parse(command);

        return String.format("Unknown command: %s", command);
    }
}
