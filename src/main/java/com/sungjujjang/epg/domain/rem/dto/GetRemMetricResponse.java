package com.sungjujjang.epg.domain.rem.dto;

import java.math.BigDecimal;

public record GetRemMetricResponse(
        Long totalCount,
        Long totalAmount
) {
}
