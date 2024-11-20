package com.example.calendar.extract;


import com.example.calendar.exception.CustomException;
import com.example.calendar.exception.ErrorCode;

public class ExtractToken {
    // JWT토큰 처리하는 로직
    public static String extractToken(String token){
        String[] parts = token.split(" ");
        if(parts[0] != "Bearer"){
            throw new CustomException(ErrorCode.NOT_Bearer, "Bearer 토큰 방식이 아닙니다.");
        }
        return parts[1];
    }

}
