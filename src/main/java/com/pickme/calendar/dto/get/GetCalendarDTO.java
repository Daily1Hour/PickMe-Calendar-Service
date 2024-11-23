package com.pickme.calendar.dto.get;

import lombok.Data;

import java.util.List;

@Data
public class GetCalendarDTO {
    private String id;
    private String userInfo;
    private List<GetInterviewDetailDTO> interviewDetails;
}
