package com.moplus.moplus_server.domain.auth.dto.response.oauth.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoAuthResponse(
        Long id,
        @JsonProperty("kakao_account") KakaoAccountResponse kakaoAccount,
        @JsonProperty("properties") PropertiesResponse properties) {
    public record KakaoAccountResponse(String email) {
    }

    public record PropertiesResponse(
            String nickname, String profile_image, String thumbnail_image) {
    }
}
