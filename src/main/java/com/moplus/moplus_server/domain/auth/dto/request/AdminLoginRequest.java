package com.moplus.moplus_server.domain.auth.dto.request;

public record AdminLoginRequest(
        String email,
        String password
) {
}
