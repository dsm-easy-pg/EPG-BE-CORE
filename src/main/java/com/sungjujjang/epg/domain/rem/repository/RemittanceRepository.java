package com.sungjujjang.epg.domain.rem.repository;

import com.sungjujjang.epg.domain.auth.entity.Member;
import com.sungjujjang.epg.domain.rem.dto.GetRemMetricResponse;
import com.sungjujjang.epg.domain.rem.entity.Remittance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RemittanceRepository extends JpaRepository<Remittance, Long> {
    Optional<Remittance> findByUuid(String uuid);
    Optional<Remittance> findByMemberAndName(Member member, String name);

    @Query("""
        SELECT r FROM Remittance r
        WHERE (:startDate IS NULL OR r.createdAt >= :startDate)
          AND (:endDate IS NULL OR r.createdAt <= :endDate)
          AND (:minAmount IS NULL OR r.amount >= :minAmount)
          AND (:isChecked IS NULL OR r.checked = :isChecked)
          AND (r.member = :member)
    """)
    Page<Remittance> search(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("minAmount") Integer minAmount,
            @Param("isChecked") Boolean isChecked,
            @Param("member") Member member,
            Pageable pageable
    );

    @Query("""
        SELECT new com.sungjujjang.epg.domain.rem.dto.GetRemMetricResponse(
            COUNT(r),
            COALESCE(SUM(r.amount), 0L)
        ) FROM Remittance r
        WHERE (:startDate IS NULL OR r.createdAt >= :startDate)
          AND (:endDate IS NULL OR r.createdAt <= :endDate)
          AND (:minAmount IS NULL OR r.amount >= :minAmount)
          AND (:isChecked IS NULL OR r.checked = :isChecked)
          AND (r.member = :member)
    """)
    GetRemMetricResponse getMetric(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("minAmount") Integer minAmount,
            @Param("isChecked") Boolean isChecked,
            @Param("member") Member member
    );
}
