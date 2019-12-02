  package com.diaspark.INB.entity;


import com.diaspark.INB.exception.BadRequestException;

public enum UserStatus {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private String status;

    UserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static UserStatus findCodeByStatus(String status) {
        for (UserStatus userStatus : values()) {
            if (status.equals(userStatus.getStatus())) {
                return userStatus;
            }
        }
        throw new BadRequestException("Incorrect User status");
    }
}