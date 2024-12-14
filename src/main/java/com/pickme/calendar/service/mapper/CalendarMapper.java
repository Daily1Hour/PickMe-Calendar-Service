package com.pickme.calendar.service.mapper;

import com.pickme.calendar.dto.get.GetCalendarDTO;
import com.pickme.calendar.dto.get.GetInterviewDetailDTO;
import com.pickme.calendar.dto.post.PostInterviewDetailDTO;
import com.pickme.calendar.dto.put.PutInterviewDetailDTO;
import com.pickme.calendar.entity.Calendar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring") // Spring Bean으로 등록
public interface CalendarMapper {

    // 전달받은 DTO(PostInterviewDetailDTO)를 InterviewDetails 객체로 변환
    @Mapping(target = "interviewDetailId", ignore = true)
    void postInterviewDetailDtoToInterviewDetails (PostInterviewDetailDTO postInterviewDetailDto, @MappingTarget Calendar.InterviewDetails interviewDetails);

    // Calendar 엔티티의 정보를 GetCalendarDTO로 매핑 (id, clientId)
    @Mapping(target = "interviewDetails", ignore = true)
    void calendarToGetCalendarDTO (Calendar calendar, @MappingTarget GetCalendarDTO getCalendarDTO);

    // interviewDetails 리스트를 GetInterviewDetailDTO 객체로 변환
    List<GetInterviewDetailDTO> interviewDetailsToGetInterviewDetailsDTO (List<Calendar.InterviewDetails> interviewDetails);

    // interviewDetail을 GetInterviewDetailDTO 객체로 변환
    GetInterviewDetailDTO interviewDetailToGetInterviewDetailDTO (Calendar.InterviewDetails interviewDetails);

    // 전달받은 DTO(PutInterviewDetailDTO)를 InterviewDetails 객체로 변환
    @Mapping(target = "interviewDetailId", ignore = true)
    void putInterviewDetailDtoTOInterviewDetail (PutInterviewDetailDTO putInterviewDetailDTO, @MappingTarget Calendar.InterviewDetails interviewDetails);
}
