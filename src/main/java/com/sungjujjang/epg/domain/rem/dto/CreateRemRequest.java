package com.sungjujjang.epg.domain.rem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateRemRequest(
        @NotNull(message = "금액은 필수 입력 값입니다.")
        @Positive(message = "금액은 양수만 입력 가능합니다.")
        int amount
) {
}
