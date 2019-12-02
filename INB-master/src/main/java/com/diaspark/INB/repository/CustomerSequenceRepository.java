package com.diaspark.INB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diaspark.INB.entity.CustomerSequence;
@Repository
public interface CustomerSequenceRepository extends JpaRepository<CustomerSequence,String> {
CustomerSequence findByName(String name);
}
