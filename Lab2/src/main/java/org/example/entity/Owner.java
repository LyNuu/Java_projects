package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "owners")
public class Owner {
    @Id
    private Integer id;
    private String name;
    private String birthday;
    @OneToMany(mappedBy = "owner")
    private List<Pet> pets;

}
