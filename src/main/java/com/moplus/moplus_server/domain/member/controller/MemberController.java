package com.moplus.moplus_server.domain.member.controller;

import com.moplus.moplus_server.domain.member.domain.Member;
import com.moplus.moplus_server.domain.member.dto.response.MemberGetResponse;
import com.moplus.moplus_server.global.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원", description = "회원 관련 API")
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("me")
    @Operation(summary = "내 정보 조회", description = "jwt accessToken을 통해 내 정보를 조회합니다.")
    public ResponseEntity<MemberGetResponse> getMyInfo(
            @AuthUser Member member
    ) {
        return ResponseEntity.ok(MemberGetResponse.of(member));
    }
}