package com.craftly.craftlyuser.controller;

import com.craftly.craftlyuser.mapper.UserMapper;
import com.craftly.craftlyuser.service.user.UserFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserControllerDB {

    private final UserFacadeService userFacadeService;

    private final UserMapper userMapper;
}