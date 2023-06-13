package com.kombucha.domain.users;

import com.kombucha.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Users extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(nullable = false)
    private int age;
    @Column(length = 500)
    private String description;

    @Builder
    public Users(String email, String password, String name, int age, String description) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.description = description;
    }
}
