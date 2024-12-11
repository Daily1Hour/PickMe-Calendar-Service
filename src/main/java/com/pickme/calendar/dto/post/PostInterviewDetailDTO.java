package com.pickme.calendar.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class PostInterviewDetailDTO {

    private PostCompanyDTO company;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date interviewTime;

    @Schema(example = "바리스타")
    private String position;

    @Schema(example = "1차 기술면접")
    private String category;

    @Schema(example = "면접 전 15분 전까지 도착하기")
    private String description;

}
