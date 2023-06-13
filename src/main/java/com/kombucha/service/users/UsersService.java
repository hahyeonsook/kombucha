package com.kombucha.service.users;

import com.kombucha.component.util.JwtUtil;
import com.kombucha.domain.users.Users;
import com.kombucha.domain.users.UsersRepository;
import com.kombucha.exception.AuthenticationException;
import com.kombucha.web.dto.users.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersMinimalResponseDto create(UsersCreateRequestDto usersCreateRequestDto) {
        usersRepository.save(usersCreateRequestDto.toEntity());
        String token = JwtUtil.generateJwt(usersCreateRequestDto);
        return UsersMinimalResponseDto.builder().token(token).build();
    }

    public void delete(UsersRequestDto usersRequestDto) {
        usersRepository.deleteByEmail(usersRequestDto.getEmail());
    }

    // https://adjh54.tistory.com/94#1.%20JWT%20%EA%B0%92%EC%9D%84%20%EB%84%A3%EC%A7%80%20%EC%95%8A%EA%B3%A0%20API%20%ED%86%B5%EC%8B%A0%20%EC%8B%9C%20%ED%85%8C%EC%8A%A4%ED%8A%B8-1
    public UsersMinimalResponseDto login(UsersLoginRequestDto usersLoginRequestDto) {
        if (!authenticate(usersLoginRequestDto.getEmail(), usersLoginRequestDto.getPassword())) {
            throw new AuthenticationException(401, "올바르지 않은 사용자입니다.");
        }
        String token = JwtUtil.generateJwt(usersLoginRequestDto);
        return UsersMinimalResponseDto.builder().token(token).build();
    }

    public UsersResponseDto getProfile(UsersRequestDto usersRequestDto) {
        Users user = usersRepository.findByEmail(usersRequestDto.getEmail());
        if (user == null) {
            throw new AuthenticationException(401, "올바르지 않은 사용자입니다.");
        }
        return UsersResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .age(user.getAge())
                .description(user.getDescription())
                .build();
    }

    public boolean authenticate(String email, String password) {
        Users user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new AuthenticationException(401, "올바르지 않은 사용자입니다.");
        }
        return email.equals(user.getEmail()) && password.equals(user.getPassword());
    }
}
