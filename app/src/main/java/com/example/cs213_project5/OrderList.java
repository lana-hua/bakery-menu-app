package com.example.cs213_project5;

import com.example.cs213_project5.menu.MenuItem;
import com.example.cs213_project5.menu.Order;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of placed orders.
 * Maintains a list of orders, the total cost of all orders.
 * Allows user to add, remove, and export orders.
 * @author Lana Huang and Sharon Chen
 */
public class OrderList {
    private int numOfOrders = 0;
    private List<Order> orders;
    private double totalCost;

    /**
     * Constructs a new and empty OrderList.
     */
    public OrderList() {
        this.orders = new ArrayList<>();
        this.totalCost = calculateTotalPrice();
    }

    /**
     * Returns the total cost of all orders.
     * @return total cost of all orders
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Returns the observable list of orders.
     * @return List of Order objects
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Adds a new order to the list.
     * Updates the total cost and increments the number of orders.
     * @param order the Order to add
     */
    public void addOrder(Order order) {
        orders.add(order);
        totalCost = calculateTotalPrice();
        numOfOrders++;
    }

    /**
     * Removes an order from the list.
     * @param order the Order to remove
     */
    public void removeItem(Order order) {
        orders.remove(order);
        totalCost = calculateTotalPrice();
        numOfOrders--;
    }

    /**
     * Calculates the total cost of all orders in the list.
     * @return the total cost of all orders
     */
    private double calculateTotalPrice() {
        double total = 0.0;
        for (int i = 0; i < orders.size(); i++) {
            total += orders.get(i).getTotalCost();
        }
        return total;
    }
}

