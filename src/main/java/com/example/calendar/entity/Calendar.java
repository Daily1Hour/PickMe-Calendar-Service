package com.example.calendar.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "calendar")
@Data
@NoArgsConstructor
public class Calendar {

    @Id
    private String id;

    private String userInfo;

    private String location;

    private String companyName;

    private LocalDateTime interviewTime;

    private String position;

    // 설명칸, 면접 유형 추가

}
