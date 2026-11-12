package com.sungjujjang.epg.global.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RefreshTokenCookieProvider {

    public static final String REFRESH_TOKEN_COOKIE = "refresh_token";

    private static final String COOKIE_PATH = "/api/auth";

    private final long refreshTokenExpiration;
    private final boolean secure;
    private final String sameSite;

    public RefreshTokenCookieProvider(
            @Value("${jwt.refresh_expiration_time}") long refreshTokenExpiration,
            @Value("${cookie.secure:false}") boolean secure,
            @Value("${cookie.same-site:Lax}") String sameSite
    ) {
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.secure = secure;
        this.sameSite = sameSite;
    }

    public ResponseCookie create(String refreshToken) {
        return baseCookie(refreshToken)
                .maxAge(Duration.ofMillis(refreshTokenExpiration))
                .build();
    }

    public ResponseCookie expire() {
        return baseCookie("")
                .maxAge(Duration.ZERO)
                .build();
    }

    private ResponseCookie.ResponseCookieBuilder baseCookie(String value) {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE, value)
                .httpOnly(true)
                .secure(secure)
                .path(COOKIE_PATH)
                .sameSite(sameSite);
    }
}
