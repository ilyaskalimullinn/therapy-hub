package ru.itis.therapy.security.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.therapy.model.User;

import java.util.Map;

public interface JWTService {
    String generateToken(User user);

    DecodedJWT parse(String token) throws JWTVerificationException;

    Map<String, Claim> getClaims(String token);
}
