package com.example.calendar.extract;


public class ExtractToken {
    // JWT토큰 처리하는 로직
    public static String extractToken(String token){
        String[] parts = token.split(" ");
        return parts[1];
    }

}
