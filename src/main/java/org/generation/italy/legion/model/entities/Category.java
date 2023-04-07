package org.generation.italy.legion.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
public class Category implements WithId{
    @Id
    @GeneratedValue(generator = "category_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_generator", sequenceName = "category_sequence", allocationSize = 1)
    @Column(name= "id_category")
    private long id;
    private String name;
    private String description;

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

}
