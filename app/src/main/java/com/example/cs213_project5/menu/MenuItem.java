package com.example.cs213_project5.menu;

/**
 * An abstract class MenuItem that contains a quantity and the price.
 * This class is the parent for Coffee, Sandwich, and the Donuts.
 * @author Lana Huang, Sharon Chen
 */
public abstract class MenuItem {
    protected int quantity;
    public abstract double price();

    /**
     * Constructs a MenuItem with the given quantity.
     * @param quantity the number of items being purchased
     */
    public MenuItem(int quantity){
        this.quantity = quantity;
    }

    /**
     * Getter method to get the quantity of a MenuItem.
     * @return the number of MenuItems
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter method to set the quantity of a MenuItem.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
