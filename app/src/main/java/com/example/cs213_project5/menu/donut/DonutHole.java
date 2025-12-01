package com.example.cs213_project5.menu.donut;
import com.example.cs213_project5.menu.MenuItem;

/**
 * Represents a donut hole menu item with specific flavors and pricing.
 * Extends the MenuItem class to inherit the quantity functionality.
 * @author Sharon Chen
 */
public class DonutHole extends MenuItem {
    private String flavor;
    private static final double price = 0.39;

    public static final String PLAIN = "Plain";
    public static final String JELLY = "Jelly";
    public static final String CHOCOLATE = "Chocolate";

    /**
     * Constructs a DonutHole with specified quantity and flavor.
     * @param quantity the number of donut holes
     * @param flavor the flavor of the donut hole
     */
    public DonutHole(int quantity, String flavor){
        super(quantity);
        this.flavor = flavor;
    }

    /**
     * Calculates the price of the donut hole order from base price and quantity.
     * @return the total price for the donut hole order
     */
    @Override
    public double price() {
        double total = price * quantity;
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Returns the base price of a singular donut hole.
     * @return the base price of a single donut hole
     */
    public static double basePrice(){
        return price;
    }

    /**
     * Returns a string of the donut hole order with flavor and price details.
     * @return the string representation of the donut hole order
     */
    @Override
    public String toString() {
        String formattedPrice = String.format("%.2f", this.price());

        if (quantity == 1) {
            return quantity + " " + flavor + " Donut Hole for $" + formattedPrice;
        } else {
            return quantity + " " + flavor + " Donut Holes for $" + formattedPrice;
        }
    }
}
