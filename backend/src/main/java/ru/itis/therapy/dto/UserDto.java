package ru.itis.therapy.dto;

import lombok.*;
import ru.itis.therapy.model.Review;
import ru.itis.therapy.model.Specialty;
import ru.itis.therapy.model.User;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String email;

    private String fullName;

    private Date createdAt;

    private User.UserRole role;

    private String specialistBio;

    private Integer specialistAppointmentPrice;

    private Double specialistAvgRating;

    private List<Review> clientReviews;

    private List<Review> specialistReviews;

    private List<Specialty> specialties;
}
