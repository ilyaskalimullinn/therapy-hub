package ru.itis.therapy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.therapy.model.Speciality;
import ru.itis.therapy.service.SpecialityService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.uri}/speciality")
public class SpecialityController {

    private final SpecialityService specialityService;

    @GetMapping
    public ResponseEntity<List<Speciality>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(specialityService.getAll());
    }
}
