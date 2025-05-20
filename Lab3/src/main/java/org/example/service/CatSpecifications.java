package org.example.service;

import org.example.model.Cat;
import org.example.model.color.Color;
import org.springframework.data.jpa.domain.Specification;

public class CatSpecifications {
    public static Specification<Cat> hasColor(Color color) {
        return (root, query, cb) ->
                color != null ? cb.equal(root.get("color"), color) : null;
    }

    public static Specification<Cat> hasBreed(String breed) {
        return (root, query, cb) ->
                breed != null ? cb.equal(root.get("breed"), breed) : null;
    }

    public static Specification<Cat> hasName(String name) {
        return (root, query, cb) ->
                name != null ? cb.like(root.get("name"), "%" + name + "%") : null;
    }

    public static Specification<Cat> combineFilters(
            String breed,
            String name
    ) {
        return Specification.where(hasBreed(breed))
                .and(hasName(name));
    }
}