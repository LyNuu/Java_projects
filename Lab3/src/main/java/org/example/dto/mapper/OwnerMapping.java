package org.example.dto.mapper;

import org.example.dto.OwnerDto;
import org.example.model.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OwnerMapping {

    @Mapping(target = "id", source = "id")
    OwnerDto toDto(Owner owner);

    @Mapping(target = "id", ignore = true)
    Owner toEntity(OwnerDto ownerDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(OwnerDto ownerDto, @MappingTarget Owner owner);
}
