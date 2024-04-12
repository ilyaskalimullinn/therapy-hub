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
public class RegisterRequest {

    @JsonProperty("username")
    private String email;

    private String fullName;

    private String password;

    private String passwordRepeat;

    private String role;
}
