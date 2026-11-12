package com.sungjujjang.epg.domain.auth.controller;

import com.sungjujjang.epg.domain.auth.dto.LoginRequest;
import com.sungjujjang.epg.domain.auth.dto.ReissueRequest;
import com.sungjujjang.epg.domain.auth.dto.SignupRequest;
import com.sungjujjang.epg.domain.auth.dto.TokenResponse;
import com.sungjujjang.epg.domain.auth.service.AuthService;
import com.sungjujjang.epg.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse<Void> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ApiResponse.ok();
    }

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.ok(authService.login(request));
    }

    @PostMapping("/reissue")
    public ApiResponse<TokenResponse> reissue(@Valid @RequestBody ReissueRequest request) {
        return ApiResponse.ok(authService.reissue(request.refreshToken()));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(Authentication authentication) {
        authService.logout(Long.valueOf(authentication.getName()));
        return ApiResponse.ok();
    }

    @DeleteMapping("/withdraw")
    public ApiResponse<Void> withdraw(Authentication authentication) {
        authService.withdraw(Long.valueOf(authentication.getName()));
        return ApiResponse.ok();
    }
}
