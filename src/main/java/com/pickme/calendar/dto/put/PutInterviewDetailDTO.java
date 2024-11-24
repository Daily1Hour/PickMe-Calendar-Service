package com.pickme.calendar.dto.put;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PutInterviewDetailDTO {

    private PutCompanyDTO company;

    private LocalDateTime interviewTime;

    @Schema(example = "수정된 직무")
    private String position;

    @Schema(example = "수정된 면접 유형")
    private String category;

    @Schema(example = "수정된 사항")
    private String description;
}
