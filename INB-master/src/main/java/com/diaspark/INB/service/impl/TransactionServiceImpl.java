package com.diaspark.INB.service.impl;

import com.diaspark.INB.DTO.TransactionDTO;
import com.diaspark.INB.DTO.TransactionResponseDTO;
import com.diaspark.INB.DTO.UtilityPaymentDto;
import com.diaspark.INB.entity.UserAccount;
import com.diaspark.INB.entity.UserStatus;
import com.diaspark.INB.entity.UserTransaction;
import com.diaspark.INB.entity.UtilityAccounts;
import com.diaspark.INB.exception.BadRequestException;
import com.diaspark.INB.exception.NotFoundException;
import com.diaspark.INB.mapper.EntityToDTOMapper;
import com.diaspark.INB.repository.UserAccountRepository;
import com.diaspark.INB.repository.UserRepository;
import com.diaspark.INB.repository.UserTransactionRepository;
import com.diaspark.INB.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityToDTOMapper entityToDTOMapper;

    public TransactionResponseDTO addTransaction(TransactionDTO transactionDto) {
        return proceedTransaction(transactionDto);
    }

    private TransactionResponseDTO proceedTransaction(TransactionDTO transactionDto) {
        long sourceAccountNo = transactionDto.getSourceAccountNo();
        long targetAccountNo = transactionDto.getTargetAccountNo();
        UserAccount sourceAccount = userAccountRepository.findByAccountNumber(sourceAccountNo);
        UserAccount targetAccount = userAccountRepository.findByAccountNumber(targetAccountNo);
        if (sourceAccount == null || targetAccount == null) {
            throw new NotFoundException("source or target account is not present");
        }


        UserTransaction newUserTransaction = new UserTransaction();
        newUserTransaction.setAmount(transactionDto.getAmount());
        // Retreive User Account by account Number
        newUserTransaction.setSourceAccount(sourceAccount);
        newUserTransaction.setTargetAccount(targetAccount);

        //money transfer to different accounts
        if (sourceAccountNo != targetAccountNo && sourceAccount.getAccountBalance() >= newUserTransaction.getAmount()) 
       {
            newUserTransaction.setStatus(UserStatus.APPROVED.getStatus());
            newUserTransaction.setDate(new Date());
            sourceAccount.setAccountBalance(sourceAccount.getAccountBalance() - newUserTransaction.getAmount());
            targetAccount.setAccountBalance(targetAccount.getAccountBalance() + newUserTransaction.getAmount());
        }//Money transfer to same account need approval
        else if (sourceAccount == targetAccount) {
            newUserTransaction.setStatus(UserStatus.PENDING.getStatus());
        } //This condition will reach when account balance is low
        else {
            newUserTransaction.setStatus(UserStatus.REJECTED.getStatus());
        }

        userAccountRepository.save(sourceAccount);
        userAccountRepository.save(targetAccount);
        return entityToDTOMapper.buildTransactionResponseDTO(userTransactionRepository.save(newUserTransaction));
    }

    @Override
    public TransactionResponseDTO updateTransactionStatus(Long transactionId, String status) {

        UserTransaction userTransaction = userTransactionRepository.findUserTransactionByTransId(transactionId);
        UserStatus.findCodeByStatus(status);
        UserAccount sourceAccount = userTransaction.getSourceAccount();
        UserAccount targetAccount = userTransaction.getTargetAccount();

        //Check if source account is equal to target account and transaction state is changing from pending to approved
        if ((sourceAccount.getAccountNumber() == targetAccount.getAccountNumber()) && (UserStatus.PENDING.getStatus().equals(userTransaction.getStatus())&& UserStatus.APPROVED.getStatus().equals(status)))
        	{
            userTransaction.setStatus(status);
            userTransaction.setDate(new Date());
            targetAccount.setAccountBalance(targetAccount.getAccountBalance() + userTransaction.getAmount());
            userAccountRepository.save(targetAccount);
            UserTransaction savedUserTransaction = userTransactionRepository.save(userTransaction);
            return entityToDTOMapper.buildTransactionResponseDTO(savedUserTransaction);
        }
        else if (UserStatus.REJECTED.getStatus().equals(status))
        {
        	 userTransaction.setStatus(status);
             userTransaction.setDate(new Date());
             targetAccount.setAccountBalance(targetAccount.getAccountBalance() + 0);
             userAccountRepository.save(targetAccount);
             UserTransaction savedUserTransaction = userTransactionRepository.save(userTransaction);
             return entityToDTOMapper.buildTransactionResponseDTO(savedUserTransaction);
        }
        throw new BadRequestException("Only Pending to Approved update is supported");
    }

    @Override
    public List<TransactionResponseDTO> retrieveUserTransactions(Long accountNumber, boolean miniStatement) {
        List<TransactionResponseDTO> requestMoneyUsers = new ArrayList<>();
        List<UserTransaction> requestList;

        UserAccount userAccount = userAccountRepository.findByAccountNumber(accountNumber);
        if (!miniStatement) {
            requestList = userTransactionRepository.findUserTransactionBySourceAccountOrTargetAccount(userAccount, userAccount);
        } else {
            PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.DESC, "date");
            Page<UserTransaction> userTransactionPage = userTransactionRepository.findUserTransactionBySourceAccountOrTargetAccount(userAccount, userAccount, pageRequest);
            requestList = userTransactionPage.getContent();
        }
        for (UserTransaction userTransaction : requestList) {
            TransactionResponseDTO transactionResponseDTO = entityToDTOMapper.buildTransactionResponseDTO(userTransaction);
            transactionResponseDTO.setUserResponseDTO(entityToDTOMapper.buildUserResponseDTO(userTransaction.getTargetAccount().getUser()));
            requestMoneyUsers.add(transactionResponseDTO);
        }
        return requestMoneyUsers;
    }

    @Override
    public List<TransactionResponseDTO> retrieveAdminTransactions(String status) {
        List<TransactionResponseDTO> requestMoneyUsers = new ArrayList<>();
        List<UserTransaction> requestList;

        //find userTransaction by status
        if (status != null) {
            UserStatus.findCodeByStatus(status);
            requestList = userTransactionRepository.findUserTransactionByStatus(status);
        } //find all userTransaction
        else {
            requestList = userTransactionRepository.findAll();
        }

        for (UserTransaction userTransaction : requestList) {
            TransactionResponseDTO transactionResponseDTO = entityToDTOMapper.buildTransactionResponseDTO(userTransaction);
            transactionResponseDTO.setUserResponseDTO(entityToDTOMapper.buildUserResponseDTO(userTransaction.getTargetAccount().getUser()));
            requestMoneyUsers.add(transactionResponseDTO);
        }
        return requestMoneyUsers;
    }

    public TransactionResponseDTO payUtilityTransaction(UtilityPaymentDto utilityPaymentDto) {

        int customerId = UtilityAccounts.findCustomerByType(utilityPaymentDto.getUtilityType()).getCustomerId();
        List<UserAccount> userAccounts = userAccountRepository.findUserAccountByUser(userRepository.findUserById(customerId));
        UserAccount userAccount = userAccounts.get(0);
        TransactionDTO transactionDto = new TransactionDTO();
        transactionDto.setAmount(utilityPaymentDto.getAmount());
        transactionDto.setSourceAccountNo(utilityPaymentDto.getSourceAccountNo());
        transactionDto.setTargetAccountNo(userAccount.getAccountNumber());

        return proceedTransaction(transactionDto);
    }
}
