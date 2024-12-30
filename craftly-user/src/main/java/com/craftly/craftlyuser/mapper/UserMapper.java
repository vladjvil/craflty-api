package com.craftly.craftlyuser.mapper;

import com.craftly.craftlycore.user.UserCreateDTO;
import com.craftly.craftlycore.user.UserResponseDTO;
import com.craftly.craftlyuser.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getPhoneNumber(),
                user.getPassword()
        );
    }

    public User toEntity(UserCreateDTO userCreateDTO) {
        return User.builder()
                .jobStatus(userCreateDTO.jobStatus())
                .email(userCreateDTO.email())
                .username(userCreateDTO.username())
                .phoneNumber(userCreateDTO.phoneNumber())
                .password(userCreateDTO.password())
                .build();
    }
}