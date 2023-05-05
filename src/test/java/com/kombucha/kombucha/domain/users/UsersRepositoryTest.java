package com.kombucha.kombucha.domain.users;

import com.kombucha.domain.users.Users;
import com.kombucha.domain.users.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @AfterEach
    public void cleanup() {
        usersRepository.deleteAll();
    }

    @Test
    public void 사용자를_저장_불러온다() {
        // given
        String email = "user1@example.com";
        String password = "1234";
        String name = "이름";
        Integer age = 10;

        // when
        usersRepository.save(Users.builder()
                .email(email).password(password).name(name).age(age)
                .build());

        List<Users> usersList = usersRepository.findAll();
        Users user = usersList.get(0);

        // then
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getAge()).isEqualTo(age);
    }

    @Test
    public void 사용자를_저장_삭제한다() {
        // given
        String email = "user1@example.com";
        String password = "1234";
        String name = "이름";
        Integer age = 10;

        Users user = Users.builder().email(email).password(password).name(name).age(age).build();
        usersRepository.save(user);

        // when
        usersRepository.delete(user);

        // then
        assertThat(usersRepository.count()).isEqualTo(0);
    }
}
