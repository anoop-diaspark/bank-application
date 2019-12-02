package com.diaspark.INB.controller;

import com.diaspark.INB.DTO.TransactionDTO;
import com.diaspark.INB.DTO.TransactionResponseDTO;
import com.diaspark.INB.DTO.UtilityPaymentDto;
import com.diaspark.INB.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PreAuthorize("hasRole('ROLE_user')")
    @PostMapping("/add")
    public TransactionResponseDTO request(@RequestBody TransactionDTO userTransaction) {
        return transactionService.addTransaction(userTransaction);
    }

    /*
     * http://localhost:8080/transaction/fetch?accountNumber=2&miniStatement=true
     * http://localhost:8080/transaction/fetch?accountNumber=2
     */
    @PreAuthorize("hasAnyRole('ROLE_user','ROLE_admin')")
    @GetMapping("/fetch")
    public List<TransactionResponseDTO> retrieveUserTransactions(@RequestParam(required = false) Long accountNumber, @RequestParam(required = false) boolean miniStatement) {
        return transactionService.retrieveUserTransactions(accountNumber, miniStatement);
    }

    /*
     * http://localhost:8080/transaction/fetch?status=pending
     * http://localhost:8080/transaction/fetch
     */
    @PreAuthorize("hasRole('ROLE_admin')")
    @GetMapping("/fetch/admin")
    public List<TransactionResponseDTO> retrieveAdminTransactions(@RequestParam(required = false) String status) {
        return transactionService.retrieveAdminTransactions(status);
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PutMapping("/update/{transactionId}")
    public TransactionResponseDTO updateTransactionStatus(@PathVariable("transactionId") Long transactionId, @RequestParam String status) {
       return transactionService.updateTransactionStatus(transactionId, status);
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @PostMapping("/pay/utility")
    public TransactionResponseDTO payUtilityAccounts(@RequestBody UtilityPaymentDto utilityPaymentDto){
        return transactionService.payUtilityTransaction(utilityPaymentDto);
    }
}
