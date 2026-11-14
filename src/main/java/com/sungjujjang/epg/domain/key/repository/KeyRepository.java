package com.sungjujjang.epg.domain.key.repository;

import com.sungjujjang.epg.domain.key.entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyRepository extends JpaRepository<Key, Long> {
}
