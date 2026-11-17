package com.sungjujjang.epg.domain.key.repository;

import com.sungjujjang.epg.domain.auth.entity.Member;
import com.sungjujjang.epg.domain.key.entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeyRepository extends JpaRepository<Key, Long> {
    List<Key> findByMember(Member member);

    Optional<Key> findByHashedKey(String hashedKey);
}
