package com.sungjujjang.epg.domain.key.service;

import com.sungjujjang.epg.domain.auth.entity.Member;
import com.sungjujjang.epg.domain.auth.repository.MemberRepository;
import com.sungjujjang.epg.domain.key.dto.CreateKeyRequest;
import com.sungjujjang.epg.domain.key.dto.CreateKeyResponse;
import com.sungjujjang.epg.domain.key.dto.GetKeyListResponse;
import com.sungjujjang.epg.domain.key.dto.KeyDto;
import com.sungjujjang.epg.domain.key.entity.Key;
import com.sungjujjang.epg.domain.key.repository.KeyRepository;
import com.sungjujjang.epg.global.exception.BusinessException;
import com.sungjujjang.epg.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeyService {

    private final PasswordEncoder passwordEncoder;

    private final KeyRepository keyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CreateKeyResponse createKey(
            Long userId,
            CreateKeyRequest createKeyRequest
    ) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        String backKey = UUID.randomUUID().toString().replace("-", "");
        String apiKey = new StringJoiner("-")
                .add("epg")
                .add(backKey)
                .toString();

        LocalDate expDate = LocalDate.now().plusDays(
                createKeyRequest.expDay().getDay()
        );

        String hashedKey = passwordEncoder.encode(apiKey);

        Key key = Key.builder()
                .hashedKey(hashedKey)
                .name(createKeyRequest.name())
                .member(member)
                .expDate(expDate)
                .build();

        keyRepository.save(key);

        return new CreateKeyResponse(
                key.getId(),
                apiKey,
                expDate
        );
    }

    public GetKeyListResponse getKeyList(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        List<Key> keyList = keyRepository.findByMember(member);
        List<KeyDto> keys = keyList.stream()
                .map(KeyDto::from)
                .toList();

        return new GetKeyListResponse(keys);
    }
}
