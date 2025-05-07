package org.example.domain;

import org.example.dto.CatDto;

import java.util.List;

public interface CatServicePort {
    public CatDto save(CatDto catDto);

    public void deleteById(Integer id);

    public void deleteByEntity(CatDto catDto);

    public void deleteAll();

    public CatDto update(CatDto catDto);

    public CatDto getById(Integer id);

    public List<CatDto> getAll();
}
