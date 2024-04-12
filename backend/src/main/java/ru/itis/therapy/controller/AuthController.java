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

@RestController
@RequestMapping("/auth")
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
                .email(loginRequest.getEmail())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .role(String.valueOf(user.getRole()))
                .build();

        if (user.getRole().equals(User.UserRole.SPECIALIST)) {
            userDetailsResponse.setSpecialistBio(user.getSpecialistBio());

            if (!user.getSpecialistReviews().isEmpty()) {
                Double rating =  (double) user.getSpecialistReviews().stream()
                        .map(Review::getRating)
                        .reduce(0, Integer::sum) / user.getSpecialistReviews().size();

                userDetailsResponse.setSpecialistRating(Double.parseDouble(new DecimalFormat("0.00").format(rating)));
            }
            userDetailsResponse.setSpecialistAppointmentPrice(user.getSpecialistAppointmentPrice());
            userDetailsResponse.setSpecialtyList(user.getSpecialityList());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(LoginResponse.builder()
                        .token(token)
                        .details(userDetailsResponse)
                        .build());
    }
}
