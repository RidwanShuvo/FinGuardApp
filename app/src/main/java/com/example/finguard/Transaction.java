package com.example.finguard;

public class Transaction {
    private String id;
    private String date;
    private String type; // "Income" or "Expense"
    private double amount;

    // Default constructor required for calls to DataSnapshot.getValue(Transaction.class)
    public Transaction() {
    }

    // Constructor
    public Transaction(String id, String date, String type, double amount) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
