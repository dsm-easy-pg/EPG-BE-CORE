package com.sungjujjang.epg.domain.key.controller;

import com.sungjujjang.epg.domain.key.dto.CreateKeyRequest;
import com.sungjujjang.epg.domain.key.dto.CreateKeyResponse;
import com.sungjujjang.epg.domain.key.dto.GetKeyListResponse;
import com.sungjujjang.epg.domain.key.service.KeyService;
import com.sungjujjang.epg.global.annotations.CurrentUserId.CurrentUserId;
import com.sungjujjang.epg.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/key")
@RequiredArgsConstructor
public class KeyController {

    private final KeyService keyService;

    @PostMapping
    public ApiResponse<CreateKeyResponse> createKey(
            @Valid @RequestBody CreateKeyRequest createKeyRequest,
            @Parameter(hidden = true) @CurrentUserId Long userId
    ) {
        CreateKeyResponse createKeyResponse =
                keyService.createKey(userId, createKeyRequest);
        return ApiResponse.ok(createKeyResponse);
    }

    @GetMapping
    public ApiResponse<GetKeyListResponse> getKeyList(
            @Parameter(hidden = true) @CurrentUserId Long userId
    ) {
        GetKeyListResponse getKeyListResponse = keyService.getKeyList(userId);
        return ApiResponse.ok(getKeyListResponse);
    }

    @DeleteMapping("/{keyId}")
    public ApiResponse<Void> deleteKey(
            @Parameter(hidden = true) @CurrentUserId Long userId,
            @PathVariable Long keyId
    ) {
        keyService.deleteKey(userId, keyId);
        return ApiResponse.ok();
    }
}
