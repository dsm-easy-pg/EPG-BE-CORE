package com.sungjujjang.epg.domain.rem.controller;

import com.sungjujjang.epg.domain.rem.service.RemApiService;
import com.sungjujjang.epg.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rem/v1")
@RequiredArgsConstructor
public class RemApiController {

    private final RemApiService remApiService;

    @PostMapping("/uuid/{uuid}")
    public ApiResponse<Void> checkRemWithUuid(
            @RequestHeader("X-Api-Key") String apiKey,
            @PathVariable String uuid
    ) {
        remApiService.checkRemWithUuid(apiKey, uuid);
        return ApiResponse.ok();
    }

    @PostMapping("/name/{name}")
    public ApiResponse<Void> checkRemWithName(
            @RequestHeader("X-Api-Key") String apiKey,
            @PathVariable String name
    ) {
        remApiService.checkRemWithName(apiKey, name);
        return ApiResponse.ok();
    }
}
