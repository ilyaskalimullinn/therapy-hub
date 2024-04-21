package ru.itis.therapy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.therapy.dto.request.SearchSpecialistRequest;
import ru.itis.therapy.model.User;
import ru.itis.therapy.repository.SpecialistRepository;
import ru.itis.therapy.service.SpecialistService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository specialistRepository;

    @Override
    public List<User> getAllByFilter(SearchSpecialistRequest request) {
        return specialistRepository.findSpecialistsByFilter(request);
    }
}
