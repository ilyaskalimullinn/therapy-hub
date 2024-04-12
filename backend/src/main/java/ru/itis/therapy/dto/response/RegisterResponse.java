package ru.itis.therapy.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {

    private String token;

    @JsonProperty("user")
    private UserDetailsResponse details;
}
