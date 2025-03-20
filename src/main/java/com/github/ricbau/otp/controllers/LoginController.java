package com.github.ricbau.otp.controllers;

import com.github.ricbau.otp.input.LoginInput;
import com.github.ricbau.otp.output.LoginOutput;
import com.github.ricbau.otp.services.LoginService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/v1/login")
    public LoginOutput login(@RequestBody @Valid LoginInput loginInput) {
        return loginService.login(loginInput);
    }

}
