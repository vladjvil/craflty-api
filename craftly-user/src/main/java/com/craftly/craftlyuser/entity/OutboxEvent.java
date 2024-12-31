package com.craftly.craftlyuser.entity;

import com.craftly.craftlycore.models.outbox.OutboxStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.UUID;

@Table("outbox_events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutboxEvent {

    @Id
    private UUID id;

    @Column("event_type")
    private String eventType;

    @Column("payload")
    private String payload;

    @Column("status")
    private OutboxStatus status;

    @Column("created_at")
    @CreatedDate
    private Timestamp createdAt;
}