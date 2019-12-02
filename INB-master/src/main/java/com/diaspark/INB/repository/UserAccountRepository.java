package com.diaspark.INB.repository;


import com.diaspark.INB.entity.User;
import com.diaspark.INB.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    List<UserAccount> findUserAccountByUser(User user);
    UserAccount findByAccountNumber(Long accountNumber);
   
   
}
