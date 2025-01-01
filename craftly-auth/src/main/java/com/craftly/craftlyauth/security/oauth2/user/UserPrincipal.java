package com.craftly.craftlyauth.security.oauth2.user;

import com.craftly.craftlycore.models.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements OAuth2User {

    private UUID id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
    private Map<String, Object> attributes;

    public static UserPrincipal create(UserResponseDTO user) {
        return new UserPrincipal(
                user.id(),
                user.email(),
                user.passwordHash(),
                Collections.emptyList(),
                null
        );
    }

    public static UserPrincipal create(UserResponseDTO userCreateDTO, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = create(userCreateDTO);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
