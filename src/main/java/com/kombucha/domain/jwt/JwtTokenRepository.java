package com.kombucha.domain.jwt;

import com.kombucha.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    JwtToken findJwtTokenByUserAndExpiredFalse(Users user);
    JwtToken findByTokenAndExpiredFalse(String token);
    List<JwtToken> findAllByUserAndExpiredFalse(Users user);
}
