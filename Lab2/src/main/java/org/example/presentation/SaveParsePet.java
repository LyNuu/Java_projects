package org.example.presentation;

import org.example.entity.Pet;
import org.example.repository.PetRepository;
import org.example.service.PetService;

import java.util.Objects;

public class SaveParsePet extends ParseCommand {
    public SaveParsePet(ParseCommand next_command) {
        super(next_command);
    }

    @Override
    public String parse(String command) throws Exception {
        String[] commandPars = command.split(" ");
        if (Objects.equals(commandPars[0], "save") && Objects.equals(commandPars[1], "pet") && commandPars.length == 5) {
            Pet pet = Pet.builder()
                    .id(Integer.parseInt(commandPars[2]))
                    .name(commandPars[3])
                    .birthday(commandPars[4])
                    .build();
            var saveCommand = new PetService(new PetRepository());
            saveCommand.save(pet);
        }
        return super.parse(command);
    }
}
