package ru.itis.therapy.service;

import java.util.Optional;

import ru.itis.therapy.dto.UserDto;
import ru.itis.therapy.dto.request.EditProfileRequest;
import ru.itis.therapy.model.User;

public interface UserService {
    void create(User user);
    UserDto getByEmail(String email);
    void deleteAccount(Long userId);
    Optional<User> findById(Long id);
    void editProfile(EditProfileRequest editProfileRequest, User user);
}
