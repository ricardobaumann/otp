package com.github.ricbau.otp.repos;

import com.github.ricbau.otp.config.JwtProperties;
import com.github.ricbau.otp.output.LoginOutput;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.crypto.SecretKey;
import java.util.Date;

@Repository
@RequiredArgsConstructor
public class TokenRepo {
    private final JwtProperties jwtProperties;
    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public LoginOutput generateFor(final String username) {
        return new LoginOutput(Jwts.builder()
                .subject((username))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtProperties.getExpirationInMillis()))
                .signWith(secretKey)
                .compact());
    }
}
