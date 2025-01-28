package com.moplus.moplus_server.domain.member.dto.response;


import com.moplus.moplus_server.domain.member.domain.Member;

public record MemberGetResponse(
        Long id,
        String name,
        String email
) {
    public static MemberGetResponse of(Member member) {
        return new MemberGetResponse(
                member.getId(),
                member.getName(),
                member.getEmail()
        );
    }
}
