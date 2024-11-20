package com.example.calendar.extract;

import org.springframework.stereotype.Service;

@Service
public class ExtractToken {
    // JWT토큰 처리하는 로직
    public String extractToken(String token){
        String[] parts = token.split(" ");
        return parts[1];
    }

}
