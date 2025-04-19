package org.example.presentation;

import org.example.repository.OwnerRepository;
import org.example.service.OwnerService;

import java.util.Objects;

public class GetByIdParseOwner extends ParseCommand {
    public GetByIdParseOwner(ParseCommand next_command) {
        super(next_command);
    }

    @Override
    public String parse(String command) throws Exception {
        String[] commandPars = command.split(" ");
        if (Objects.equals(commandPars[0], "delete") && Objects.equals(commandPars[1], "owner") && commandPars.length == 3) {
            var deleteCommand = new OwnerService(new OwnerRepository());
            deleteCommand.deleteById(Integer.parseInt(commandPars[2]));
        }
        return super.parse(command);
    }
}
