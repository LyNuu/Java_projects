package org.example.dto;

import lombok.Data;
import org.example.model.Cat;
import org.example.model.color.Color;

@Data
public class CatDto {
    private Integer id;
    private String name;
    private String birthday;
    private String breed;
    private Color color;
    private Cat cat;

}
