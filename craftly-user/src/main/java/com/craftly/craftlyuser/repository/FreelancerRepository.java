package com.craftly.craftlyuser.repository;

import com.craftly.craftlyuser.entity.Freelancer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FreelancerRepository extends ReactiveCrudRepository<Freelancer, UUID> {
}