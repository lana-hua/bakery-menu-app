package com.example.cs213_project5.menu.coffee;
import com.example.cs213_project5.menu.MenuItem;

import java.util.ArrayList;

/**
 * Represents a Coffee menu item, including size, quantity, and optional add-ins.
 * Calculates price based on selected cup size and number of add-ins.
 * @author Lana Huang
 */
public class Coffee extends MenuItem {
    private CupSize size;
    private ArrayList<AddIns> addIns;
    final double addInPrice = 0.25;

    /**
     * Default constructor for a Coffee object.
     * Initializes quantity to 1, size to Short, and creates an empty add-ins list.
     */
    public Coffee() {
        super(1);
        this.size = CupSize.Short;
        this.addIns = new ArrayList<>();
    }

    /**
     * Constructs a Coffee object with given quantity, cup size, and selected add-ins.
     * @param quantity the amount of coffees
     * @param size the cup size of the coffee
     * @param addins the list of add-ins chosen
     */
    public Coffee(int quantity, CupSize size, ArrayList<AddIns> addins) {
        super(quantity);
        this.size = size;
        this.addIns = addins;
    }

    /**
     * Overrides the toString method to returns a string representation of the Coffee object, including quantity, size, add-ins (if any), and price.
     * @return a string describing the coffee order
     */
    @Override
    public String toString() {
        String formattedPrice = String.format("%.2f", this.price());

        if (!addIns.isEmpty()) {
            return quantity + " " + size.toString() + " coffee with " + addIns + " for $" + formattedPrice;
        }
        return quantity + " " + size.toString() + " coffee for " + formattedPrice;

    }

    /**
     * Getter method that gets the size of the Coffee Item
     * @return the enum CupSize
     */
    public CupSize getSize() {
        return size;
    }

    /**
     * Overrides the MenuItem method price to return the order's corresponding price
     * @return price in double format
     */
    @Override
    public double price() {
        double coffeePrice = (this.size.getCupPrice() + addIns.size() * addInPrice) * quantity;
        return Math.round(coffeePrice * 100.0) / 100.0;
    }
}
