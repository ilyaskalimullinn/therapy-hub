package ru.itis.therapy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.itis.therapy.dto.UserDto;
import ru.itis.therapy.dto.request.EditProfileRequest;
import ru.itis.therapy.exception.UserAlreadyExistsException;
import ru.itis.therapy.exception.UserNotFoundException;
import ru.itis.therapy.model.User;
import ru.itis.therapy.repository.UserRepository;
import ru.itis.therapy.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void create(User user) {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }
    }

    @Override
    public UserDto getByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with this email not found");
        }

        User user = userOptional.get();

        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .createdAt(user.getCreatedAt())
                .role(user.getRole())
                .specialistBio(user.getSpecialistBio())
                .specialistAppointmentPrice(user.getSpecialistAppointmentPrice())
                .specialistAvgRating(user.getSpecialistAvgRating())
                .clientReviews(user.getClientReviews())
                .specialistReviews(user.getSpecialistReviews())
                .specialties(user.getSpecialties())
                .build();
    }

    @Override
    public void deleteAccount(Long userId) {
        userRepository.delete(userRepository.findById(userId).get());
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void editProfile(EditProfileRequest editProfileRequest, User user) {
        user.setFullName(editProfileRequest.getFullName());
        this.userRepository.save(user);
    }
    
}
