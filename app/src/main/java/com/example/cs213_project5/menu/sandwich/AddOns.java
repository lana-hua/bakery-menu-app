package com.example.cs213_project5.menu.sandwich;

/**
 * Enum of the Sandwich Proteins that includes all the possible proteins that could be added to a sandwich.
 * @author Sharon Chen
 */
public enum AddOns {
    Cheese("Cheese", 1.00),
    Lettuce("Lettuce", 0.30),
    Onion("Onion", 0.30),
    Tomato("Tomato", 0.30);

    private String addOn;
    private double price;


    /**
     * Constructs an AddOn enum with the String AddOn and the price.
     * @param addOn the given AddOn in string format
     * @param price the given price
     */
    AddOns(String addOn, double price) {
        this.addOn = addOn;
        this.price = price;
    }

    /**
     * Getter for the string name of this add-on.
     * @return the display name of the add-on
     */
    public String getAddOn() {
        return addOn;
    }

    /**
     * Converts a string to its corresponding AddOns enum constant.
     * @param text the string to convert
     * @return the corresponding AddOns constant, or null if no match
     */
    public static AddOns fromString(String text) {
        for (AddOns a : AddOns.values()) {
            if (a.getAddOn().equalsIgnoreCase(text)) {
                return a;
            }
        }
        return null;
    }
}