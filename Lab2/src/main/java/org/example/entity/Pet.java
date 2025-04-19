package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pets")
public class Pet {
    @Id
    private Integer id;
    private String name;
    private String birthday;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "owner_id")
    private Owner owner;
    @Builder.Default
    @ManyToMany
    @JoinTable(name = "pet_friends", joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<Friend> friends = new ArrayList<>();

    public void addFriend(Friend friend) {
        friends.add(friend);
        friend.getPets().add(this);
    }
}
