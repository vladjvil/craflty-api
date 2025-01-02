package com.craftly.craftlyauth.service;

import com.craftly.craftlyauth.security.oauth2.user.OAuth2UserInfo;
import com.craftly.craftlyauth.security.oauth2.user.OAuth2UserInfoFactory;
import com.craftly.craftlyauth.security.oauth2.user.UserPrincipal;
import com.craftly.craftlycore.exception.auth.OAuth2AuthenticationProcessingException;
import com.craftly.craftlycore.models.user.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Value("${private.secret}")
    private String privateSecret;

    private final UserClient userClient;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                userRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );

        isValidEmail(oAuth2UserInfo.getEmail());

        UserResponseDTO user;
        if (userClient.existsByEmail(oAuth2UserInfo.getEmail())) {
            String authProvider = userClient.getByEmail(oAuth2UserInfo.getEmail(), privateSecret).getAuthProvider();
            isCorrectProvider(authProvider, userRequest);

            user = updateExistingUser(oAuth2UserInfo);
        } else {
            user = registerNewUser(userRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }
    
    private void isValidEmail(String email) {
        if (ObjectUtils.isEmpty(email)) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
    }

    private void isCorrectProvider(String authProvider, OAuth2UserRequest userRequest) {
        if (!userRequest.getClientRegistration().getRegistrationId().equals(authProvider)) {
            throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                    authProvider + " account. Please use your " + authProvider +
                    " account to login.");
        }
    }

    private UserResponseDTO updateExistingUser(OAuth2UserInfo oAuth2UserInfo) {
        UpdateOAuth2UserDto dto = new UpdateOAuth2UserDto(
                oAuth2UserInfo.getEmail(),
                oAuth2UserInfo.getUsername(),
                oAuth2UserInfo.getAvatarUrl()
        );

        return userClient.updateOAuth2UserData(dto, privateSecret);
    }

    private UserResponseDTO registerNewUser(OAuth2UserRequest userRequest, OAuth2UserInfo oAuth2UserInfo) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        RegisterOAuth2UserDto dto = new RegisterOAuth2UserDto(
                oAuth2UserInfo.getEmail(),
                oAuth2UserInfo.getUsername(),
                oAuth2UserInfo.getAvatarUrl(),
                registrationId
        );

        return userClient.registerOAuth2User(dto, privateSecret);
    }
}