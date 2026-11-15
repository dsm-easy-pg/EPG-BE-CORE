package com.sungjujjang.epg.domain.key.dto;

import com.sungjujjang.epg.domain.key.entity.Key;

import java.time.LocalDate;

public record KeyDto(
        Long id,
        String name,
        LocalDate expDate
) {
    public static KeyDto from(Key key) {
        return new KeyDto(
                key.getId(),
                key.getName(),
                key.getExpDate()
        );
    }
}
