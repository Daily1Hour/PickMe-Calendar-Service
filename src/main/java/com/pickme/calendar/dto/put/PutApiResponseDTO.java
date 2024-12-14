package com.pickme.calendar.dto.put;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PutApiResponseDTO {
    private String success;

    private String message;
}
