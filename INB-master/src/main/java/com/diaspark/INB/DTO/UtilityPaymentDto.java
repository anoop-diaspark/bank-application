package com.diaspark.INB.DTO;

public class UtilityPaymentDto {

    private double amount;
    private long sourceAccountNo;
    private String utilityType;

    public String getUtilityType() {
        return utilityType;
    }

    public void setUtilityType(String utilityType) {
        this.utilityType = utilityType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getSourceAccountNo() {
        return sourceAccountNo;
    }

    public void setSourceAccountNo(long sourceAccountNo) {
        this.sourceAccountNo = sourceAccountNo;
    }
}
