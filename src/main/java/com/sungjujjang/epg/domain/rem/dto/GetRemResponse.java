package com.sungjujjang.epg.domain.rem.dto;

import com.sungjujjang.epg.domain.rem.entity.Remittance;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record GetRemResponse(
        Long id,
        int amount,
        boolean checked,
        String name,
        String uuid,
        LocalDateTime created_date,
        LocalDateTime checked_date
) {

    public static GetRemResponse from(Remittance remittance) {
        return new GetRemResponse(
                remittance.getId(),
                remittance.getAmount(),
                remittance.isChecked(),
                remittance.getName(),
                remittance.getUuid(),
                remittance.getCreatedAt(),
                remittance.getUpdatedAt()
        );
    }
}
