package ru.itis.therapy.service;

import ru.itis.therapy.dto.request.EditProfileRequest;
import ru.itis.therapy.dto.request.SearchSpecialistRequest;
import ru.itis.therapy.model.User;

import java.util.List;

public interface SpecialistService {
    List<User> getAllByFilter(SearchSpecialistRequest request);
    User getById(Long id);
    void editSpecialties(List<Long> specialties, User specialist);
    void editProfile(EditProfileRequest editProfileRequest, User user);
}
