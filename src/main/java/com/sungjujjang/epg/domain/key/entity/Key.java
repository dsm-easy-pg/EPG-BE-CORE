package com.sungjujjang.epg.domain.key.entity;

import com.sungjujjang.epg.domain.auth.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "api_keys")
public class Key {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hashedKey; // hash 하세욤!

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    @Column(nullable = false)
    private LocalDate expDate;
}
