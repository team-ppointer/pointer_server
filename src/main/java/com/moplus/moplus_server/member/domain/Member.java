package com.moplus.moplus_server.member.domain;

import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Embedded
    private OauthInfo oauthInfo;

    @Builder
    public Member(String name, String email, String password, MemberRole role, OauthInfo oauthInfo) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.oauthInfo = oauthInfo;
    }

    public static Member createDefaultOAuthMember(OauthInfo oauthInfo) {
        return Member.builder()
                .role(MemberRole.USER)
                .oauthInfo(oauthInfo)
                .build();
    }

    public boolean isMatchingPassword(String password) {
        return this.password.equals(password);
    }
}
