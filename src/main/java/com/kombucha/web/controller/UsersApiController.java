package com.kombucha.web.controller;

import com.kombucha.common.CommonResponse;
import com.kombucha.service.users.UsersService;
import com.kombucha.web.dto.users.UsersCreateRequestDto;
import com.kombucha.web.dto.users.UsersLoginRequestDto;
import com.kombucha.web.dto.users.UsersRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UsersApiController {
    private final UsersService usersService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> create(@Valid @RequestBody UsersCreateRequestDto usersCreateRequestDto) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, usersService.create(usersCreateRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse> login(@Valid @RequestBody UsersLoginRequestDto usersLoginRequestDto) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, usersService.login(usersLoginRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse> logout(UsersRequestDto usersRequestDto) {
        usersService.logout(usersRequestDto);
        return CommonResponse.toResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<CommonResponse> delete(UsersRequestDto usersRequestDto) {
        usersService.delete(usersRequestDto);
        return CommonResponse.toResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<CommonResponse> getProfile(UsersRequestDto usersRequestDto) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, usersService.getProfile(usersRequestDto));
    }
}
