package org.example.presentation;


import org.example.repository.PetRepository;
import org.example.service.PetService;

import java.util.Objects;

public class GetAllParsePet extends ParseCommand {
    public GetAllParsePet(ParseCommand next_command) {
        super(next_command);
    }

    @Override
    public String parse(String command) throws Exception {
        String[] commandPars = command.split(" ");
        if (Objects.equals(commandPars[0], "get_all") && Objects.equals(commandPars[1], "pet") && commandPars.length == 2) {
            var getAllCommand = new PetService(new PetRepository());
            getAllCommand.getAll();
        }
        return super.parse(command);
    }
}
