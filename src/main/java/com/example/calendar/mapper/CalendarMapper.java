package com.example.calendar.mapper;

import com.example.calendar.dto.GetInterviewListDto;
import com.example.calendar.dto.InterviewScheduleDto;
import com.example.calendar.entity.Calendar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring") // Spring Bean으로 등록
public interface CalendarMapper {

    // InterviewScheduleDto 객체를 Calendar 엔티티로 변환합니다.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userInfo", ignore = true)
    void toCalendarEntity(InterviewScheduleDto interviewScheduleDto, @MappingTarget Calendar calendar);

    // Calendar 엔티티를 InterviewScheduleDto 객체로 변환합니다.
    InterviewScheduleDto toInterviewScheduleDto(Calendar calendar);

    // Calendar 엔티티 리스트를 GetInterviewListDto 리스트로 변환합니다.
    List<GetInterviewListDto> toGetInterviewListDto(List<Calendar> calendarList);
}
