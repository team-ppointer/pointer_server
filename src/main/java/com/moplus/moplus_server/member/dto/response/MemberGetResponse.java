package com.moplus.moplus_server.member.dto.response;


import com.moplus.moplus_server.member.domain.Member;

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
