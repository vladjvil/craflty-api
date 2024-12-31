package com.craftly.craftlyuser.service.user;

import com.craftly.craftlycore.messaging.KafkaTopics;
import com.craftly.craftlycore.models.outbox.OutboxEventCreateDTO;
import com.craftly.craftlycore.models.outbox.OutboxStatus;
import com.craftly.craftlycore.models.user.UserCreateDTO;
import com.craftly.craftlyuser.service.outbox.OutboxWriteServiceDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserEventService {

    private final OutboxWriteServiceDB outboxWriteServiceDB;

    private final Gson gson = new GsonBuilder().create();

    public Mono<Void> saveUserCreatedEvent(UserCreateDTO userCreateDTO) {
        OutboxEventCreateDTO outboxEventCreateDTO = new OutboxEventCreateDTO(
                KafkaTopics.USER_CREATED,
                gson.toJson(userCreateDTO),
                OutboxStatus.PENDING
        );
        return outboxWriteServiceDB.createOutboxEvent(outboxEventCreateDTO).then();
    }
}
