package ru.itis.therapy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.therapy.dto.request.LoginRequest;
import ru.itis.therapy.dto.request.RegisterRequest;
import ru.itis.therapy.exception.PasswordsNotMatchException;
import ru.itis.therapy.exception.UserNotFoundException;
import ru.itis.therapy.model.User;
import ru.itis.therapy.repository.UserRepository;
import ru.itis.therapy.security.service.JWTService;
import ru.itis.therapy.service.AuthService;
import ru.itis.therapy.service.UserService;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public String register(RegisterRequest registerRequest) {

        if (!registerRequest.getPassword().equals(registerRequest.getPasswordRepeat())) {
            throw new PasswordsNotMatchException("Passwords do not match");
        }

        User user = User.builder()
                .email(registerRequest.getEmail())
                .fullName(registerRequest.getFullName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .createdAt(new Date())
                .role(User.UserRole.valueOf(registerRequest.getRole()))
                .authority(User.UserAuthority.DEFAULT)
                .build();

        userService.create(user);

        return jwtService.generateToken(user);
    }

    @Override
    public String login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                (() -> new UserNotFoundException("User with this email not found"))
        );

        return jwtService.generateToken(user);
    }
}
