package org.example.dto.mapper;

import org.example.dto.CatDto;
import org.example.model.Cat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CatMapping {
    @Mapping(target = "id", source = "id")
    CatDto toDto(Cat cat);

    @Mapping(target = "id", ignore = true)
    Cat toEntity(CatDto catDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CatDto catDto, @MappingTarget Cat cat);
}
