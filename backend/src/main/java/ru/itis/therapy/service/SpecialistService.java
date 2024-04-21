package ru.itis.therapy.service;

import ru.itis.therapy.dto.request.SearchSpecialistRequest;
import ru.itis.therapy.model.User;

import java.util.List;

public interface SpecialistService {
    List<User> getAllByFilter(SearchSpecialistRequest request);
}
