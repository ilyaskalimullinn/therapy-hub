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
public class SearchSpecialistPriceFilter {

    @JsonProperty("min")
    private Integer minPrice;

    @JsonProperty("max")
    private Integer maxPrice;
}
