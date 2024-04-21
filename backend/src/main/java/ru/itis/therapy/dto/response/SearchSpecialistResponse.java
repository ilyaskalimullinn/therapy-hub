package ru.itis.therapy.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.itis.therapy.model.Speciality;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchSpecialistResponse {

    @JsonProperty("id")
    private Long specialistId;

    private String name;

    private Double rating;

    private String bio;

    private Integer price;

    private List<Speciality> specialityList;
}
