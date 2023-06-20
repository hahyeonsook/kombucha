package com.kombucha.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmailIsNotNull(String email);
    void deleteByEmail(String email);
}
