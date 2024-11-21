package com.example.calendar.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    // JWT토큰 처리하는 로직
    public String extractToken(String token){
        // "Bearer " 접두사 제거
        token = token.replace("Bearer ", "");

        // JWT 토큰을 디코딩
        DecodedJWT decodedJWT = JWT.decode(token);

        // "client_id" 클레임을 문자열로 추출하여 반환
        return decodedJWT.getClaim("client_id").asString();
    }

}
