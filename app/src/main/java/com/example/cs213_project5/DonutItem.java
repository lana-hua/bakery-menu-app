package com.example.cs213_project5;

import java.util.Locale;

/**
 * Represents a donut item with name, type, price, quantity, and image resource.
 * Used for display in the RecyclerView.
 * @author Sharon Chen
 */
public class DonutItem {
    private String flavor;
    private String type;
    private double price;
    private int quantity;
    private int imageResource;

    /**
     * Constructs a DonutItem with specified attributes.
     * @param flavor the name of the donut
     * @param type the type of donut (Yeast, Cake, etc.)
     * @param price the price of the donut
     * @param imageResource the image resource ID for the donut
     */
    public DonutItem(String flavor, String type, double price, int imageResource) {
        this.flavor = flavor;
        this.type = type;
        this.price = price;
        this.imageResource = imageResource;
        this.quantity = 0; // Default quantity is 0
    }

    /**
     * Returns the flavor of the donut.
     * @return the donut flavor
     */
    public String getFlavor() {
        return flavor;
    }

    /**
     * Returns the type of the donut.
     * @return the donut type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the price of the donut.
     * @return the donut price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the image resource ID for the donut.
     * @return the image resource ID
     */
    public int getImageResource() {
        return imageResource;
    }

    /**
     * Returns the quantity of this donut item.
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of this donut item.
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Calculates the total price for this donut item.
     * @return price multiplied by quantity
     */
    public double getTotalPrice() {
        return price * quantity;
    }

    @Override
    public  String toString(){
        return flavor + " (" + type + ") - $" + String.format(Locale.US, "%.2f", price);
    }
}
