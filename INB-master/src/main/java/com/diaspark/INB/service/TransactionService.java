package com.diaspark.INB.service;

import com.diaspark.INB.DTO.TransactionDTO;
import com.diaspark.INB.DTO.TransactionResponseDTO;
import com.diaspark.INB.DTO.UtilityPaymentDto;

import java.util.List;

public interface TransactionService {
    TransactionResponseDTO addTransaction(TransactionDTO transactionDto);
    TransactionResponseDTO updateTransactionStatus(Long transactionId, String status);
    List<TransactionResponseDTO> retrieveUserTransactions(Long accountNumber, boolean miniStatement);
    List<TransactionResponseDTO> retrieveAdminTransactions(String status);
    TransactionResponseDTO payUtilityTransaction(UtilityPaymentDto utilityPaymentDto);

}
