package com.craftly.craftlyuser.messaging.consumer;

import com.craftly.craftlycore.messaging.KafkaConsumers;
import com.craftly.craftlycore.messaging.KafkaTopics;
import com.craftly.craftlycore.models.user.UserCreateDTO;
import com.craftly.craftlyuser.service.user.UserFacadeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserConsumer {

    private final UserFacadeService userFacadeService;

    private final Gson gson = new GsonBuilder().create();

    @KafkaListener(topics = KafkaTopics.USER_CREATED, groupId = KafkaConsumers.USERS)
    public void listenUserCreation(@Payload String message) {
        UserCreateDTO userCreateDTO = gson.fromJson(message, UserCreateDTO.class);
        userFacadeService.createUser(userCreateDTO)
                .subscribe();
    }
}
