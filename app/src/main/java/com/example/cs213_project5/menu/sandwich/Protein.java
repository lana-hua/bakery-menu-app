package com.example.cs213_project5.menu.sandwich;

/**
 * Enum of the Sandwich Protein types that includes all the possible bread types for the sandwich
 * @author Sharon Chen
 */
public enum Protein {
    Beef("Beef", 12.99),
    Chicken("Chicken", 10.99),
    Salmon("Salmon", 14.99);

    private String protein;
    private double price;

    /**
     * Constructs a Protein enum with a string name.
     * @param protein the string name of the protein type
     * @param price the double price of the protein type
     */
    Protein(String protein, double price) {
        this.protein = protein;
        this.price = price;
    }

    /**
     * Getter method for the price of the protein.
     * @return the price of the protein
     */
    public double getPrice() {
        return price;
    }

    /**
     * Converts a string to its matching Protein enum.
     * @param text the string to convert into a Protein type
     * @return the matching Protein enum constant, or null if no match is found
     */
    public static Protein fromString(String text) {
        for (Protein proteinType : Protein.values()) {
            if (proteinType.protein.equals(text)) {
                return proteinType;
            }
        }
        return null;
    }

    /**
     * Overrides the toString method to return the string name of the protein type.
     * @return the string representation of the protein
     */
    @Override
    public String toString() {
        return protein;
    }
}

