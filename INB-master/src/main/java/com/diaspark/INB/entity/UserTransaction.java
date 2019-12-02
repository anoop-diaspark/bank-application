package com.diaspark.INB.entity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "user_transaction")
public class UserTransaction {

    @Id
    @Column(name = "trans_id", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transId;

    @ManyToOne
    @JoinColumn(name = "source_account", referencedColumnName = "account_number")
    private UserAccount sourceAccount;

    @ManyToOne
    @JoinColumn(name = "target_account", referencedColumnName = "account_number")
    private UserAccount targetAccount;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "status", nullable = false)
    private String status;


    public UserAccount getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(UserAccount sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public UserAccount getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(UserAccount targetAccount) {
        this.targetAccount = targetAccount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
