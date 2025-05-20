package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.domain.CatServicePort;
import org.example.dto.CatDto;
import org.example.dto.mapper.CatMapping;
import org.example.model.Cat;
import org.example.model.color.Color;
import org.example.repository.CatRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatService implements CatServicePort {
    private final CatRepository catRepository;
    private final CatMapping catMapping;

    @Override
    public CatDto save(CatDto catDto) {
        Cat cat = catMapping.toEntity(catDto);
        Cat savedCat = catRepository.save(cat);
        return catMapping.toDto(savedCat);
    }

    @Override
    public void deleteById(Integer id) {
        catRepository.deleteById(id);

    }

    @Override
    public void deleteByEntity(CatDto catDto) {
        Cat cat = catMapping.toEntity(catDto);
        catRepository.delete(cat);

    }

    @Override
    public void deleteAll() {
        catRepository.deleteAll();

    }

    @Override
    public CatDto update(CatDto catDto) {
        Cat cat = catRepository.findById(catDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cat not found"));
        catMapping.updateEntityFromDto(catDto, cat);
        Cat updateCat = catRepository.save(cat);
        return catMapping.toDto(updateCat);
    }

    @Override
    public CatDto getById(Integer id) {
        return catRepository.findById(id)
                .map(catMapping::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Cat not found with id: " + id));
    }

    @Override
    public List<CatDto> getAll() {
        return catRepository.findAll().stream()
                .map(catMapping::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<CatDto> getAllFiltered(String breed, String name) {
        Specification<Cat> spec = CatSpecifications.combineFilters(breed, name);
        return catRepository.findAll(spec).stream()
                .map(catMapping::toDto)
                .collect(Collectors.toList());
    }
}
