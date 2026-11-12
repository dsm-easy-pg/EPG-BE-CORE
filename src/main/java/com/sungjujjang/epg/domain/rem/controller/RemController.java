package com.sungjujjang.epg.domain.rem.controller;

import com.sungjujjang.epg.domain.rem.dto.CreateRemRequest;
import com.sungjujjang.epg.domain.rem.dto.CreateRemResponse;
import com.sungjujjang.epg.domain.rem.dto.GetRemResponse;
import com.sungjujjang.epg.domain.rem.service.RemService;
import com.sungjujjang.epg.global.annotations.CurrentUserId.CurrentUserId;
import com.sungjujjang.epg.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rem")
@RequiredArgsConstructor
public class RemController {

    private final RemService remService;

    @PostMapping
    public ApiResponse<CreateRemResponse> createRem(
            @Valid @RequestBody CreateRemRequest createRemRequest,
            @Parameter(hidden = true) @CurrentUserId Long userId
    ) {
        CreateRemResponse createRemResponse =
                remService.createRem(userId, createRemRequest.amount());

        return ApiResponse.ok(createRemResponse);
    }

    @GetMapping("/{uuid}")
    public ApiResponse<GetRemResponse> getRem(
            @PathVariable String uuid,
            @Parameter(hidden = true) @CurrentUserId Long userId
    ) {
        GetRemResponse getRemResponse =
                remService.getRem(userId, uuid);

        return ApiResponse.ok(getRemResponse);
    }
}
