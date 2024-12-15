package com.pickme.calendar.dto.delete;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteApiResponseDTO {
    private String success;

    private String message;
}
