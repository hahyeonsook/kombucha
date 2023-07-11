package com.kombucha.component;

import com.kombucha.component.util.JwtUtil;
import com.kombucha.exception.AuthenticationException;
import com.kombucha.web.dto.users.UsersRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.kombucha.common.constants.AuthConstants.AUTH_HEADER;

@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UsersRequestDto.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();

        String token = jwtUtil.getTokenFromHeader(httpServletRequest.getHeader(AUTH_HEADER));
        if (!jwtUtil.isValidToken(token)) {
          throw new AuthenticationException();
        }

        String email = jwtUtil.getEmailFromToken(token);
        return UsersRequestDto.builder().email(email).build();
    }
}
