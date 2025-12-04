package com.example.cs213_project5;

public class DonutItem {
    private String name;
    private String type;
    private double price;
    private int quantity;

    public DonutItem(String name, String type, double price){
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getPrice(){
        return price;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity(){
        return quantity;
    }

    @Override
    public  String toString(){
        return name+ " (" + type + ") - $" + String.format("%.2f", price);
    }
}
