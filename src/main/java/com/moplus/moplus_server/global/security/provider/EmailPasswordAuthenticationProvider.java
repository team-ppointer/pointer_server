package com.moplus.moplus_server.global.security.provider;

import com.moplus.moplus_server.domain.member.domain.Member;
import com.moplus.moplus_server.domain.member.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class EmailPasswordAuthenticationProvider implements AuthenticationProvider {

    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        final String memberEmail = token.getName();
        final String memberPassword = (String) token.getCredentials();

        Member member = memberService.getMemberByEmail(memberEmail);
        if (!memberPassword.equals(member.getPassword())) {
            throw new BadCredentialsException(member.getEmail() + "Invalid password");
        }

        return UsernamePasswordAuthenticationToken.authenticated(
                member,
                memberPassword,
                List.of(new SimpleGrantedAuthority(member.getRole().getValue())
                ));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
