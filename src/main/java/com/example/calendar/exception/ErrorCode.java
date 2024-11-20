package com.example.calendar.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // enum은 ,(콤마)로 구분하고 마지막에만 ;(세미클론)
    NULL_USERINFO(HttpStatus.CONFLICT, ""),
    DOCUMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID에 면접 일정이 없습니다."),
    NOT_Bearer(HttpStatus.BAD_REQUEST, "Bearer 토큰 방식이 아닙니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
