package ru.itis.therapy.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditProfileRequest {
    private String fullName;
    // for specialist:
    private String bio;
    private int price;
    private List<Long> specialties;
}
