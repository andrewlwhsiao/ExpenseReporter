package com.andrewhsiao.springproject;

import java.text.DecimalFormat;

public class Expense {
    String name;
    String category;
    String location;
    String date;
    double amount;

    public Expense() {}

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getLocation() {
        return location;
    }
    
    public String getDate() {
        return date;
    }

    public String getAmount() {
        DecimalFormat format = new DecimalFormat("#.00");
        return format.format(amount);
    }
    
    public void setName(String nameIn) {
        name = nameIn;
    }

    public void setCategory(String categoryIn) {
        category = categoryIn;
    }
    
    public void setLocation(String locationIn) {
        location = locationIn;
    }
    
    public void setDate(String dateIn) {
        date = dateIn;
    }
    
    public void setAmount(double amountIn) {
        amount = amountIn;
    }
    
    public String toString() {
        return String.format("Name: %s, Cost: %s, Category: %s", name, String.valueOf(amount), category);
    }
}