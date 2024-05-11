package ru.itis.therapy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.therapy.model.Specialty;
import ru.itis.therapy.repository.SpecialtyRepository;
import ru.itis.therapy.service.SpecialtyService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    @Override
    public List<Specialty> getAll() {
        return specialtyRepository.findAll();
    }
}
