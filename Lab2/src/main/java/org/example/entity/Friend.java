package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "friends")
public class Friend {
    @Id
    private Integer id;
    private String name;
    @Builder.Default
    @ManyToMany(mappedBy = "friends")
    private List<Pet> pets = new ArrayList<>();
}
