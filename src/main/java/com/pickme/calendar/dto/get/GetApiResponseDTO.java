package com.pickme.calendar.dto.get;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetApiResponseDTO {
    @Schema(description = "success", example = "false")
    private String success;
    @Schema(description = "message", example = "interviewDetailId에 해당하는 면접 일정이 없습니다.")
    private String message;
}
