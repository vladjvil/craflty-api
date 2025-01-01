package com.craftly.craftlyauth.security.oauth2;

import com.craftly.craftlyauth.security.oauth2.user.UserPrincipal;
import com.craftly.craftlyauth.util.CookieUtil;
import com.craftly.craftlycore.models.user.UserResponseDTO;
import com.craftly.jwtmodule.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${private.secret}")
    private String privateSecret;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final UserClient userClient;
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) return;

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserResponseDTO user = userClient.getByEmail(userPrincipal.getEmail(), privateSecret);
        String accessToken = jwtProvider.generateAccessToken(user.email());

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", accessToken)
                .build()
                .toString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}