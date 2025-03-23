package com.moplus.moplus_server.client.homefeed.controller;

import com.moplus.moplus_server.client.homefeed.dto.response.HomeFeedResponse;
import com.moplus.moplus_server.client.homefeed.service.HomeFeedFacadeService;
import com.moplus.moplus_server.global.annotation.AuthUser;
import com.moplus.moplus_server.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "홈 피드 조회", description = "홈 피드 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client/home-feed")
public class HomeFeedController {

    private final HomeFeedFacadeService homeFeedFacadeService;

    @Operation(summary = "홈 피드 조회", description = "회원의 홈 피드 정보를 조회합니다.")
    @GetMapping("")
    public HomeFeedResponse getHomeFeed(@AuthUser Member member) {
        return homeFeedFacadeService.getHomeFeed(member);
    }
}
