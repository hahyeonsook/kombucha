package com.kombucha.component.util;

import com.kombucha.web.dto.users.UsersCreateRequestDto;
import com.kombucha.web.dto.users.UsersLoginRequestDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    @Value("${auth.token}")
    private static final String jwtSecretKey = "78zjtR5yoNurjhBgz1Oz30wy9sk5ufVtEsgf8WRdLak";

    public static String generateJwt(UsersCreateRequestDto usersCreateRequestDto) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(usersCreateRequestDto))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .setExpiration(createExpiredDate())
                .compact();
    }

    public static String generateJwt(UsersLoginRequestDto usersLoginRequestDto) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(usersLoginRequestDto))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .setExpiration(createExpiredDate())
                .compact();
    }

    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static String getEmailFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return claims.get("email").toString();
    }

    private static Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecretKey)
                .parseClaimsJws(token).getBody();
    }

    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private static Map<String, Object> createClaims(UsersCreateRequestDto usersCreateRequestDto) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("email", usersCreateRequestDto.getEmail());
        return claims;
    }

    private static Map<String, Object> createClaims(UsersLoginRequestDto usersLoginRequestDto) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("email", usersLoginRequestDto.getEmail());
        return claims;
    }

    private static Date createExpiredDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 8);     // 8시간
        return calendar.getTime();
    }
}
