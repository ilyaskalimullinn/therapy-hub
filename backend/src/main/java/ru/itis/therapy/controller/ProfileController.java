package ru.itis.therapy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.itis.therapy.dto.request.EditProfileRequest;
import ru.itis.therapy.model.User;
import ru.itis.therapy.security.service.JWTService;
import ru.itis.therapy.service.SpecialistService;
import ru.itis.therapy.service.UserService;

import java.util.List;

@RestController
@RequestMapping("${api.uri}/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final JWTService jwtService;
    private final SpecialistService specialistService;
    private final UserService userService;

    @PostMapping("/edit")
    public ResponseEntity<Void> edit(@RequestBody EditProfileRequest editProfileRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        User user = userService.findById(jwtService.getClaims(token.substring(7)).get("id").asLong()).get();
        userService.editProfile(editProfileRequest, user);

        if (user.getRole().equals(User.UserRole.SPECIALIST)) {
            specialistService.editProfile(editProfileRequest, user);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit/delete")
    public ResponseEntity<Void> deleteAccount(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        userService.deleteAccount(jwtService.getClaims(token.substring(7)).get("id").asLong());
        return ResponseEntity.ok().build();
    }
}
