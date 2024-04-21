package ru.itis.therapy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.therapy.dto.request.SearchSpecialistRequest;
import ru.itis.therapy.dto.response.SearchSpecialistResponse;
import ru.itis.therapy.model.User;
import ru.itis.therapy.service.SpecialistService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/specialist")
public class SpecialistController {

    private final SpecialistService specialistService;

    @PostMapping("/find")
    public ResponseEntity<List<SearchSpecialistResponse>> searchSpecialists(@RequestBody SearchSpecialistRequest request) {
        List<User> specialists = specialistService.getAllByFilter(request);

        List<SearchSpecialistResponse> searchSpecialistResponse = new ArrayList<>();

        for (User specialist : specialists) {
            SearchSpecialistResponse response = SearchSpecialistResponse.builder()
                    .specialistId(specialist.getId())
                    .name(specialist.getFirstName() + " " + specialist.getLastName())
                    .rating(specialist.getSpecialistAvgRating())
                    .bio(specialist.getSpecialistBio())
                    .price(specialist.getSpecialistAppointmentPrice())
                    .specialityList(specialist.getSpecialityList())
                    .build();

            searchSpecialistResponse.add(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(searchSpecialistResponse);
    }
}
