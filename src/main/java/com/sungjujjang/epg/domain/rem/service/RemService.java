package com.sungjujjang.epg.domain.rem.service;

import com.sungjujjang.epg.domain.auth.entity.Member;
import com.sungjujjang.epg.domain.auth.repository.MemberRepository;
import com.sungjujjang.epg.domain.rem.dto.CreateRemResponse;
import com.sungjujjang.epg.domain.rem.dto.GetRemResponse;
import com.sungjujjang.epg.domain.rem.entity.Remittance;
import com.sungjujjang.epg.domain.rem.repository.RemittanceRepository;
import com.sungjujjang.epg.global.exception.BusinessException;
import com.sungjujjang.epg.global.exception.ErrorCode;
import com.sungjujjang.epg.global.util.RandomStringGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RemService {

    private final RemittanceRepository remittanceRepository;
    private final MemberRepository memberRepository;
    private final RandomStringGenerator randomStringGenerator;

    @Transactional
    public CreateRemResponse createRem(Long userId, int amount) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

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

    public GetRemResponse getRem(Long userId, String uuid) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        Remittance remittance = remittanceRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException(ErrorCode.REMITTANCE_NOT_FOUND));

        if (remittance.getMember() != member) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        return GetRemResponse.from(remittance);
    }
}
