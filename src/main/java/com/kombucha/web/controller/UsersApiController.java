package com.kombucha.web.controller;

import com.kombucha.service.users.UsersService;
import com.kombucha.web.dto.users.UsersCreateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UsersApiController {
    private final UsersService usersService;

    @PostMapping("/api/v1/users")
    public Long create(@Valid @RequestBody UsersCreateRequestDto usersCreateRequestDto) {
        return usersService.create(usersCreateRequestDto);
    }
}
