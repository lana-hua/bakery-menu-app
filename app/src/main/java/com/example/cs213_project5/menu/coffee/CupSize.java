package com.example.cs213_project5.menu.coffee;

/**
 * Enum of the Coffee Sizes that includes all the possible Sizes that can be ordered
 * @Author Lana Huang
 */
public enum CupSize {
    Short(2.39),
    Tall(2.99),
    Grande(3.59),
    Venti(4.19);

    private final double price;

    /**
     * Gives the string size.
     * @param price The size string.
     */
    CupSize(double price) {
        this.price = price;
    }

    /**
     * Getter method that gets the price of the cup.
     * @return price in double format
     */
    public double getCupPrice() {
        return price;
    }
}
