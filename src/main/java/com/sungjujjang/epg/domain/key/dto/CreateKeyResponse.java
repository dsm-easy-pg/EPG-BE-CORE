package com.sungjujjang.epg.domain.key.dto;

import java.time.LocalDate;

public record CreateKeyResponse(
        Long id,
        String key,
        LocalDate expDate
) {
}
