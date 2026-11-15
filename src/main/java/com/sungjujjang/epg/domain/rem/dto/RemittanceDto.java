package com.sungjujjang.epg.domain.rem.dto;

import com.sungjujjang.epg.domain.rem.entity.Remittance;

public record RemittanceDto(
        String uuid,
        int amount,
        boolean checked,
        String name
) {

    public static RemittanceDto of(Remittance remittance) {
        return new RemittanceDto(
                remittance.getUuid(),
                remittance.getAmount(),
                remittance.isChecked(),
                remittance.getName()
        );
    }
}
