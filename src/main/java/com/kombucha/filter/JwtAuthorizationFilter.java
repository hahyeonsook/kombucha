package com.kombucha.filter;

import com.google.gson.Gson;
import com.kombucha.common.ErrorCode;
import com.kombucha.component.util.JwtUtil;
import com.kombucha.domain.jwt.JwtToken;
import com.kombucha.service.users.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.kombucha.common.constants.AuthConstants.TOKEN_TYPE;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UsersService usersService;
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        List<String> unauthApiList = Arrays.asList("/api/v1/user/login", "/api/v1/user/signup");

        if (unauthApiList.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        try {
            if (header == null || !header.startsWith(TOKEN_TYPE)) {
                throw new Exception("올바르지 않은 토큰입니다.");
            }
            String token = jwtUtil.getTokenFromHeader(header);
            JwtToken jwtToken = usersService.findByTokenExpiredFalse(token);
            if (jwtToken == null || !token.equals(jwtToken.getToken()) || !jwtUtil.isValidToken(token)) {
                throw new Exception("올바르지 않은 토큰입니다.");
            }

            String email = jwtUtil.getEmailFromToken(token);
            if (email == null || email.equalsIgnoreCase("")) {
                throw new Exception("올바르지 않은 토큰입니다.");
            }
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.print(jsonErrorResponseWrapper(ErrorCode.INVALID_TOKEN, exception));
            printWriter.flush();
            printWriter.close();
        }
    }

    private String jsonErrorResponseWrapper(ErrorCode errorCode, Exception exception) {
        HashMap<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("status", errorCode.getStatus());
        jsonMap.put("code", errorCode.getStatus());
        jsonMap.put("error",exception.getMessage());
        jsonMap.put("message", errorCode.getMessage());
        return new Gson().toJson(jsonMap);
    }
}
