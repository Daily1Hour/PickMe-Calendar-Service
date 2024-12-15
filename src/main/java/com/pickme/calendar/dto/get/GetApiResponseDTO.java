package com.pickme.calendar.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetApiResponseDTO {
    private String success;

    private String message;
}
