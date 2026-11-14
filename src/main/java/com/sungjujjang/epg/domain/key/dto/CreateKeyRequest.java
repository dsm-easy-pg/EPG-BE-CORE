package com.sungjujjang.epg.domain.key.dto;

import com.sungjujjang.epg.domain.key.enums.ExpDay;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateKeyRequest(
        @NotNull(message = "유효기간은 필수 입력 값입니다.")
        ExpDay expDay,

        @NotBlank(message = "별창은 필수 입력 값입니다.")
        String name
) {
}
