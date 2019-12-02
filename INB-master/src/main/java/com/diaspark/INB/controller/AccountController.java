package com.diaspark.INB.controller;

import com.diaspark.INB.DTO.AccountResponseDTO;
import com.diaspark.INB.DTO.UserAccountDto;
import com.diaspark.INB.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PreAuthorize("hasRole('ROLE_user')")
    @PostMapping("/add")
    public AccountResponseDTO account(@RequestBody UserAccountDto userAccountDto) {
        return accountService.saveAccount(userAccountDto);
    }
}
