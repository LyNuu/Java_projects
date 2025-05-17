package org.example.domain;

import org.example.dto.OwnerDto;

import java.util.List;

public interface OwnerServicePort {
    OwnerDto save(OwnerDto ownerDto);

    void deleteById(Integer id);

    void deleteByEntity(OwnerDto ownerDto);

    void deleteAll();

    OwnerDto update(OwnerDto ownerDto);

    OwnerDto getById(Integer id);

    List<OwnerDto> getAll();
}
