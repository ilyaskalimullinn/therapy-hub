package ru.itis.therapy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchSpecialistFilterParams {
    private SearchSpecialistPriceFilter price;
    private SearchSpecialistRatingFilter rating;
    private List<Long> specialityList;
}
