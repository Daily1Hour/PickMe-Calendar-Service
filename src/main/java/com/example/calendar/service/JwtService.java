package com.example.calendar.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;

@Service
@Slf4j
public class JwtService {
    // JWT토큰 처리하는 로직
    public String extractToken(String token){

        token = token.replace("Bearer ", "");
        String[] tokenParts = token.split("\\.");

        // Payload 디코딩
        String payload = new String(Base64.getUrlDecoder().decode(tokenParts[1]));

        // JSON 파싱
        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> payloadMap = jsonParser.parseMap(payload);

        // client_id 추출
        String clientId = (String) payloadMap.get("client_id");


        return clientId;
    }

}
