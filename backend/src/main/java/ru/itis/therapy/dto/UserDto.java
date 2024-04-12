package ru.itis.therapy.dto;

import lombok.*;
import ru.itis.therapy.model.Appointment;
import ru.itis.therapy.model.Review;
import ru.itis.therapy.model.Speciality;
import ru.itis.therapy.model.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String email;

    private String firstName;

    private String lastName;

    private Date createdAt;

    private User.UserRole role;

    private String specialistBio;

    private Integer specialistAppointmentPrice;

    private List<Review> clientReviews;

    private List<Review> specialistReviews;

    private List<Speciality> specialityList;
}
