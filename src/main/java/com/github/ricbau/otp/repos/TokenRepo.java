package com.github.ricbau.otp.repos;

import com.github.ricbau.otp.config.JwtProperties;
import com.github.ricbau.otp.exceptions.TokenNotFound;
import com.github.ricbau.otp.output.LoginOutput;
import com.github.ricbau.otp.output.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

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

    public UserInfo decrypt(final String token) {
        return Optional.of(parse(token))
                .map(Jwt::getPayload)
                .map(Claims::getSubject)
                .map(UserInfo::new)
                .orElseThrow(TokenNotFound::new);
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }
}
