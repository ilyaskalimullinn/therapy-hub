package ru.itis.therapy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchSpecialistRequest {
    private String specialistName;
    private SearchSpecialistFilterParams filterParams;
    private SearchSpecialistOrder order;
}