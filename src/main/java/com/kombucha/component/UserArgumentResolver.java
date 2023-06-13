package com.kombucha.component;

import com.kombucha.component.util.JwtUtil;
import com.kombucha.exception.AuthenticationException;
import com.kombucha.web.dto.users.UsersRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.kombucha.common.constants.AuthConstants.AUTH_HEADER;

public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UsersRequestDto.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();

        String token = JwtUtil.getTokenFromHeader(httpServletRequest.getHeader(AUTH_HEADER));
        if (!JwtUtil.isValidToken(token)) {
          throw new AuthenticationException();
        }

        String email = JwtUtil.getEmailFromToken(token);
        return UsersRequestDto.builder().email(email).build();
    }
}
