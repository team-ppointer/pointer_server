package com.moplus.moplus_server.domain.auth.dto.response;

import com.moplus.moplus_server.member.domain.Member;

public record LoginResponse(
        Long memberId,
        String email,
        String accessToken,
        String refreshToken
) {

    public static LoginResponse of(Member member, TokenResponse tokenPairResponse) {
        return new LoginResponse(member.getId(), member.getOauthInfo().getOauthEmail(), tokenPairResponse.accessToken(),
                tokenPairResponse.refreshToken());
    }
}
