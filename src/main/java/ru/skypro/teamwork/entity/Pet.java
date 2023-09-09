package ru.skypro.teamwork.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column
    String species;
    @Column
    String breed;
    @Column
    String name;
    @Column
    Integer age;
    @Column
    String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    User owner;
    @OneToMany(mappedBy = "pet")
    List<Report> reports;
    @Column(name = "date_adoption")
    LocalDate date_adoption;

    @Override
    public String toString() {
        return "Name: " + getName() + ", age: " + age + ", " + species;
    }
}