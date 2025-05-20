package org.example.presentation;

import org.example.repository.OwnerRepository;
import org.example.service.OwnerService;

import java.util.Objects;

public class DeleteAllParseOwner extends ParseCommand {
    public DeleteAllParseOwner(ParseCommand next_command) {
        super(next_command);
    }

    @Override
    public String parse(String command) throws Exception {
        String[] commandPars = command.split(" ");
        if (Objects.equals(commandPars[0], "delete_all") && Objects.equals(commandPars[1], "owner") && commandPars.length == 2) {
            var deleteAllCommand = new OwnerService(new OwnerRepository());
            deleteAllCommand.deleteAll();
        }
        return super.parse(command);
    }
}
