package ru.itis.therapy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.therapy.dto.request.SearchSpecialistRequest;
import ru.itis.therapy.exception.SpecialistNotFoundException;
import ru.itis.therapy.model.User;
import ru.itis.therapy.repository.SpecialistRepository;
import ru.itis.therapy.repository.UserRepository;
import ru.itis.therapy.service.SpecialistService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final UserRepository userRepository;

    @Override
    public List<User> getAllByFilter(SearchSpecialistRequest request) {
        return specialistRepository.findSpecialistsByFilter(request);
    }

    @Override
    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty() || !user.get().getRole().equals(User.UserRole.SPECIALIST)) {
            throw new SpecialistNotFoundException("Specialist with id " + id + " not found");
        }

        return user.get();
    }
}
