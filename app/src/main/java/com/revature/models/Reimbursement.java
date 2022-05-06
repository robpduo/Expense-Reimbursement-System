package com.revature.models;

import java.time.LocalDate;

public class Reimbursement {
    private int reimbursementId;
    private double amount;
    private LocalDate sumbmittedDate;
    private LocalDate resolvedDate;
    private User author;
    private User resolver;
    private User status;
    private User type;

    public Reimbursement() {
    }

    public Reimbursement(int reimbursementId, double amount, LocalDate sumbmittedDate, LocalDate resolvedDate, User author, User resolver, User status, User type) {
        this.reimbursementId = reimbursementId;
        this.amount = amount;
        this.sumbmittedDate = sumbmittedDate;
        this.resolvedDate = resolvedDate;
        this.author = author;
        this.resolver = resolver;
        this.status = status;
        this.type = type;
    }

    public int getReimbursementId() {
        return reimbursementId;
    }

    public void setReimbursementId(int reimbursementId) {
        this.reimbursementId = reimbursementId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getSumbmittedDate() {
        return sumbmittedDate;
    }

    public void setSumbmittedDate(LocalDate sumbmittedDate) {
        this.sumbmittedDate = sumbmittedDate;
    }

    public LocalDate getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(LocalDate resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getResolver() {
        return resolver;
    }

    public void setResolver(User resolver) {
        this.resolver = resolver;
    }

    public User getStatus() {
        return status;
    }

    public void setStatus(User status) {
        this.status = status;
    }

    public User getType() {
        return type;
    }

    public void setType(User type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbursementId=" + reimbursementId +
                ", amount=" + amount +
                ", sumbmittedDate=" + sumbmittedDate +
                ", resolvedDate=" + resolvedDate +
                ", author=" + author +
                ", resolver=" + resolver +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
