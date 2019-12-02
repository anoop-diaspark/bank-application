package com.diaspark.INB.entity;

import javax.persistence.*;
import java.util.Set;

//import javax.persistence.CascadeType;


@Entity
@Table(name = "account")
public class UserAccount {


    @Id
   // @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_number", nullable = false, unique = true)
    private long accountNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private User user;

    @OneToMany(mappedBy = "sourceAccount")
    private Set<UserTransaction> userTransaction;

    @Column(name = "account_type", nullable = true)
    private String accountType;

    @Column(name = "balance", nullable = false)
    private double accountBalance;


    public Set<UserTransaction> getUserTransaction() {
        return userTransaction;
    }

    public void setUserTransaction(Set<UserTransaction> userTransaction) {
        this.userTransaction = userTransaction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
