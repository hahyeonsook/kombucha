package com.kombucha.service.users;

import com.kombucha.domain.users.UsersRepository;
import com.kombucha.web.dto.users.UsersCreateRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsersService {
    // todo 이거 왜 final로 해야하더라? 찾아보기
    private final UsersRepository usersRepository;

    // todo 비밀번호 암호화
    // todo 이메일 중복 확인
    @Transactional
    public Long create(UsersCreateRequestDto usersCreateRequestDto) {
        return usersRepository.save(usersCreateRequestDto.toEntity()).getId();
    }
}
