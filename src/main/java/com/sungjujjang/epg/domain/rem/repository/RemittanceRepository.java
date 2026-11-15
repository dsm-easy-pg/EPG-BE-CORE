package com.sungjujjang.epg.domain.rem.repository;

import com.sungjujjang.epg.domain.auth.entity.Member;
import com.sungjujjang.epg.domain.rem.entity.Remittance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RemittanceRepository extends JpaRepository<Remittance, Long> {
    Optional<Remittance> findByUuid(String uuid);
    Optional<Remittance> findByMemberAndName(Member member, String name);
}
