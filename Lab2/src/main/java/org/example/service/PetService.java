package org.example.service;

import lombok.AllArgsConstructor;
import org.example.entity.Pet;
import org.example.repository.Repository;
import java.util.List;
@AllArgsConstructor
public class PetService implements Service<Pet> {

    private final Repository<Pet> petRepository;

    @Override
    public Pet save(Pet entity) {
        return petRepository.save(entity);
    }

    @Override
    public void deleteById(long id) {
       petRepository.deleteById(id);
    }

    @Override
    public void deleteByEntity(Pet entity) {
        petRepository.deleteByEntity(entity);
    }

    @Override
    public void deleteAll() {
        petRepository.deleteAll();
    }

    @Override
    public Pet update(Pet entity) {
        return petRepository.update(entity);
    }

    @Override
    public Pet getById(long id) {
        return petRepository.getById(id);
    }

    @Override
    public List<Pet> getAll() {
        return petRepository.getAll();
    }
}
