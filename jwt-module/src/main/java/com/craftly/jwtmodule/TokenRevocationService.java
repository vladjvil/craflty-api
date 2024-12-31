package com.craftly.jwtmodule;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenRevocationService {

    private final JwtProvider jwtProvider;

    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    public void revoke(String token) {
        String jti = jwtProvider.extractJti(token);
        Duration ttl = jwtProvider.extractTimeUntilExpiration(token);
        reactiveRedisTemplate.opsForValue().set(jti, "", ttl);
    }

    public boolean isRevoked(String accessToken) {
        String jti = jwtProvider.extractJti(accessToken);
        Object fetchedRedisValue = reactiveRedisTemplate.opsForValue().get(jti);
        return Optional.ofNullable(fetchedRedisValue).isPresent();
    }
}
