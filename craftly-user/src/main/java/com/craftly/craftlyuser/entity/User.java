package com.craftly.craftlyuser.entity;

import com.craftly.craftlycore.user.UserJobStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.UUID;

@Table("users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    private UUID id;

    @Column("email")
    private String email;

    @Column("username")
    private String username;

    @Column("phone_number")
    private String phoneNumber;

    @Column("password")
    private String password;

    @Column("job_status")
    private UserJobStatus jobStatus;

    @Column("created_at")
    private Timestamp createdAt;

    @Column("updated_at")
    private Timestamp updatedAt;
}
