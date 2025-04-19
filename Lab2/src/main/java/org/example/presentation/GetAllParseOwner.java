package org.example.presentation;

import org.example.service.OwnerController;

import java.util.Objects;

public class GetAllParseOwner extends ParseCommand {
    public GetAllParseOwner(ParseCommand next_command) {
        super(next_command);
    }

    @Override
    public String parse(String command) throws Exception {
        String[] commandPars = command.split(" ");
        if (Objects.equals(commandPars[0], "get_all") && Objects.equals(commandPars[1], "owner") && commandPars.length == 2) {
            var getAllCommand = new OwnerController();
            getAllCommand.getAll();
        }
        return super.parse(command);
    }
}
