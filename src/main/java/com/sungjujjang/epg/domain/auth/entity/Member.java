package com.sungjujjang.epg.domain.auth.entity;

import com.sungjujjang.epg.domain.auth.enums.Role;
import com.sungjujjang.epg.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String password;

    public static Member create(String username, String password, Role role) {
        Member member = new Member();
        member.username = username;
        member.password = password;
        member.role = role;
        return member;
    }
}
