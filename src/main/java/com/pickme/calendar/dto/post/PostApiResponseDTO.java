package com.pickme.calendar.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "일정 생성&추가 시 응답")
public class PostApiResponseDTO {
    @Schema(description = "success", example = "true")
    private String success;
    @Schema(description = "message", example = "면접 일정 추가 성공")
    private String message;
    @Schema(description = "interviewDetailId", example = "fd3c55f5-07fe-4d0c-94d3-69cbaeb2646c")
    private String interviewDetailId;
}
