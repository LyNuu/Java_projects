package org.example.presentation;

import org.example.entity.Owner;
import org.example.entity.Pet;
import org.example.service.OwnerController;
import org.example.service.PetСontroller;

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
            var saveCommand = new PetСontroller();
            saveCommand.save(pet);
        }
        return super.parse(command);
    }
}
