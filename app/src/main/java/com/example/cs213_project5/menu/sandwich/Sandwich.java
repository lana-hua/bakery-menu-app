package com.example.cs213_project5.menu.sandwich;
import com.example.cs213_project5.menu.MenuItem;
import java.util.ArrayList;

/**
 * Sandwich class that represents a Sandwich menu item.
 * This includes selected bread type, protein, additional add-ons, and quantity.
 * Calculates price based on protein price and add-on costs.
 * @author Sharon Chen
 */
public class Sandwich extends MenuItem {
    private Bread breadType;
    private Protein proteinType;
    private ArrayList<AddOns> addOns;

    /**
     * Default contructor for the sandwich object
     */
    public Sandwich() {
        super(1);
        this.breadType = Bread.Bagel;
        this.proteinType = Protein.Beef;
        this.addOns = new ArrayList<>();
    }

    /**
     * Constructs a Sandwich object with given quantity, bread type, protein type, and list of add-ons.
     * @param quantity the number of sandwiches ordered
     * @param breadType the selected bread type
     * @param proteinType the selected protein type
     * @param addOnsList list of add-ons selected for the sandwich
     */
    public Sandwich(int quantity, Bread breadType, Protein proteinType, ArrayList<AddOns> addOnsList) {
        super(quantity);
        this.breadType = breadType;
        this.proteinType = proteinType;
        this.addOns = addOnsList;
    }

    /**
     * Getter method for the Bread object of the type of bread for the sandwich order.
     * @return the bread Type
     */
    public Bread getBreadType() {
        return breadType;
    }

    /**
     * Getter method for the Protein object of the type of protein for the sandwich order.
     * @return the protein type
     */
    public Protein getProteinType() {
        return proteinType;
    }

    /**
     * Getter method for the number of sandwiches ordered.
     * @return the quantity of sandwiches ordered
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Calculates the total price of the sandwich order.
     * The price is determined by protein, add-ons, and quantity.
     * @return the total price of the sandwich order rounded to 2 decimal points
     */
    @Override
    public double price() {
        double proteinPrice = proteinType.getPrice();
        double addOnsPrice = 0.0;

        for (int i = 0; i < addOns.size(); i++) {
            AddOns addOn = addOns.get(i);
            if (addOn == AddOns.Cheese) {
                addOnsPrice += 1.00;
            } else {
                addOnsPrice += 0.30;
            }
        }

        double total = (proteinPrice + addOnsPrice) * quantity;
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Returns a string representation of the Sandwich item, including quantity, protein, bread type, add-ons, and formatted price.
     * @return a readable description of the sandwich order in 2 decimal format
     */
    @Override
    public String toString() {
        String formattedPrice = String.format("%.2f", this.price());

        if (!addOns.isEmpty()) {
            return quantity + " " + proteinType.toString() + " " + breadType.toString() + " Sandwich with " + addOns + " for $" + formattedPrice;
        }

        return quantity + " " + proteinType.toString() + " " + breadType.toString() + " Sandwich for $" + formattedPrice;
    }
}

