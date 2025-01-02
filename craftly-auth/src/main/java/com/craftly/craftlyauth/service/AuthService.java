package com.craftly.craftlyauth.service;

import com.craftly.craftlyauth.messaging.producer.MailActionsProducer;
import com.craftly.craftlyauth.security.AuthenticatedDataProvider;
import com.craftly.craftlyauth.security.RefreshTokenGenerator;
import com.craftly.craftlycore.models.auth.mail.ForgotPasswordMessage;
import com.craftly.craftlycore.models.auth.mail.UserPasswordResetMessage;
import com.craftly.craftlycore.models.auth.mail.VerificationMessage;
import com.craftly.craftlycore.models.user.UserResponseDTO;
import com.craftly.jwtmodule.JwtProvider;
import com.craftly.jwtmodule.TokenRevocationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${private.secret}")
    private String privateSecret;

    private final JwtProvider jwtProvider;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private final TokenRevocationService tokenRevocationService;
    private final AuthenticatedDataProvider authenticatedDataProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserClient userClient;
    private final UserActionsProducer userActionsProducer;
    private final MailActionsProducer mailActionsProducer;
    private final HttpServletRequest httpServletRequest;

    private final Random random = new Random();

    public AuthResponse register(RegisterUserDto dto) {
        if (userClient.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        sendCreatingUserMessage(dto);
        AuthResponse response = getAuthResponse(dto.getEmail());
        sendVerificationMailMessage(dto.getUsername(), dto.getEmail(), response.getAccessToken());

        return response;
    }

    private void sendCreatingUserMessage(RegisterUserDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        userActionsProducer.executeCreatingUser(dto);
    }

    private void sendVerificationMailMessage(String username, String email, String accessToken) {
        VerificationMessage verificationMessage = new VerificationMessage(username, email, accessToken);
        mailActionsProducer.executeSendVerificationMessage(verificationMessage);
    }

    private AuthResponse getAuthResponse(String email) {
        String accessToken = jwtProvider.generateAccessToken(email);
        String refreshToken = refreshTokenGenerator.generate(email);

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse login(AuthRequest request) {
        PrivateUserResponse user = userClient.getByEmail(request.getEmail(), privateSecret);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials provided.");
        }

        if (user.isUsing2FA()) {
            cacheManager.save("2fa:" + user.getEmail(), Duration.ofMinutes(5));
            return null;
        }

        return getAuthResponse(request.getEmail());
    }

    public String logout() {
        tokenRevocationService.revoke(getToken());
        return "You have successfully logout from your account.";
    }

    private String getToken() {
        return Optional.ofNullable(httpServletRequest.getHeader("Authorization"))
                .orElseThrow(TokenNotFoundException::new).substring(7);
    }

    public String confirmByEmail(String accessToken) {
        String subject = jwtProvider.extractEmail(accessToken);
        userActionsProducer.executeConfirmByEmail(subject);

        return "Account is verified.";
    }

    public AuthResponse getTokensByRefresh(String refreshToken, boolean isRefresh) {
        String subject = cacheManager.fetch(refreshToken, String.class)
                .orElseThrow(TokenVerificationException::new);

        String accessToken = jwtProvider.generateAccessToken(subject);
        String newRefreshToken = isRefresh ? refreshTokenGenerator.generate(subject) : null;

        return new AuthResponse(accessToken, newRefreshToken);
    }

    public String forgotPassword() {
        PrivateUserResponse user = userClient.getByEmail(authenticatedDataProvider.getEmail(), privateSecret);
        sendForgotPasswordMailMessage(user.getEmail());

        return "A link to reset your password has been sent to your email.";
    }

    private void sendForgotPasswordMailMessage(String email) {
        String accessToken = jwtProvider.generateAccessToken(email);
        ForgotPasswordMessage forgotPasswordMessage = new ForgotPasswordMessage(email, accessToken);
        mailActionsProducer.executeSendForgotPasswordMessage(forgotPasswordMessage);
    }

    public String resetPassword(ResetPasswordDto dto, String token) {
        UserResponseDTO user = userClient.getByEmail(jwtProvider.extractEmail(token), privateSecret);
        isValidUser(user);

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.passwordHash())) {
            throw new InvalidCredentialsException("Invalid credentials provided.");
        }

        sendUserPasswordResetMessage(user.getEmail(), dto.getNewPassword());
        tokenRevocationService.revoke(token);

        return "Password has been reset successfully.";
    }

    private void sendUserPasswordResetMessage(String email, String newPassword) {
        String newEncodedPassword = passwordEncoder.encode(newPassword);
        UserPasswordResetMessage userPasswordResetMessage = new UserPasswordResetMessage(email, newEncodedPassword);
        userActionsProducer.executeResetPassword(userPasswordResetMessage);
    }

    private void isValidUser(PrivateUserResponse user) {
        isUserBanned(user, "You are banned.");
        isUserVerified(user, "You are not verified. Please confirm your email.");
    }

    public String changeEmail(String email) {
        if (userClient.existsByEmail(email) || cacheManager.isPresent(email)) {
            throw new UserAlreadyExistsException();
        }

        int verificationCode = generateVerificationCode();
        sendEmailVerificationMailMessage(email, verificationCode);

        cacheManager.save(email, verificationCode, Duration.ofHours(1));

        return "The verification code will be sent to the email you specified.";
    }

    private int generateVerificationCode() {
        return random.nextInt(9999 - 1000 + 1) + 1000;
    }

    private void sendEmailVerificationMailMessage(String email, int verificationCode) {
        NewEmailVerificationMessage verificationMessage = new NewEmailVerificationMessage(email, verificationCode);
        mailActionsProducer.executeSendNewEmailVerificationMessage(verificationMessage);
    }

    public AuthResponse confirmEmailChange(ConfirmEmailChangeRequest request) {
        int correctVerificationCode = cacheManager.fetch(request.getNewEmail(), Integer.class)
                .orElseThrow(InvalidEmailException::new);

        if (request.getVerificationCode() != correctVerificationCode) {
            throw new InvalidVerificationCodeException();
        }

        sendChangeEmailMessage(authenticatedDataProvider.getEmail(), request.getNewEmail());

        tokenRevocationService.revoke(getToken());
        cacheManager.delete(request.getNewEmail());

        return getAuthResponse(request.getNewEmail());
    }

    private void sendChangeEmailMessage(String currentEmail, String newEmail) {
        ChangeEmailMessage changeEmailMessage = new ChangeEmailMessage(currentEmail, newEmail);
        userActionsProducer.executeChangeEmail(changeEmailMessage);
    }
}