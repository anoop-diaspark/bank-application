package com.diaspark.INB.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.diaspark.INB.entity.UserAccount;
import com.diaspark.INB.entity.UserTransaction;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long> {
    List<UserTransaction> findUserTransactionBySourceAccountOrTargetAccount(UserAccount sourceAccount, UserAccount targetAccount);
    List<UserTransaction> findUserTransactionBySourceAccount(UserAccount userAccount);
    List <UserTransaction>findUserTransactionByStatus(String status);
    UserTransaction findUserTransactionByTransId(long id);
    Page<UserTransaction> findUserTransactionBySourceAccountOrTargetAccount(UserAccount sourceAccount, UserAccount targetAccount, Pageable pageable);

}
