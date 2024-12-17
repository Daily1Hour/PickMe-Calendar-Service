package com.pickme.calendar.dto.get;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class GetInterviewDetailDTO {
    @Schema(description = "interviewDetailId", example = "fd3c55f5-07fe-4d0c-94d3-69cbaeb2646c")
    private String interviewDetailId;

    private GetCompanyDTO company;
    @Schema(description = "interviewTime", example = "2024-12-01T02:49:03.465+00:00")
    private Date interviewTime;
    @Schema(description = "position", example = "Web Application")
    private String position;
    @Schema(description = "category", example = "1차 기술 면접")
    private String category;
    @Schema(description = "description", example = "면접 시간 15분 전에 도착하기")
    private String description;

}
