package com.diaspark.INB.DTO;

public class TransactionDTO {

    private double amount;
    private long sourceAccountNo;
    private long targetAccountNo;


    public long getSourceAccountNo() {
        return sourceAccountNo;
    }

    public void setSourceAccountNo(long sourceAccountNo) {
        this.sourceAccountNo = sourceAccountNo;
    }

    public long getTargetAccountNo() {
        return targetAccountNo;
    }

    public void setTargetAccountNo(long targetAccountNo) {
        this.targetAccountNo = targetAccountNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


}
