package com.example.cs213_project5.menu.coffee;

/**
 * Enum of the Coffee AddIns that includes all the possible Addins that could be added to coffee
 * @Author Lana Huang
 */
public enum AddIns {
    Cream("Whipped Cream"),
    Milk("2% Milk"),
    Vanilla("Vanilla"),
    Caramel("Caramel"),
    Mocha("Mocha");

    private String addIns;

    /**
     * Gives the string make.
     * @param addIns The make string.
     */
    AddIns(String addIns) {
        this.addIns = addIns;
    }

    /**
     * Getter method for the String equivalent Add Ins.
     * @return the string Add Ins
     */
    public String getAddIns() {
        return addIns;
    }


    /**
     * Converts a text string to its matching AddIns enum.
     * Case-insensitive match.
     * @param text The string to match
     * @return the matching AddIns enum, or null if no match
     */
    public static AddIns fromString(String text) {
        for (int i = 0; i < AddIns.values().length; i++) {
            AddIns a = AddIns.values()[i];
            if (a.getAddIns().equalsIgnoreCase(text)) {
                return a;
            }
        }
        return null;
    }
}
