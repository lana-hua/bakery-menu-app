package com.example.cs213_project5;

/**
 * Represents a donut item with flavor, type, price, quantity, and image resource.
 * This class is used to display donut items in the RecyclerView in DonutActivity.
 * @author Sharon Chen
 */
public class DonutItem {
    private String flavor;
    private String type;
    private double price;
    private int quantity;
    private int image;

    /**
     * Constructs a DonutItem with the specified attributes.
     * @param flavor the flavor of the donut
     * @param type the type of donut
     * @param price the price of a single donut of this type
     * @param image the resource ID of the image to display for this donut
     */
    public DonutItem(String flavor, String type, double price, int image){
        this.flavor = flavor;
        this.type = type;
        this.price = price;
        this.image = image;
        this.quantity = 0;
    }

    /**
     * Getter method for the flavor of the donut
     * @return the donut flavor as a String
     */
    public String getFlavor() {
        return flavor;
    }

    /**
     * Getter method for the type of the donut.
     * @return the donut type as a String
     */
    public String getType() {
        return type;
    }

    /**
     * Getter method for the price of the donut.
     * @return the donut price as a double
     */
    public double getPrice(){
        return price;
    }

    /**
     * Sets the quantity of the donut
     * @param quantity the number of donuts selected
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    /**
     * Getter method for the quantity of donuts.
     * @return the donut quantity as a int
     */
    public int getQuantity(){
        return quantity;
    }

    /**
     * Getter method for the image resource ID associated with the donuts.
     * @return the donut image resource ID as a int
     */
    public int getImageResource() {
        return image;
    }

    /**
     * Returns a string representation of the donut item including flavor, type, and formatted price.
     * @return a formatted string representation of the donut
     */
    @Override
    public String toString(){
        return flavor + " (" + type + ") - $" + String.format("%.2f", price);
    }
}
