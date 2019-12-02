package com.diaspark.INB.mapper;

import com.diaspark.INB.DTO.AccountResponseDTO;
import com.diaspark.INB.DTO.TransactionResponseDTO;
import com.diaspark.INB.DTO.UserResponseDTO;
import com.diaspark.INB.entity.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class EntityToDTOMapper {

    public UserPrincipal buildUserPrincipalFromUser(User user, int jwtExpirationInMs) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(user.getCustomerId());
        userPrincipal.setEmailId(user.getEmail());
        userPrincipal.setUsername(user.getUsername());
        userPrincipal.setNow(now);
        userPrincipal.setExpireAt(expiryDate);

        //set authorities
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        updatedAuthorities.add(authority);
        userPrincipal.setAuthorities(updatedAuthorities);

        return userPrincipal;
    }

    public UserResponseDTO buildUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setCity(user.getCity());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhone(user.getPhone());
        userResponseDTO.setAddressLine1(user.getAddressLine1());
        userResponseDTO.setAddressLine2(user.getAddressLine2());
        userResponseDTO.setAddressLine3(user.getAddressLine3());
        userResponseDTO.setZip(user.getZip());
        userResponseDTO.setMobile(user.getCell());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setCustomerId(user.getCustomerId());
        userResponseDTO.setStatus(user.getStatus());
        userResponseDTO.setState(user.getState());
        userResponseDTO.setAddressLine2(user.getAddressLine2());
        userResponseDTO.setAddressLine3(user.getAddressLine3());
        return userResponseDTO;
    }

    public TransactionResponseDTO buildTransactionResponseDTO(UserTransaction userTransaction) {
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
        transactionResponseDTO.setAmount(userTransaction.getAmount());
        transactionResponseDTO.setSourceAccount(buildAccountResponseDto(userTransaction.getSourceAccount()));
        transactionResponseDTO.setTransId(userTransaction.getTransId());
        transactionResponseDTO.setStatus(userTransaction.getStatus());
        transactionResponseDTO.setTargetAccount(buildAccountResponseDto(userTransaction.getTargetAccount()));
        transactionResponseDTO.setDate(userTransaction.getDate());
        transactionResponseDTO.setTransId(userTransaction.getTransId());
        return transactionResponseDTO;
    }

    public AccountResponseDTO buildAccountResponseDto(UserAccount userAccount) {
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setBalance(userAccount.getAccountBalance());
        String accountType = AccountType.findAccountByDescritpion(String.valueOf(userAccount.getAccountType())).getDescritpion();
        accountResponseDTO.setAccountType(accountType);
        accountResponseDTO.setAccountNumber(userAccount.getAccountNumber());
        return accountResponseDTO;
    }
}
