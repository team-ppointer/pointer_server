package com.moplus.moplus_server.global.annotation;

import com.moplus.moplus_server.member.domain.Member;
import com.moplus.moplus_server.member.repository.MemberRepository;
import com.moplus.moplus_server.global.error.exception.BusinessException;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        final boolean isUserAuthAnnotation = parameter.getParameterAnnotation(AuthUser.class) != null;
        final boolean isMemberClass = parameter.getParameterType().equals(Member.class);
        return isUserAuthAnnotation && isMemberClass;
    }

    @Override
    public Member resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return getCurrentMember();
    }

    private Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ErrorCode.AUTH_NOT_FOUND);
        }
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof Member)) {
            throw new BusinessException(ErrorCode.INVALID_PRINCIPAL);
        }
        return (Member) principal;
    }
}
