package org.example.presentation;

import org.example.service.OwnerController;

import java.util.Objects;

public class GetByIdParsePet extends ParseCommand {
    public GetByIdParsePet(ParseCommand next_command) {
        super(next_command);
    }

    @Override
    public String parse(String command) throws Exception {
        String[] commandPars = command.split(" ");
        if (Objects.equals(commandPars[0], "delete") && Objects.equals(commandPars[1], "pet") && commandPars.length == 3) {
            var deleteCommand = new OwnerController();
            deleteCommand.deleteById(Integer.parseInt(commandPars[2]));
        }
        return super.parse(command);
    }
}
