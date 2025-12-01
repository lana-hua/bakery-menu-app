package com.example.cs213_project5.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Order that represents a customer order containing one or more MenuItem objects.
 * Each order has a unique and incremented order number and keeps track of the total cost of all menu items.
 * @author Lana Huang and Sharon Chen
 */
public class Order {
    private static int nextOrderNumber = 1;  // auto-increment order numbers
    private double totalCost;
    private final int orderNumber;
    private List<MenuItem> items;

    /**
     * Default constructor for creating a new order.
     * It automatically assigns a unique order number and initializes an empty list of menu items.
     */
    public Order() {
        this.orderNumber = nextOrderNumber++;
        this.items = new ArrayList<>();
        this.totalCost = calculateTotalPrice();
    }

    /**
     * Copy constructor to create a new order based on a given one.
     * @param other the existing order to copy
     */
    public Order(Order other) {
        this.orderNumber = other.orderNumber;  // keep same order #
        this.items = new ArrayList<>();

        for (MenuItem item : other.items) {
            this.items.add(item);   // shallow copy is fine if MenuItem is immutable
        }

        this.totalCost = other.totalCost;
    }

    /**
     * Returns the total cost of the order.
     * @return the total cost of all menu items
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Returns the order number.
     * @return the order number
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Returns the list of menu items in this order.
     * @return list of MenuItem objects
     */
    public List<MenuItem> getItems() {
        return items;
    }

    /**
     * Adds a menu item to the order and updates the total cost.
     * @param item the MenuItem to add
     */
    public void addItem(MenuItem item) {
        items.add(item);
        totalCost = calculateTotalPrice();
    }

    /**
     * Removes a menu item to the order and updates the total cost.
     * @param item the MenuItem to remove
     */
    public void removeItem(MenuItem item) {
        items.remove(item);
        totalCost = calculateTotalPrice();
    }

    /**
     * Calculates the total price of all menu items in the order.
     * @return the total price of all items
     */
    private double calculateTotalPrice() {
        double total = 0.0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).price();
        }
        return total;
    }

    /**
     * Overrides the toString method and returns a string representation of the order number and the number of items.
     * @return a string representation of the order
     */
    @Override
    public String toString() {
        return "Order #" + orderNumber + " (" + items.size() + " items)";
    }
}

