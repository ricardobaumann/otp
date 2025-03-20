package com.github.ricbau.otp.controllers;

import com.github.ricbau.otp.input.OtpInput;
import com.github.ricbau.otp.output.OtpOutput;
import com.github.ricbau.otp.services.OtpService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/otp")
public class OtpController {
    private final OtpService otpService;

    @PostMapping("/mail/{mail:.+}")
    @ResponseStatus(HttpStatus.CREATED)
    public OtpOutput post(@PathVariable @Valid @Email String mail) {
        return otpService.create(new OtpInput(mail));
    }

}
