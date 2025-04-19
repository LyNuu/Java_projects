package org.example.presentation;

import org.example.entity.Owner;
import org.example.repository.OwnerRepository;
import org.example.service.OwnerService;

import java.util.Objects;

public class SaveParseOwner extends ParseCommand {
    public SaveParseOwner(ParseCommand next_command) {
        super(next_command);
    }

    @Override
    public String parse(String command) throws Exception {
        String[] commandPars = command.split(" ");
        if (Objects.equals(commandPars[0], "save") && commandPars.length == 5) {
            Owner owner = Owner.builder()
                    .id(Integer.parseInt(commandPars[2]))
                    .name(commandPars[3])
                    .birthday(commandPars[4])
                    .build();
            var saveCommand = new OwnerService(new OwnerRepository());
            saveCommand.save(owner);
        }
        return super.parse(command);
    }
}
