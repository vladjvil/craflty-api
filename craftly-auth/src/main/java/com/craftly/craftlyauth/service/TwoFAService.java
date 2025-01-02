package com.craftly.craftlyauth.service;

import com.craftly.craftlyauth.security.AuthenticatedDataProvider;
import com.craftly.craftlyauth.security.RefreshTokenGenerator;
import com.craftly.craftlycore.models.user.UserResponseDTO;
import com.craftly.jwtmodule.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class TwoFAService {

    @Value("${2fa.qr_prefix}")
    private String qrPrefix;

    @Value("${private.secret}")
    private String privateSecret;

    private static final String ISSUER = "SocialNetwork";

    private final AuthenticatedDataProvider authenticatedDataProvider;
    private final UserActionsProducer userActionsProducer;
    private final UserClient userClient;
    private final JwtProvider jwtProvider;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    public Setup2FAResponse setup() {
        UserResponseDTO user = userClient.getByEmail(authenticatedDataProvider.getEmail(), privateSecret);
        isValidUser(user);

        String secret = Base32.random();
        sendSaveSecretMessage(user.getEmail(), secret);

        return new Setup2FAResponse(generateQrCodeUrl(user.getEmail(), secret));
    }

    private void isValidUser(UserResponseDTO user) {
        isUserBanned(user, "You are banned.");
        isUserVerified(user, "You are not verified. Please confirm your email.");
    }

    private void sendSaveSecretMessage(String email, String secret) {
        SaveSecretMessage saveSecretMessage = new SaveSecretMessage(email, secret);
        userActionsProducer.executeSaveSecret(saveSecretMessage);
    }

    private String generateQrCodeUrl(String email, String secret) {
        return qrPrefix + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", ISSUER, email, secret, ISSUER), StandardCharsets.UTF_8) + "&size=200x200";
    }

    public String verify2FA(Verify2FADto dto) {
        PrivateUserResponse user = userClient.getByEmail(authenticatedDataProvider.getEmail(), privateSecret);
        verifyOtp(user, dto.getOtp());

        userActionsProducer.executeUse2FA(user.getEmail());

        return "Two-factor authentication is now enabled.";
    }

    private void verifyOtp(UserResponseDTO user, String otp) {
        Totp totp = new Totp(user.getSecret());

        if (!totp.verify(otp)) {
            throw new InvalidCredentialsException("Invalid verification code (otp)");
        }
    }

    public AuthResponse login(Login2FADto login2FADto) {
        UserResponseDTO user = userClient.getByEmail(login2FADto.getEmail(), privateSecret);

        if (!cacheManager.isPresent("2fa:" + user.getEmail())) {
            throw new TwoFactorIsNotRequiredException();
        }

        verifyOtp(user, login2FADto.getOtp());

        String accessToken = jwtProvider.generateAccessToken(user.getEmail());
        String refreshToken = refreshTokenGenerator.generate(user.getEmail());

        reactiveRedisTemplate.delete("2fa:" + user.getEmail());

        return new AuthResponse(accessToken, refreshToken);
    }
}