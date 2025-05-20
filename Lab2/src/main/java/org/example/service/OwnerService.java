package org.example.service;

import lombok.AllArgsConstructor;
import org.example.entity.Owner;
import org.example.repository.Repository;
import java.util.List;
@AllArgsConstructor
public class OwnerService implements Service<Owner> {

    private final Repository<Owner> ownerRepository;
    @Override
    public Owner save(Owner entity) {
        return ownerRepository.save(entity);
    }

    @Override
    public void deleteById(long id) {
         ownerRepository.deleteById(id);
    }

    @Override
    public void deleteByEntity(Owner entity) {
        ownerRepository.deleteByEntity(entity);
    }

    @Override
    public void deleteAll() {
        ownerRepository.deleteAll();
    }

    @Override
    public Owner update(Owner entity) {
        return ownerRepository.update(entity);
    }

    @Override
    public Owner getById(long id) {
        return ownerRepository.getById(id);
    }

    @Override
    public List<Owner> getAll() {
        return ownerRepository.getAll();
    }
}
