package com.pickme.calendar.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostInterviewDetailDTO {

    private PostCompanyDTO company;

    private LocalDateTime interviewTime;

    @Schema(example = "바리스타")
    private String position;

    @Schema(example = "1차 기술면접")
    private String category;

    @Schema(example = "면접 전 15분 전까지 도착하기")
    private String description;

}
