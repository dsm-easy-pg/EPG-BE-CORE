package com.sungjujjang.epg.domain.auth.repository;

import com.sungjujjang.epg.domain.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
