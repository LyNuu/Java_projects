package org.example.domain;

import org.example.dto.CatDto;

import java.util.List;

public interface CatServicePort {
    CatDto save(CatDto catDto);

    void deleteById(Integer id);

    void deleteByEntity(CatDto catDto);

    void deleteAll();

    CatDto update(CatDto catDto);

    CatDto getById(Integer id);

    List<CatDto> getAll();
}
