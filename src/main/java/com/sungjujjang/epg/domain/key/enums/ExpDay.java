package com.sungjujjang.epg.domain.key.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExpDay {
    DAY_30(30),
    DAY_90(90),
    DAY_365(365),
    DAY_999(999);

    private final int day;
}
