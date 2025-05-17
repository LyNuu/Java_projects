package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.CatDto;
import org.example.service.CatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cats")
@RequiredArgsConstructor
public class CatController {
    private final CatService catService;

    @PostMapping
    public ResponseEntity<CatDto> createCat(@RequestBody CatDto catDto) {
        return ResponseEntity.ok(catService.save(catDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatDto> updateCat(@PathVariable("id") Integer id, @RequestBody CatDto catDto) {
        catDto.setId(id);
        return ResponseEntity.ok(catService.update(catDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCatById(@PathVariable("id") Integer id) {
        catService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllCats() {
        catService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatDto> getCatById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(catService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CatDto>> getAllCats() {
        return ResponseEntity.ok(catService.getAll());
    }
}
