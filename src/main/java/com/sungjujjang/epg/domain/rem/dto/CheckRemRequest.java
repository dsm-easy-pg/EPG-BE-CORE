package com.sungjujjang.epg.domain.rem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CheckRemRequest(
        @NotNull(message = "금액은 필수 입력 값입니다.")
        @Min(value = 2, message = "최소 금액은 2원 이상입니다.")
        int amount
) {
}
