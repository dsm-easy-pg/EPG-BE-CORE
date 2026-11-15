package com.sungjujjang.epg.domain.key.dto;

import java.util.List;

public record GetKeyListResponse(
        List<KeyDto> keys
) {
}
