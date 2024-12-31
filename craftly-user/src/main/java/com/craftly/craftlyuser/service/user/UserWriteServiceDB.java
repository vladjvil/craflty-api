package com.craftly.craftlyuser.service.user;

import com.craftly.craftlycore.models.user.UserCreateDTO;
import com.craftly.craftlyuser.entity.User;
import com.craftly.craftlyuser.mapper.UserMapper;
import com.craftly.craftlyuser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserWriteServiceDB {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Mono<User> createBaseUser(UserCreateDTO userCreateDTO) {
        User user = userMapper.toEntity(userCreateDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user)
                .doOnSuccess(u -> log.info("Base user created. User ID: {}", u.getId()))
                .doOnError(e -> log.error("Failed to create base user: {}", e.getMessage()));
    }
}