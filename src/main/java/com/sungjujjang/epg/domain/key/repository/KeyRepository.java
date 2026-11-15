package com.sungjujjang.epg.domain.key.repository;

import com.sungjujjang.epg.domain.auth.entity.Member;
import com.sungjujjang.epg.domain.key.entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeyRepository extends JpaRepository<Key, Long> {
    List<Key> findByMember(Member member);
}
