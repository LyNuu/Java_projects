package org.example.presentation;

import org.example.repository.PetRepository;
import org.example.service.PetService;

import java.util.Objects;

public class DeleteAllParsePet extends ParseCommand {
    public DeleteAllParsePet(ParseCommand next_command) {
        super(next_command);
    }

    @Override
    public String parse(String command) throws Exception {
        String[] commandPars = command.split(" ");
        if (Objects.equals(commandPars[0], "delete_all") && Objects.equals(commandPars[1], "pet") && commandPars.length == 2) {
            var deleteAllCommand = new PetService(new PetRepository());
            deleteAllCommand.deleteAll();
        }
        return super.parse(command);
    }
}
