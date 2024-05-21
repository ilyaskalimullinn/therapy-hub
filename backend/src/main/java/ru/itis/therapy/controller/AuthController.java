package ru.itis.therapy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.therapy.dto.UserDto;
import ru.itis.therapy.dto.request.LoginRequest;
import ru.itis.therapy.dto.request.RegisterRequest;
import ru.itis.therapy.dto.response.LoginResponse;
import ru.itis.therapy.dto.response.RegisterResponse;
import ru.itis.therapy.dto.response.UserDetailsResponse;
import ru.itis.therapy.model.Review;
import ru.itis.therapy.model.User;
import ru.itis.therapy.service.AuthService;
import ru.itis.therapy.service.UserService;

import java.text.DecimalFormat;
import java.util.ArrayList;

@RestController
@RequestMapping("${api.uri}/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        String token = authService.register(registerRequest);
        UserDetailsResponse userDetailsResponse = UserDetailsResponse.builder()
                .email(registerRequest.getEmail())
                .fullName(registerRequest.getFullName())
                .role(registerRequest.getRole())
                .build();

        UserDto user = userService.getByEmail(registerRequest.getEmail());
        
        if (user.getRole().equals(User.UserRole.SPECIALIST)) {
                userDetailsResponse.setSpecialistBio(user.getSpecialistBio());

                if (user.getSpecialistReviews() != null && !user.getSpecialistReviews().isEmpty()) {
                        Double rating = (double) user.getSpecialistReviews().stream()
                                        .map(Review::getRating)
                                        .reduce(0, Integer::sum) / user.getSpecialistReviews().size();

                        userDetailsResponse.setSpecialistRating(
                                        Double.parseDouble(new DecimalFormat("0.00").format(rating)));
                }

                userDetailsResponse.setId(user.getId());
                userDetailsResponse.setSpecialistRating(user.getSpecialistAvgRating());
                userDetailsResponse.setSpecialistAppointmentPrice(user.getSpecialistAppointmentPrice());
                userDetailsResponse.setSpecialties(new ArrayList<>());
        }        

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RegisterResponse.builder()
                        .token(token)
                        .details(userDetailsResponse)
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);

        UserDto user = userService.getByEmail(loginRequest.getEmail());

        UserDetailsResponse userDetailsResponse = UserDetailsResponse.builder()
                .id(user.getId())
                .email(loginRequest.getEmail())
                .fullName(user.getFullName())
                .role(String.valueOf(user.getRole()))
                .build();

        if (user.getRole().equals(User.UserRole.SPECIALIST)) {
            userDetailsResponse.setSpecialistBio(user.getSpecialistBio());

            if (user.getSpecialistReviews() != null && !user.getSpecialistReviews().isEmpty()) {
                Double rating =  (double) user.getSpecialistReviews().stream()
                        .map(Review::getRating)
                        .reduce(0, Integer::sum) / user.getSpecialistReviews().size();

                userDetailsResponse.setSpecialistRating(Double.parseDouble(new DecimalFormat("0.00").format(rating)));
            }

            userDetailsResponse.setSpecialistRating(user.getSpecialistAvgRating());
            userDetailsResponse.setSpecialistAppointmentPrice(user.getSpecialistAppointmentPrice());
            userDetailsResponse.setSpecialties(user.getSpecialties());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(LoginResponse.builder()
                        .token(token)
                        .details(userDetailsResponse)
                        .build());
    }
}
