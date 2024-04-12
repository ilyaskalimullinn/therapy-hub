package ru.itis.therapy.service;

import ru.itis.therapy.dto.UserDto;
import ru.itis.therapy.dto.request.SearchSpecialistRequest;
import ru.itis.therapy.model.User;

import java.util.List;

public interface UserService {
    void create(User user);
    UserDto getByEmail(String email);
}
