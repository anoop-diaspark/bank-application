package com.diaspark.INB.DTO;


import java.util.Date;

public class TransactionResponseDTO {
    private String status;
    private double amount;
    private AccountResponseDTO sourceAccount;
    private AccountResponseDTO targetAccount;
    private UserResponseDTO userResponseDTO;
    private long transId;
    private Date date;

    public long getTransId() {
        return transId;
    }

    public void setTransId(long transId) {
        this.transId = transId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public UserResponseDTO getUserResponseDTO() {
        return userResponseDTO;
    }

    public void setUserResponseDTO(UserResponseDTO userResponseDTO) {
        this.userResponseDTO = userResponseDTO;
    }

    public AccountResponseDTO getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(AccountResponseDTO sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public AccountResponseDTO getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(AccountResponseDTO targetAccount) {
        this.targetAccount = targetAccount;
    }
}
