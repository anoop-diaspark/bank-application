package com.diaspark.INB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diaspark.INB.entity.AccountSequence;
import com.diaspark.INB.entity.CustomerSequence;
@Repository
public interface AccountSequenceRepository extends JpaRepository<AccountSequence,String>{
	AccountSequence findByName(String name);

}
