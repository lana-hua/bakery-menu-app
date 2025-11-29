package com.example.cs213_project5.menu.sandwich;

/**
 * Enum of the Sandwich Bread types that includes all the possible bread types for the sandwich
 * @author Sharon Chen
 */
public enum Bread {
    Bagel("Bagel"),
    Sourdough("Sourdough"),
    Wheat("Wheat Bread");

    private String bread;

    /**
     * Constructs a Bread enum with a string name.
     * @param bread the string name of the bread type
     */
    Bread(String bread) {
        this.bread = bread;
    }

    /**
     * Converts a string to its matching Bread enum.
     * @param text the string to convert into a Bread type
     * @return the matching Bread enum constant, or null if no match is found
     */
    public static Bread fromString(String text) {
        for (Bread breadType : Bread.values()) {
            if (breadType.bread.equals(text)) {
                return breadType;
            }
        }
        return null;
    }

    /**
     * Overrides the toString method to return the string name of the bread type.
     * @return the string representation of the bread
     */
    @Override
    public String toString() {
        return bread;
    }
}
