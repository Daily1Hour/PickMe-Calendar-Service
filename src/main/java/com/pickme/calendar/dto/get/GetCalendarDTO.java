package com.pickme.calendar.dto.get;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "전체 조회 & 조건 조회 시 응답")
public class GetCalendarDTO {
    @Schema(description = "clientId", example = "60011bt0ev46lpb51pbnpugpn7")
    private String clientId;
    private List<GetInterviewDetailDTO> interviewDetails;
}
