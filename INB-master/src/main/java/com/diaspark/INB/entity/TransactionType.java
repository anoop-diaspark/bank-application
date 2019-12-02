package com.diaspark.INB.entity;

import com.diaspark.INB.exception.InvalidTransactionException;

public enum TransactionType {

    CREDIT("credit"),
    DEBIT("debit");

    private String transType;

    TransactionType(String transType) {
        this.transType = transType;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public static TransactionType findCodeByTransType(String transType) {
        for (TransactionType transactionType : values()) {
            if (transType.equals(transactionType.getTransType())) {
                return transactionType;
            }
        }
        throw new InvalidTransactionException("Incorrect transaction type");
    }
}

