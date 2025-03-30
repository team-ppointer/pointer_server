package com.moplus.moplus_server.domain.auth.service;

import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthProvider {
    KAKAO("KAKAO"),
    APPLE("APPLE"),
    ;
    private final String value;

    public static OAuthProvider from(String provider) {
        return switch (provider.toUpperCase()) {
            case "APPLE" -> APPLE;
            case "KAKAO" -> KAKAO;
            default -> throw new InvalidValueException(ErrorCode.INVALID_PROVIDER_TYPE);
        };
    }
}
