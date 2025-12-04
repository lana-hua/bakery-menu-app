package com.example.cs213_project5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cs213_project5.menu.MenuItem;
import com.example.cs213_project5.menu.Order;
import com.example.cs213_project5.menu.coffee.Coffee;
import com.example.cs213_project5.menu.coffee.CupSize;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Placed Orders Activity that shows the Placed Order view, where user can view and cancel all orders.
 * From the Placed Orders view you can navigate to the Menu.
 * Wired each button to each different activity and their corresponding view or to an event handler
 * @author Lana Huang
 */
public class PlacedOrdersActivity extends AppCompatActivity {
    private Spinner orderNumberSpinner;
    private TextView orderTotal, grandTotal;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> itemNames;
    private int selectedIndex = -1;

    /**
     * This method is executed only once when first creating the Placed Orders activity.
     * It initializes and sets up button, ListView, Spinner, and TextViews that is used for user interaction.
     * It updates the Order Total and the Grand Total.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_orders);

        orderNumberSpinner = findViewById(R.id.listOfOrdersSpinner);
        orderTotal = findViewById(R.id.orderTotalTextView);
        grandTotal = findViewById(R.id.grandTotalTextView);
        listView = findViewById(R.id.itemsInOrderListView);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // allow single selection


        findViewById(R.id.btnMenu).setOnClickListener(v ->
                startActivity(new Intent(PlacedOrdersActivity.this, MainActivity.class))
        );

        setupOrderSpinner();
        setupDeleteButton();
    }

    /**
     * This sets up the Order Spinner where each order placed is viewable
     * It displays by the Order Number and updates the ListView that corresponds with the items in the selected Order Number
     */
    private void setupOrderSpinner() {
        OrderList orderList = ShareResource.getInstance().getOrderList();

        ArrayList<String> orderNumbers = new ArrayList<>();

        List<Order> orders = orderList.getOrders();
        orderNumbers.clear();
        for (int i = 0; i < orders.size(); i++) {
            orderNumbers.add("Order #" + orders.get(i).getOrderNumber());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                orderNumbers
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderNumberSpinner.setAdapter(spinnerAdapter);
        orderNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * Event handler when selected implmented in the anonymous inner class.
             * It calls loadOrderItems to update the ListView.
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                selectedIndex = index;
                loadOrderItems(index);
            }

            /**
             * Event handler when nothing selected implmented in the anonymous inner class.
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * This method sets up the delete order button that gets the selected order and removes it from the list of orders
     * It sends a Toast delete message and refreshes the ListView and Spinner
     */
    private void setupDeleteButton() {
        Button deleteButton = findViewById(R.id.btnCancelOrder);

        deleteButton.setOnClickListener(v -> {
            if (selectedIndex < 0) {
                return; // no order selected
            }

            OrderList orderList = ShareResource.getInstance().getOrderList();

            showDeleteMessage(orderList.getOrders().get(selectedIndex).getOrderNumber());

            if (selectedIndex < orderList.getOrders().size()) {
                orderList.removeItem(orderList.getOrders().get(selectedIndex));
            }

            refreshAfterDelete();
        });
    }

    /**
     * This method refreshes the Spinner without the deleted Order
     */
    private void refreshAfterDelete() {
        OrderList orderList = ShareResource.getInstance().getOrderList();

        ArrayList<String> numbers = new ArrayList<>();
        numbers.clear();
        List<Order> orders = orderList.getOrders();
        for (int i = 0; i < orders.size(); i++) {
            numbers.add("Order #" + orders.get(i).getOrderNumber());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                numbers
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderNumberSpinner.setAdapter(spinnerAdapter);

        itemNames = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemNames);
        listView.setAdapter(adapter);

        orderTotal.setText("Order Total: $0.00");
        updateTotals();

        selectedIndex = -1;
    }

    /**
     * This method loads all the MenuItems from the selected Order from the order list.
     * @param orderIndex the index of the selected order
     */
    private void loadOrderItems(int orderIndex) {
        OrderList orderList = ShareResource.getInstance().getOrderList();
        Order selectedOrder = orderList.getOrders().get(orderIndex);

        itemNames = new ArrayList<>();
        for (int i = 0; i < selectedOrder.getItems().size(); i++) {
            itemNames.add(selectedOrder.getItems().get(i).toString());
        }

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                itemNames
        );
        listView.setAdapter(adapter);

        orderTotal.setText("Order Total: $" + String.format("%.2f", selectedOrder.getTotalCost()));
        updateTotals();
    }

    /**
     * This method shows a Toast message with the order number of the deleted order.
     * @param orderNum the order number of the deleted order.
     */
    private void showDeleteMessage(int orderNum) {
        String message = "Deleted Order Number " + orderNum;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * This method updates the grand total of all the orders in the order list.
     */
    private void updateTotals() {
        OrderList orderList = ShareResource.getInstance().getOrderList();

        if(orderList.getOrders().isEmpty()) {
            grandTotal.setText("Grand Total: $0.00");

        } else{
            grandTotal.setText("Grand Total: $" + String.format("%.2f", orderList.getTotalCost()));
        }
    }


}