package com.example.cs213_project5;

public class DonutItem {
    private String flavor;
    private String type;
    private double price;
    private int quantity;
    private int image;

    public DonutItem(String flavor, String type, double price, int image){
        this.flavor = flavor;
        this.type = type;
        this.price = price;
        this.image = image;
        this.quantity = 0;
    }

    public String getFlavor() {
        return flavor;
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
        return flavor + " (" + type + ") - $" + String.format("%.2f", price);
    }

    public int getImageResource() {
        return image;
    }
}
