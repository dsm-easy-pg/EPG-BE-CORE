package com.sungjujjang.epg.domain.auth.controller;

import com.sungjujjang.epg.domain.auth.dto.AccessTokenResponse;
import com.sungjujjang.epg.domain.auth.dto.LoginRequest;
import com.sungjujjang.epg.domain.auth.dto.SignupRequest;
import com.sungjujjang.epg.domain.auth.dto.TokenResponse;
import com.sungjujjang.epg.domain.auth.service.AuthService;
import com.sungjujjang.epg.global.response.ApiResponse;
import com.sungjujjang.epg.global.security.jwt.RefreshTokenCookieProvider;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CookieValue;
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
    private final RefreshTokenCookieProvider refreshTokenCookieProvider;

    @PostMapping("/signup")
    public ApiResponse<Void> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ApiResponse.ok();
    }

    @PostMapping("/login")
    public ApiResponse<AccessTokenResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        TokenResponse tokens = authService.login(request);
        setRefreshTokenCookie(response, tokens.refreshToken());
        return ApiResponse.ok(new AccessTokenResponse(tokens.accessToken()));
    }

    @PostMapping("/reissue")
    public ApiResponse<AccessTokenResponse> reissue(
            @CookieValue(name = RefreshTokenCookieProvider.REFRESH_TOKEN_COOKIE, required = false) String refreshToken,
            HttpServletResponse response
    ) {
        TokenResponse tokens = authService.reissue(refreshToken);
        setRefreshTokenCookie(response, tokens.refreshToken());
        return ApiResponse.ok(new AccessTokenResponse(tokens.accessToken()));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(Authentication authentication, HttpServletResponse response) {
        authService.logout(Long.valueOf(authentication.getName()));
        expireRefreshTokenCookie(response);
        return ApiResponse.ok();
    }

    @DeleteMapping("/withdraw")
    public ApiResponse<Void> withdraw(Authentication authentication, HttpServletResponse response) {
        authService.withdraw(Long.valueOf(authentication.getName()));
        expireRefreshTokenCookie(response);
        return ApiResponse.ok();
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        response.addHeader(
                HttpHeaders.SET_COOKIE,
                refreshTokenCookieProvider.create(refreshToken).toString()
        );
    }

    private void expireRefreshTokenCookie(HttpServletResponse response) {
        response.addHeader(
                HttpHeaders.SET_COOKIE,
                refreshTokenCookieProvider.expire().toString()
        );
    }
}
