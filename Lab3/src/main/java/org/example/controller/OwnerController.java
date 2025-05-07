package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.OwnerDto;
import org.example.service.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;

    @PostMapping
    public ResponseEntity<OwnerDto> createOwner(@RequestBody OwnerDto ownerDto) {
        return ResponseEntity.ok(ownerService.save(ownerDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerDto> updateOwner(@PathVariable Integer id, @RequestBody OwnerDto ownerDto) {
        ownerDto.setId(id);
        return ResponseEntity.ok(ownerService.update(ownerDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwnerById(@PathVariable Integer id) {
        ownerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteOwnerByEntity(@RequestBody OwnerDto ownerDto) {
        ownerService.deleteByEntity(ownerDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllOwners() {
        ownerService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> getOwnerById(@PathVariable Integer id) {
        return ResponseEntity.ok(ownerService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAllOwners() {
        return ResponseEntity.ok(ownerService.getAll());
    }
}
