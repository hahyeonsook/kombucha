package com.kombucha.filter;

import com.google.gson.Gson;
import com.kombucha.common.ErrorCode;
import com.kombucha.component.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        List<String> unauthApiList = Arrays.asList("/api/v1/user/login", "/api/v1/user/signup");

        System.out.println("요청 path" + request.getRequestURI());
        if (unauthApiList.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        try {
            if (header != null || !header.equalsIgnoreCase("")) {
                String token = JwtUtil.getTokenFromHeader(header);
                if (JwtUtil.isValidToken(token)) {
                    String email = JwtUtil.getEmailFromToken(token);
                    if (email != null || !email.equalsIgnoreCase("")) {
                        filterChain.doFilter(request, response);
                    } else {
                        throw new Exception("올바르지 않은 토큰입니다.");
                    }
                }
            }
        } catch (Exception exception) {
            System.out.println("exception" + exception);
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
