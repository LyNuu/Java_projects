package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.domain.OwnerServicePort;
import org.example.dto.OwnerDto;
import org.example.dto.mapper.OwnerMapping;
import org.example.model.Owner;
import org.example.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerService implements OwnerServicePort {
    private final OwnerRepository ownerRepository;
    private final OwnerMapping ownerMapping;

    @Override
    public OwnerDto save(OwnerDto ownerDto) {
        Owner owner = ownerMapping.toEntity(ownerDto);
        Owner savedOwner = ownerRepository.save(owner);
        return ownerMapping.toDto(savedOwner);
    }

    @Override
    public void deleteById(Integer id) {
        ownerRepository.deleteById(id);

    }

    @Override
    public void deleteByEntity(OwnerDto ownerDto) {
        Owner owner = ownerMapping.toEntity(ownerDto);
        ownerRepository.delete(owner);

    }

    @Override
    public void deleteAll() {
        ownerRepository.deleteAll();

    }

    @Override
    public OwnerDto update(OwnerDto ownerDto) {
        Owner owner = ownerRepository.findById(ownerDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        ownerMapping.updateEntityFromDto(ownerDto, owner);
        var updateOwner = ownerRepository.save(owner);
        return ownerMapping.toDto(updateOwner);
    }

    @Override
    public OwnerDto getById(Integer id) {
        return ownerRepository.findById(id)
                .map(ownerMapping::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + id));
    }

    @Override
    public List<OwnerDto> getAll() {
        return ownerRepository.findAll().stream().map(ownerMapping::toDto).collect(Collectors.toList());
    }
}
