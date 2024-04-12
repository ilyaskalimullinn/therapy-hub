package ru.itis.therapy.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchSpecialistRatingFilter {
    @JsonProperty("min")
    private Double minRating;

    @JsonProperty("max")
    private Double maxRating;
}
