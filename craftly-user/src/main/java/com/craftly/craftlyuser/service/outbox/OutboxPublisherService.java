package com.craftly.craftlyuser.service.outbox;

import com.craftly.craftlycore.models.outbox.OutboxStatus;
import com.craftly.craftlyuser.entity.OutboxEvent;
import com.craftly.craftlyuser.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxPublisherService {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedRate = 5000)
    public void publishPendingEvents() {
        outboxEventRepository.findByStatus("PENDING")
                .flatMap(event ->
                        Mono.fromFuture(kafkaTemplate.send(event.getEventType().toString(), event.getPayload()))
                                .then(Mono.defer(() ->
                                        outboxEventRepository.save(markAsProcessed(event))
                                ))
                )
                .subscribe();
    }

    private OutboxEvent markAsProcessed(OutboxEvent event) {
        event.setStatus(OutboxStatus.PROCESSED);
        return event;
    }
}
