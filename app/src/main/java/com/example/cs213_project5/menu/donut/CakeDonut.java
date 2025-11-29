package com.example.cs213_project5.menu.donut;
import com.example.cs213_project5.menu.MenuItem;

/**
 * Represents a cake donut menu item with specific flavors and pricing.
 * Extends the MenuItem class to inherit the quantity functionality.
 * @author Sharon Chen
 */
public class CakeDonut extends MenuItem {
    private String flavor;
    private static final double price = 2.19;

    public static final String PLAIN = "Plain";
    public static final String GLAZED = "Glazed";
    public static final String CHOCOLATE_FROSTED = "Chocolate Frosted";

    /**
     * Constructs a CakeDonut with specified quantity and flavor.
     * @param quantity the number of cake donuts
     * @param flavor the flavor of the cake donut
     */
    public CakeDonut(int quantity, String flavor){
        super(quantity);
        this.flavor = flavor;
    }

    /**
     * Calculates the price of the cake donut order from base price and quantity.
     * @return the total price for the cake donut order
     */
    @Override
    public double price() {
        double total = price * quantity;
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Returns the base price of a singular cake donut.
     * @return the base price of a single cake donut
     */
    public static double basePrice(){
        return price;
    }

    /**
     * Returns a string of the cake donut order with flavor and price details.
     * @return the string representation of the cake donut order
     */
    @Override
    public String toString() {
        String formattedPrice = String.format("%.2f", this.price());

        if (quantity == 1) {
            return quantity + " " + flavor + " Cake Donut for $" + formattedPrice;
        } else {
            return quantity + " " + flavor + " Cake Donuts for $" + formattedPrice;
        }
    }
}
