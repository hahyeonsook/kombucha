//package com.kombucha.filter;
//
//import com.kombucha.component.util.JwtUtil;
//import io.jsonwebtoken.JwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.web.csrf.InvalidCsrfTokenException;
//import org.springframework.security.web.csrf.MissingCsrfTokenException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class JwtAuthorizationFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        List<String> unauthApiList = Arrays.asList("/api/v1/users/login", "/api/v1/users/signup");
//
//        if (unauthApiList.contains(request.getRequestURI())) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String header = request.getHeader("Authorization");
//        try {
//            if (header != null && !header.equalsIgnoreCase("")) {
//                String token = JwtUtil.getTokenFromHeader(header);
//                if (JwtUtil.isValidToken(token)) {
//                    String email = JwtUtil.getEmailFromToken(token);
//                    if (email != null && !email.equalsIgnoreCase("")) {
//                        filterChain.doFilter(request, response);
//                    } else {
//                        throw new JwtException("TOKEN isn't email");
//                    }
//                }
//            } else {
//                throw new JwtException("Token is invalid");
//            }
//        } catch (Exception e) {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json");
//        }
//    }
//}
