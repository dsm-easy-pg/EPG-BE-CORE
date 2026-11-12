package com.sungjujjang.epg.domain.rem.entity;

import com.sungjujjang.epg.domain.auth.entity.Member;
import com.sungjujjang.epg.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "remittances")
public class Remittance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false)
    private boolean checked;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;
}
