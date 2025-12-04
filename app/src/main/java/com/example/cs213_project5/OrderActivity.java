package com.example.cs213_project5;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cs213_project5.menu.MenuItem;
import com.example.cs213_project5.menu.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Order Activity that shows the Current Order view, where user can view, change, and place their current order.
 * From the Current Order view you can navigate to the Menu.
 * Wired each button to each different activity and their corresponding view or to an event handler
 * @author Lana Huang
 */
public class OrderActivity extends AppCompatActivity {
    private TextView subtotal, salesTax, grandTotal;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> itemNames;
    private int selectedIndex = -1;

    /**
     * This method is executed only once when first creating the Order activity.
     * It initializes and sets up buttons, ListView, and TextViews that is used for user interaction.
     * It updates the Subtotal.
     * It also deletes a MenuItem if selected and deleted
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        subtotal = findViewById(R.id.subtotal);
        salesTax = findViewById(R.id.salesTax);
        grandTotal = findViewById(R.id.total);
        listView = findViewById(R.id.currentOrdersListView);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // allow single selection
        Button btnAddOrder = findViewById(R.id.btnPlaceOrder);
        btnAddOrder.setOnClickListener(v -> placeCurrentOrder());

        loadOrderItems();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, itemNames);
        listView.setAdapter(adapter);

        updateTotals();
        listView.setOnItemClickListener((parent, view, position, id) -> {selectedIndex = position;});

        Button btnDelete = findViewById(R.id.btnDeleteMenuItem);
        btnDelete.setOnClickListener(v -> {
            if (selectedIndex != -1) {
                Order currentOrder = ShareResource.getInstance().getCurrentOrder();
                showDeleteMessage(currentOrder.getItems().get(selectedIndex));
                currentOrder.removeItem(currentOrder.getItems().get(selectedIndex));
                itemNames.remove(selectedIndex);
                adapter.notifyDataSetChanged();
                updateTotals();
                selectedIndex = -1;
                listView.clearChoices();
            }
        });

        findViewById(R.id.btnMenu).setOnClickListener(v -> startActivity(new Intent(OrderActivity.this, MainActivity.class)));
    }

    /**
     * This method uses Toast to make a popup alert that the MenuItem is deleted
     * @param item MenuItem that's being deleted
     */
    private void showDeleteMessage(MenuItem item) {
        String message = "Deleted " + item.toString();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * When the activity comes to the foreground and interacts with the user.
     * Updates the itemNames with the current order's items.
     */
    @Override
    protected void onResume() {
        super.onResume();
        itemNames.clear();
        List<MenuItem> items = ShareResource.getInstance().getCurrentOrder().getItems();
        for (int i = 0; i < items.size(); i++) {
            MenuItem m = items.get(i);
            itemNames.add(m.toString());
        }
        adapter.notifyDataSetChanged();

        updateTotals();
    }

    /**
     * This method places the current order to the list of orders from the ShareResource class.
     * It sends out an AlertDialog after placing the order.
     */
    private void placeCurrentOrder() {
        ShareResource shared = ShareResource.getInstance();
        Order currentOrder = shared.getCurrentOrder();

        if (currentOrder.getItems().isEmpty()) {
            return;
        }

        shared.placeCurrentOrder();

        itemNames.clear();

        List<MenuItem> items = shared.getCurrentOrder().getItems();
        for (int i = 0; i < items.size(); i++) {
            itemNames.add(items.get(i).toString());
        }
        adapter.notifyDataSetChanged();
        updateTotals();

        new AlertDialog.Builder(this)
                .setTitle("Order Placed")
                .setMessage("Placed Order Number " + currentOrder.getOrderNumber())
                .setPositiveButton("OK", null)
                .show();
    }


    /**
     * This method is for loading the order items into itemNames
     */
    private void loadOrderItems() {
        itemNames = new ArrayList<>();
        Order currentOrder = ShareResource.getInstance().getCurrentOrder();
        List<MenuItem> items = currentOrder.getItems();
        for (int i = 0; i < items.size(); i++) {
            itemNames.add(items.get(i).toString());
        }
    }

    /**
     * Used for calculating the totals to update the totals everytime the order is changed.
     */
    private void updateTotals() {
        Order currentOrder = ShareResource.getInstance().getCurrentOrder();

        if(currentOrder.getItems().isEmpty()) {
            subtotal.setText("Subtotal: $0.00");
            salesTax.setText("Sales Tax: $0.00");
            grandTotal.setText("Total: $0.00");
        } else {
            subtotal.setText(String.format("Subtotal: $%.2f", currentOrder.getTotalCost()));
            salesTax.setText(String.format("Sales Tax: $%.2f", currentOrder.getTotalCost()*.06625));
            grandTotal.setText(String.format("Total: $%.2f", currentOrder.getTotalCost()*.06625+currentOrder.getTotalCost()));
        }

    }
}