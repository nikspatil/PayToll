package com.example.paytoll;

public class FetchPaymentsModelClass {
    String id;
    String status;
    String amount;
    String date;
    public FetchPaymentsModelClass(String id, String status, String amount, String date) {
        this.id = id;
        this.status = status;
        this.amount = amount;
        this.date = date;
    }

    public FetchPaymentsModelClass(){
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
