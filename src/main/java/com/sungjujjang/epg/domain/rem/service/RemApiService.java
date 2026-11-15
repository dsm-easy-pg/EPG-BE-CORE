package com.sungjujjang.epg.domain.rem.service;

import com.sungjujjang.epg.domain.auth.entity.Member;
import com.sungjujjang.epg.domain.auth.repository.MemberRepository;
import com.sungjujjang.epg.domain.key.entity.Key;
import com.sungjujjang.epg.domain.key.repository.KeyRepository;
import com.sungjujjang.epg.domain.rem.entity.Remittance;
import com.sungjujjang.epg.domain.rem.repository.RemittanceRepository;
import com.sungjujjang.epg.global.exception.BusinessException;
import com.sungjujjang.epg.global.exception.ErrorCode;
import com.sungjujjang.epg.global.response.ApiResponse;
import com.sungjujjang.epg.global.util.HmacUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RemApiService {

    private final RemittanceRepository remittanceRepository;
    private final KeyRepository keyRepository;
    private final HmacUtil hmacUtil;

    @Transactional
    public void checkRemWithUuid(String apiKey, String uuid) {
        Key key = keyRepository.findByHashedKey(hmacUtil.hmacSha256(apiKey))
                .orElseThrow(() -> new BusinessException(ErrorCode.KEY_NOT_FOUND));

        Member member = key.getMember();

        Remittance remittance = remittanceRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException(ErrorCode.REMITTANCE_NOT_FOUND));

        if (!remittance.getMember().equals(member)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        remittance.check();
        remittanceRepository.save(remittance);
    }

    @Transactional
    public void checkRemWithName(String apiKey, String name) {
        Key key = keyRepository.findByHashedKey(hmacUtil.hmacSha256(apiKey))
                .orElseThrow(() -> new BusinessException(ErrorCode.KEY_NOT_FOUND));

        Member member = key.getMember();

        Remittance remittance = remittanceRepository.findByMemberAndName(member, name)
                .orElseThrow(() -> new BusinessException(ErrorCode.REMITTANCE_NOT_FOUND));

        remittance.check();
        remittanceRepository.save(remittance);
    }
}
