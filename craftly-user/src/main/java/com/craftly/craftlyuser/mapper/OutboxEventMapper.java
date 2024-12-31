package com.craftly.craftlyuser.mapper;

import com.craftly.craftlycore.models.outbox.OutboxEventCreateDTO;
import com.craftly.craftlyuser.entity.OutboxEvent;
import org.springframework.stereotype.Component;

@Component
public class OutboxEventMapper {

    public OutboxEvent toEntity(OutboxEventCreateDTO outboxEventCreateDTO) {
        return OutboxEvent.builder()
                .eventType(outboxEventCreateDTO.eventType())
                .payload(outboxEventCreateDTO.payload())
                .status(outboxEventCreateDTO.status())
                .build();
    }
}
