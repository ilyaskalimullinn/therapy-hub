package ru.itis.therapy.service;

import ru.itis.therapy.dto.request.LoginRequest;
import ru.itis.therapy.dto.request.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest registerRequest);
    String login(LoginRequest loginRequest);
}
