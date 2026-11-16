package com.sungjujjang.epg.domain.rem.service;

import com.sungjujjang.epg.domain.auth.entity.Member;
import com.sungjujjang.epg.domain.auth.repository.MemberRepository;
import com.sungjujjang.epg.domain.key.entity.Key;
import com.sungjujjang.epg.domain.key.repository.KeyRepository;
import com.sungjujjang.epg.domain.rem.dto.CreateRemResponse;
import com.sungjujjang.epg.domain.rem.dto.GetRemCheckedResponse;
import com.sungjujjang.epg.domain.rem.dto.GetRemResponse;
import com.sungjujjang.epg.domain.rem.entity.Remittance;
import com.sungjujjang.epg.domain.rem.repository.RemittanceRepository;
import com.sungjujjang.epg.global.exception.BusinessException;
import com.sungjujjang.epg.global.exception.ErrorCode;
import com.sungjujjang.epg.global.response.ApiResponse;
import com.sungjujjang.epg.global.util.HmacUtil;
import com.sungjujjang.epg.global.util.RandomStringGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RemApiService {

    private final RemittanceRepository remittanceRepository;
    private final KeyRepository keyRepository;
    private final HmacUtil hmacUtil;
    private final RandomStringGenerator randomStringGenerator;

    @Transactional
    public CreateRemResponse createRem(String apiKey, int amount) {
        Key key = keyRepository.findByHashedKey(hmacUtil.hmacSha256(apiKey))
                .orElseThrow(() -> new BusinessException(ErrorCode.KEY_NOT_FOUND));

        Member member = key.getMember();

        String name = randomStringGenerator.ramdomString(6);
        String uuid = UUID.randomUUID().toString().replace("-", "");

        Remittance remittance = Remittance.builder()
                .name(name)
                .uuid(uuid)
                .amount(amount)
                .checked(false)
                .member(member)
                .build();

        remittanceRepository.save(remittance);

        CreateRemResponse createRemResponse = new CreateRemResponse(
                remittance.getUuid(),
                remittance.getName(),
                remittance.getAmount()
        );

        return createRemResponse;
    }

    public GetRemResponse getRem(String apiKey, String uuid) {
        Key key = keyRepository.findByHashedKey(hmacUtil.hmacSha256(apiKey))
                .orElseThrow(() -> new BusinessException(ErrorCode.KEY_NOT_FOUND));

        Member member = key.getMember();

        Remittance remittance = remittanceRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException(ErrorCode.REMITTANCE_NOT_FOUND));

        if (!remittance.getMember().equals(member)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        return GetRemResponse.from(remittance);
    }

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
    public void checkRemWithName(String apiKey, String name, int amount) {
        Key key = keyRepository.findByHashedKey(hmacUtil.hmacSha256(apiKey))
                .orElseThrow(() -> new BusinessException(ErrorCode.KEY_NOT_FOUND));

        Member member = key.getMember();

        LocalDateTime threshold = LocalDateTime.now().minusMinutes(10);

        Remittance remittance = remittanceRepository
                .findFirstByMemberAndNameAndCreatedAtAfterOrderByCreatedAtDesc(
                        member,
                        name,
                        threshold
                )
                .orElseThrow(() -> new BusinessException(ErrorCode.REMITTANCE_NOT_FOUND));

        if (remittance.getAmount() != amount) {
            return;
        }

        remittance.check();
        remittanceRepository.save(remittance);
    }

    public GetRemCheckedResponse getRemChecked(String apiKey, String uuid) {
        Key key = keyRepository.findByHashedKey(hmacUtil.hmacSha256(apiKey))
                .orElseThrow(() -> new BusinessException(ErrorCode.KEY_NOT_FOUND));

        Member member = key.getMember();

        Remittance remittance = remittanceRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException(ErrorCode.REMITTANCE_NOT_FOUND));

        if (!remittance.getMember().equals(member)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        return new GetRemCheckedResponse(remittance.isChecked());
    }
}
