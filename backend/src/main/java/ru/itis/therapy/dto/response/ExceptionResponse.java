package ru.itis.therapy.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ExceptionResponse {
    private String message;
}
