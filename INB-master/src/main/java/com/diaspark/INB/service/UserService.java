package com.diaspark.INB.service;

import com.diaspark.INB.DTO.*;

import java.util.List;


public interface UserService {
    void registerUser(RegisterUserDTO registerUserDTO);

    UserResponseDTO authenticateUser(LoginUserDTO loginUserDTO) throws Exception;

    EmailResponseDTO sendQuery(SendMailDTO sendMailDTO);

    List<UserResponseDTO> retreiveUsers(String status);

    UserResponseDTO updateUserStatus(long customerId, String status);

}
