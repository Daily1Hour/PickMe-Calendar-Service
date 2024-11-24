package com.pickme.calendar.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "calendar")
@Data
@NoArgsConstructor
public class Calendar {

    @Id
    private String id;

    private String clientId;

    private List<InterviewDetails> interviewDetails;

    @Data
    @NoArgsConstructor
    public static class InterviewDetails{

        private String interviewDetailId;

        private Company company;

        private LocalDateTime interviewTime;

        private String position;

        private String category;

        private String description;
    }

    @Data
    @NoArgsConstructor
    public static class Company {

        private String name;

        private String location;

    }
}
