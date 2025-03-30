package com.moplus.moplus_server.domain.auth.dto.response;

import com.moplus.moplus_server.member.domain.Member;

public record LoginResponse(
        Long memberId,
        String name,
        String email,
        String accessToken,
        String refreshToken
) {

    public static LoginResponse of(Member member, TokenResponse tokenPairResponse) {
        return new LoginResponse(member.getId(), member.getName(), member.getOauthInfo().getOauthEmail(),
                tokenPairResponse.accessToken(),
                tokenPairResponse.refreshToken());
    }
}
