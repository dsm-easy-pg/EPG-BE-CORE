package com.sungjujjang.epg.domain.rem.controller;

import com.sungjujjang.epg.domain.rem.dto.CreateRemRequest;
import com.sungjujjang.epg.domain.rem.dto.CreateRemResponse;
import com.sungjujjang.epg.domain.rem.dto.GetRemCheckedResponse;
import com.sungjujjang.epg.domain.rem.dto.GetRemResponse;
import com.sungjujjang.epg.domain.rem.service.RemApiService;
import com.sungjujjang.epg.global.annotations.CurrentUserId.CurrentUserId;
import com.sungjujjang.epg.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rem/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
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

    @GetMapping("/{uuid}")
    public ApiResponse<GetRemCheckedResponse> getRemChecked(
            @RequestHeader("X-Api-Key") String apiKey,
            @PathVariable String uuid
    ) {
        GetRemCheckedResponse getRemCheckedResponse =
                remApiService.getRemChecked(apiKey, uuid);
        return ApiResponse.ok(getRemCheckedResponse);
    }

    @PostMapping
    public ApiResponse<CreateRemResponse> createRem(
            @Valid @RequestBody CreateRemRequest createRemRequest,
            @RequestHeader("X-Api-Key") String apiKey
    ) {
        CreateRemResponse createRemResponse =
                remApiService.createRem(apiKey, createRemRequest.amount());

        return ApiResponse.ok(createRemResponse);
    }

    @GetMapping("/detail/{uuid}")
    public ApiResponse<GetRemResponse> getRem(
            @PathVariable String uuid,
            @RequestHeader("X-Api-Key") String apiKey
    ) {
        GetRemResponse getRemResponse =
                remApiService.getRem(apiKey, uuid);

        return ApiResponse.ok(getRemResponse);
    }
}
