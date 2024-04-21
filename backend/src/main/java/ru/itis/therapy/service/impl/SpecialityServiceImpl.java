package ru.itis.therapy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.therapy.model.Speciality;
import ru.itis.therapy.repository.SpecialityRepository;
import ru.itis.therapy.service.SpecialityService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialityServiceImpl implements SpecialityService {

    private final SpecialityRepository specialityRepository;

    @Override
    public List<Speciality> getAll() {
        return specialityRepository.findAll();
    }
}
