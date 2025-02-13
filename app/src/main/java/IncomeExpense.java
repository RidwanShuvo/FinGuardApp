package com.example.finguard;

public class IncomeExpense {
    private String id;
    private String type;
    private String date;
    private double amount; // Ensure amount is double

    // Default constructor (required for Firebase)
    public IncomeExpense() {
    }

    // Constructor with parameters
    public IncomeExpense(String id, String type, String date, double amount) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.amount = amount;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    // Setters (needed for Firebase)
    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
