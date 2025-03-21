package com.github.ricbau.otp.controllers;

import com.github.ricbau.otp.input.LoginInput;
import com.github.ricbau.otp.output.LoginOutput;
import com.github.ricbau.otp.output.UserInfo;
import com.github.ricbau.otp.services.LoginService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/login")
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginOutput login(@RequestBody @Valid LoginInput loginInput) {
        return loginService.login(loginInput);
    }

    @GetMapping("/{token}")
    public UserInfo get(@PathVariable String token) {
        log.info("Checking token {}", token);
        return loginService.getUserFor(token);
    }
}
