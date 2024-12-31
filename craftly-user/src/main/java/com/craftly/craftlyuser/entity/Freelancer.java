package com.craftly.craftlyuser.entity;

import com.craftly.craftlycore.models.user.FreelancerSpecialization;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table("freelancers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Freelancer {

    @Id
    private UUID id;

    @Column("specializations")
    private List<FreelancerSpecialization> specializations = new ArrayList<>();

    @Column("portfolio_ids")
    private List<UUID> portfolioIds = new ArrayList<>();
}
