package com.revature.models;

import java.time.LocalDate;

public class Reimbursement {

    private double amount;
    private LocalDate sumbmittedDate;
    private LocalDate resolvedDate;
    private String description;
    private User author;
    private User resolver;
    private Status status;
    private Type type;

    public Reimbursement() {
    }

    public Reimbursement(double amount, LocalDate sumbmittedDate, LocalDate resolvedDate, String description, User author, User resolver, Status status, Type type) {
        this.amount = amount;
        this.sumbmittedDate = sumbmittedDate;
        this.resolvedDate = resolvedDate;
        this.description = description;
        this.author = author;
        this.resolver = resolver;
        this.status = status;
        this.type = type;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "amount=" + amount +
                ", sumbmittedDate=" + sumbmittedDate +
                ", resolvedDate=" + resolvedDate +
                ", author=" + author +
                ", resolver=" + resolver +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
