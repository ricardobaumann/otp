package com.github.ricbau.otp.services;

import com.github.ricbau.otp.input.OtpInput;
import com.github.ricbau.otp.output.OtpOutput;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.random.RandomGenerator;

@Service
@AllArgsConstructor
public class OtpService {

    private final RandomGenerator randomGenerator = RandomGenerator.getDefault();

    /**
     * @noinspection unused
     */
    @CachePut(key = "#otpInput.email", cacheNames = "otp")
    public OtpOutput create(final OtpInput otpInput) {
        return new OtpOutput(String.valueOf(randomGenerator.nextInt(0, 9999)));
    }

    /**
     * @noinspection unused
     */
    @Cacheable(key = "#username", cacheNames = "otp")
    @Nullable
    public OtpOutput getOtpFor(final String username) {
        return null;
    }
}
