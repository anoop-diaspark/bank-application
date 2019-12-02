package com.diaspark.INB.entity;

import com.diaspark.INB.exception.InvalidTransactionException;

public enum AccountType {

    SAVING("0", "saving"),
    CURRENT("1", "current");

    private String code;
    private String descritpion;

    AccountType(String code, String descritpion) {
        this.code = code;
        this.descritpion = descritpion;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }

    public static AccountType findAccountByDescritpion(String descritpion) {
        for (AccountType accountType : values()) {
            if (descritpion.equals(accountType.getDescritpion())) {
                return accountType;
            }
        }
        throw new InvalidTransactionException("Incorrect transaction type");
    }
}

