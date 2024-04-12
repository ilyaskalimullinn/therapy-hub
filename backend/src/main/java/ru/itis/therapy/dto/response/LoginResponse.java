package ru.itis.therapy.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String token;

    @JsonProperty("user")
    private UserDetailsResponse details;
}
