package com.diaspark.INB.entity;

import com.diaspark.INB.exception.InvalidTransactionException;

public enum Role {

    ADMIN("admin"),
    USER("user");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static Role findCodeByRole(String role) {
        for (Role roleType : values()) {
            if (role.equals(roleType.getRole())) {
                return roleType;
            }
        }
        throw new InvalidTransactionException("Incorrect role type");
    }
}

