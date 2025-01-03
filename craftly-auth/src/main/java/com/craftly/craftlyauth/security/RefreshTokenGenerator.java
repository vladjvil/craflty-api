package com.craftly.craftlyauth.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RefreshTokenGenerator {

    @Value("${jwt.refresh-token.validity}")
    private int refreshTokenValidity;

    private final ReactiveRedisTemplate reactiveRedisTemplate;

    private static final String ALGORITHM = "SHA256";

    @SneakyThrows
    public String generate(String email) {
        String randomIdentifier = String.valueOf(UUID.randomUUID());
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
        byte[] hash = messageDigest.digest(randomIdentifier.getBytes(StandardCharsets.UTF_8));

        String refreshToken = convertBytesToString(hash);
        saveRefreshTokenInCache(refreshToken, email);

        return refreshToken;
    }

    private String convertBytesToString(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte currentByte: bytes) {
            String hexValue = String.format("%02x", currentByte);
            hexStringBuilder.append(hexValue);
        }

        return hexStringBuilder.toString();
    }

    private void saveRefreshTokenInCache(String refreshToken, String email) {
        reactiveRedisTemplate.opsForValue().set(refreshToken, email, Duration.ofMinutes(refreshTokenValidity));
    }
}