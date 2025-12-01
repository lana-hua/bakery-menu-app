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

    Protein(String protein, double price) {
        this.protein = protein;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return protein;
    }
}

