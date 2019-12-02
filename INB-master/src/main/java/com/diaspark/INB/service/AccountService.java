package com.diaspark.INB.service;

import com.diaspark.INB.DTO.AccountResponseDTO;
import com.diaspark.INB.DTO.UserAccountDto;

import java.util.List;


public interface AccountService {
    AccountResponseDTO saveAccount(UserAccountDto userAccountDto);

}
