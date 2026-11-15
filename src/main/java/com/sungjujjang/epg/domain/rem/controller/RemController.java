package com.sungjujjang.epg.domain.rem.controller;

import com.sungjujjang.epg.domain.rem.dto.CreateRemRequest;
import com.sungjujjang.epg.domain.rem.dto.CreateRemResponse;
import com.sungjujjang.epg.domain.rem.dto.GetRemResponse;
import com.sungjujjang.epg.domain.rem.dto.RemittanceDto;
import com.sungjujjang.epg.domain.rem.service.RemService;
import com.sungjujjang.epg.global.annotations.CurrentUserId.CurrentUserId;
import com.sungjujjang.epg.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    @GetMapping
    public ApiResponse<Page<RemittanceDto>> getRemList(
            @Parameter(hidden = true) @CurrentUserId
            Long userId,

            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate,

            @RequestParam(required = false)
            Integer minAmount,

            @RequestParam(required = false)
            Boolean isChecked,

            @RequestParam(required = false)
            Integer page
    ) {
        Page<RemittanceDto> remittanceDtoPage =
                remService.getRemList(
                        startDate,
                        endDate,
                        minAmount,
                        isChecked,
                        page,
                        userId
                );

        return ApiResponse.ok(remittanceDtoPage);
    }
}
