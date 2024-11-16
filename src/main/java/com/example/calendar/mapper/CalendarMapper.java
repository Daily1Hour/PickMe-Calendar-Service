package com.example.calendar.mapper;

import com.example.calendar.dto.InterviewScheduleDto;
import com.example.calendar.entity.Calendar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring") // Spring Bean으로 등록
public interface CalendarMapper {

    // InterviewScheduleDto -> Calendar
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userInfo", ignore = true)
    void toCalendarEntity(InterviewScheduleDto interviewScheduleDto, @MappingTarget Calendar calendar);

    // Calendar -> InterviewScheduleDto
    InterviewScheduleDto toInterviewScheduleDto(Calendar calendar);
}
