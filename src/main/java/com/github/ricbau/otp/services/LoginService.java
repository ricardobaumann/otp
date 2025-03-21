package com.github.ricbau.otp.services;

import com.github.ricbau.otp.exceptions.FailedLogin;
import com.github.ricbau.otp.input.LoginInput;
import com.github.ricbau.otp.output.LoginOutput;
import com.github.ricbau.otp.output.OtpOutput;
import com.github.ricbau.otp.output.UserInfo;
import com.github.ricbau.otp.repos.TokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {
    private final OtpService otpService;
    private final TokenRepo tokenRepo;

    public LoginOutput login(final LoginInput loginInput) {
        return Optional.ofNullable(otpService.getOtpFor(loginInput.username()))
                .map(OtpOutput::code)
                .filter(code -> loginInput.otp().equals(code))
                .map(__ -> tokenRepo.generateFor(loginInput.username()))
                .orElseThrow(FailedLogin::new);
    }

    public UserInfo getUserFor(final String token) {
        return tokenRepo.decrypt(token);
    }
}
