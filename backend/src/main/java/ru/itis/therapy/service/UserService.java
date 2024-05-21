package ru.itis.therapy.service;

import ru.itis.therapy.dto.UserDto;
import ru.itis.therapy.model.User;

public interface UserService {
    void create(User user);
    UserDto getByEmail(String email);
    void deleteAccount(Long userId);
}
