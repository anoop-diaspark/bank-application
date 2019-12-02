package com.diaspark.INB.repository;

import com.diaspark.INB.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
  Repository name is always table Bean name + Repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserById(long id);
    List<User> findUserByStatus(String status);

}
                    