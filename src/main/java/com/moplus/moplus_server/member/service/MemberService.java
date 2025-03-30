package com.moplus.moplus_server.member.service;

import com.moplus.moplus_server.member.domain.Member;
import com.moplus.moplus_server.member.domain.OauthInfo;
import com.moplus.moplus_server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member getMemberByOAuthInfo(OauthInfo oauthInfo) {
        return memberRepository.findByOauthInfo(oauthInfo)
                .orElseGet(() -> createMember(oauthInfo));
    }

    private Member createMember(OauthInfo oauthInfo) {
        Member member = Member.createDefaultOAuthMember(oauthInfo);
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmailOrThrow(email);
    }

    @Transactional(readOnly = true)
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }
}
