package com.moplus.moplus_server.domain.member.service;

import com.moplus.moplus_server.domain.member.domain.Member;
import com.moplus.moplus_server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmailOrThrow(email);
    }
}
