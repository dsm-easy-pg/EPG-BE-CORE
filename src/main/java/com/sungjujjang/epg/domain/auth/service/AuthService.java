package com.sungjujjang.epg.domain.auth.service;

import com.sungjujjang.epg.domain.auth.dto.LoginRequest;
import com.sungjujjang.epg.domain.auth.dto.SignupRequest;
import com.sungjujjang.epg.domain.auth.dto.TokenResponse;
import com.sungjujjang.epg.domain.auth.entity.Member;
import com.sungjujjang.epg.domain.auth.enums.Role;
import com.sungjujjang.epg.domain.auth.repository.MemberRepository;
import com.sungjujjang.epg.global.exception.BusinessException;
import com.sungjujjang.epg.global.exception.ErrorCode;
import com.sungjujjang.epg.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public void signup(SignupRequest request) {
        if (memberRepository.existsByUsername(request.username())) {
            throw new BusinessException(ErrorCode.DUPLICATE_USERNAME);
        }

        Member member = Member.create(
                request.username(),
                passwordEncoder.encode(request.password()),
                Role.USER
        );

        memberRepository.save(member);
    }

    public TokenResponse login(LoginRequest request) {
        Member member = memberRepository.findByUsername(request.username())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.isWithdrawn()) {
            throw new BusinessException(ErrorCode.WITHDRAWN_MEMBER);
        }

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        return issueTokens(member);
    }

    public TokenResponse reissue(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken) || !jwtProvider.isRefreshToken(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        Long memberId = jwtProvider.getMemberId(refreshToken);
        String savedRefreshToken = refreshTokenService.findByMemberId(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        if (!savedRefreshToken.equals(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.isWithdrawn()) {
            throw new BusinessException(ErrorCode.WITHDRAWN_MEMBER);
        }

        return issueTokens(member);
    }

    public void logout(Long memberId) {
        refreshTokenService.delete(memberId);
    }

    @Transactional
    public void withdraw(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.isWithdrawn()) {
            throw new BusinessException(ErrorCode.WITHDRAWN_MEMBER);
        }

        member.withdraw();
        refreshTokenService.delete(memberId);
    }

    private TokenResponse issueTokens(Member member) {
        String accessToken = jwtProvider.createAccessToken(member.getId(), member.getRole().getKey());
        String refreshToken = jwtProvider.createRefreshToken(member.getId());

        refreshTokenService.save(member.getId(), refreshToken, jwtProvider.getRefreshTokenExpiration());

        return new TokenResponse(accessToken, refreshToken);
    }
}
