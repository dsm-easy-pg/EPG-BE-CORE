package com.sungjujjang.epg.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_VALID_DTO_ERR(400, "NOT_VALID_DTO_ERR", "유효하지 않은 요청입니다."),

    UNAUTHORIZED(401, "UNAUTHORIZED", "인증이 필요합니다."),

    FORBIDDEN(403, "FORBIDDEN", "권한이 없습니다."),

    NOT_FOUND(404, "NOT_FOUND", "리소스가 없습니다."),

    MEMBER_NOT_FOUND(404, "MEMBER_NOT_FOUND", "회원을 찾을 수 없습니다."),
    REMITTANCE_NOT_FOUND(404, "REMITTANCE_NOT_FOUND", "송금을 찾을 수 없습니다."),

    DUPLICATE_USERNAME(409, "DUPLICATE_USERNAME", "이미 사용 중인 아이디입니다."),

    WITHDRAWN_MEMBER(401, "WITHDRAWN_MEMBER", "탈퇴한 회원입니다."),

    INVALID_PASSWORD(401, "INVALID_PASSWORD", "비밀번호가 일치하지 않습니다."),

    INVALID_REFRESH_TOKEN(401, "INVALID_REFRESH_TOKEN", "유효하지 않은 리프레시 토큰입니다."),

    REFRESH_TOKEN_NOT_FOUND(401, "REFRESH_TOKEN_NOT_FOUND", "리프레시 토큰이 존재하지 않습니다."),

    INTERNAL_SERVER_ERR(500, "INTERNAL_SERVER_ERR", "서버 측 오류가 발생했습니다.");

    private Integer statusCode;
    private String errorCode;
    private String errorMessage;
}
