package com.example.cs213_project5;

import com.example.cs213_project5.menu.MenuItem;
import com.example.cs213_project5.menu.Order;

/**
 * This class implements the Singleton Design Pattern
 * An instance of this class is holding the orders shared by all activities
 * @author Sharon Chen
 */
public class ShareResource {
    private static ShareResource resource;
    private Order currentOrder;
    private OrderList orderList;

    /**
     * Private constructor
     */
    private ShareResource() {
        currentOrder = new Order();
        orderList = new OrderList();
    }

    /**
     * If the instance is not created yet, create one, otherwise return the instance (lazy approach.)
     * The synchronized keyword is essential to avoid problems in multi-threaded programs.
     * @return the reference of the only instance of this class
     */
    public static synchronized ShareResource getInstance() {
        if (resource == null)
            resource = new ShareResource();
        return resource;
    }

    /**
     * Gets the current order
     * Return the current order
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Gets the list of orders
     * Return the order list
     */
    public OrderList getOrderList() {
        return orderList;
    }

    /**
     * Adds a menu item to the current order
     * @param item the MenuItem to add
     */
    public void addMenuItem(MenuItem item) {
        currentOrder.addItem(item);
    }

    /**
     * Places current order and starts a new one
     */
    public void placeCurrentOrder() {
        if (!currentOrder.getItems().isEmpty()){
            orderList.addOrder(new Order(currentOrder));
            currentOrder = new Order();
        }
    }
}
