package ru.itis.therapy.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.itis.therapy.model.Specialty;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsResponse {

    private Long id;

    @JsonProperty("username")
    private String email;

    private String fullName;

    private String role;

    @JsonProperty("bio")
    private String specialistBio;

    @JsonProperty("rating")
    private Double specialistRating;

    @JsonProperty("price")
    private Integer specialistAppointmentPrice;

    @JsonProperty("specialties")
    private List<Specialty> specialties;
}
