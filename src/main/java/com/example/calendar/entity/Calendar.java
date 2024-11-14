package com.example.calendar.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity // 해당 클래스가 데이터베이스의 테이블과 매핑
@Data
@NoArgsConstructor
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String userInfo;

    private String location;

    private String companyName;

    private LocalDateTime interviewTime;

    private String position;

}
