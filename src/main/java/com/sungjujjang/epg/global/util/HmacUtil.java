package com.sungjujjang.epg.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

@Component
public class HmacUtil {

    private final String secret;

    public HmacUtil(@Value("${apikey.secret}") String secret) {
        this.secret = secret;
    }

    public String hmacSha256(String apiKey) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");

            mac.init(new SecretKeySpec(
                    secret.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"
            ));

            return HexFormat.of().formatHex(
                    mac.doFinal(apiKey.getBytes(StandardCharsets.UTF_8))
            );

        } catch (Exception e) {
            throw new IllegalStateException("HMAC hashing failed", e);
        }
    }
}