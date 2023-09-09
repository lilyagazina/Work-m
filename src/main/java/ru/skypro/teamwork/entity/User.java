package ru.skypro.teamwork.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "chat_id")
    Long chatId;
    @Column(name = "full_name")
    String fullName;
    @Column(name = "access_level")
    String accessLevel;
    @Column(name = "current_state")
    String currentState;
    String address;
    @Column(name = "phone_numbers")
    String phoneNumbers;
    @Column(name = "email")
    String email;
    @OneToMany(mappedBy = "owner")
    List<Pet> pets;

    @Override
    public String toString() {
        return id + ": " + "(" + chatId + ") " + fullName + ", " + accessLevel;
    }
}
