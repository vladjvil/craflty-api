package com.craftly.craftlyuser.service.outbox;

import com.craftly.craftlycore.models.outbox.OutboxEventCreateDTO;
import com.craftly.craftlyuser.entity.OutboxEvent;
import com.craftly.craftlyuser.mapper.OutboxEventMapper;
import com.craftly.craftlyuser.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxWriteServiceDB {

    private final OutboxEventRepository outboxEventRepository;

    private final OutboxEventMapper outboxEventMapper;

    public Mono<OutboxEvent> createOutboxEvent(OutboxEventCreateDTO outboxEventCreateDTO) {
        return outboxEventRepository.save(outboxEventMapper.toEntity(outboxEventCreateDTO))
                .doOnSuccess(outboxEvent -> log.info("Outbox Event created. Outbox Event ID: {}", outboxEvent.getId()))
                .doOnError(e -> log.error("Failed to create order event: {}", e.getMessage()));
    }
}
