package com.kombucha.domain.jwt;

import com.kombucha.domain.users.Users;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    private boolean expired;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Builder
    private JwtToken(String token, Users user) {
        this.expired = false;
        this.token = token;
        this.user = user;
    }

    public void expireToken() {
        this.expired = true;
    }
}
