package com.kombucha.component.util;

import com.kombucha.domain.users.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${auth.token}")
    private String JWT_SECRET_KEY;

    public String generateJwt(Users user) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .signWith(createSignature())
                .setExpiration(createExpiredDate())
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            return true;
        } catch (JwtException | NullPointerException exception) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return claims.get("email").toString();
    }

    private Key createSignature() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(JWT_SECRET_KEY);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(JWT_SECRET_KEY))
                .parseClaimsJws(token).getBody();
    }

    public String getTokenFromHeader(String header) {
        return header.substring(7);
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private Map<String, Object> createClaims(Users user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("email", user.getEmail());
        return claims;
    }

    private Date createExpiredDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 8);     // 8시간
        return calendar.getTime();
    }
}
