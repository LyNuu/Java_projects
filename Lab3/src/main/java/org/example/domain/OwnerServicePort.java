package org.example.domain;

import org.example.dto.OwnerDto;

import java.util.List;

public interface OwnerServicePort {
    public OwnerDto save(OwnerDto ownerDto);

    public void deleteById(Integer id);

    public void deleteByEntity(OwnerDto ownerDto);

    public void deleteAll();

    public OwnerDto update(OwnerDto ownerDto);

    public OwnerDto getById(Integer id);

    public List<OwnerDto> getAll();
}
