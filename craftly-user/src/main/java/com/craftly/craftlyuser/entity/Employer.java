package com.craftly.craftlyuser.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("employers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employer {

    @Id
    private UUID id;

    @Column("company_name")
    private String companyName;

    @Column("company_website")
    private String companyWebsite;
}
