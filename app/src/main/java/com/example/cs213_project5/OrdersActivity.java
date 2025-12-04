package com.example.cs213_project5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cs213_project5.menu.MenuItem;
import com.example.cs213_project5.menu.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    private TextView subtotal, salesTax, grandTotal;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> itemNames;
    private int selectedIndex = -1;

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

        updateCosts();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
        });

        Button btnDelete = findViewById(R.id.btnDeleteMenuItem);
        btnDelete.setOnClickListener(v -> {
            if (selectedIndex != -1) {
                Order currentOrder = ShareResource.getInstance().getCurrentOrder();
                currentOrder.removeItem(currentOrder.getItems().get(selectedIndex));
                itemNames.remove(selectedIndex);
                adapter.notifyDataSetChanged();
                updateCosts();
                selectedIndex = -1;
                listView.clearChoices();
            }
        });

        findViewById(R.id.btnMenu).setOnClickListener(v ->
                startActivity(new Intent(OrdersActivity.this, MainActivity.class))
        );
    }

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

        updateCosts();
    }

    private void placeCurrentOrder() {
        ShareResource shared = ShareResource.getInstance();
        Order currentOrder = shared.getCurrentOrder();

        if (currentOrder.getItems().isEmpty()) {
            return;
        }
        Order orderCopy = new Order(currentOrder);

        shared.getOrderList().addOrder(orderCopy);

        shared.placeCurrentOrder();

        itemNames.clear();

        List<MenuItem> items = shared.getCurrentOrder().getItems();
        for (int i = 0; i < items.size(); i++) {
            itemNames.add(items.get(i).toString());
        }
        adapter.notifyDataSetChanged();
        updateCosts();

        new AlertDialog.Builder(this)
                .setTitle("Order Placed")
                .setMessage("Placed Order Number" + currentOrder.getOrderNumber())
                .setPositiveButton("OK", null)
                .show();
    }


    private void loadOrderItems() {
        itemNames = new ArrayList<>();
        Order currentOrder = ShareResource.getInstance().getCurrentOrder();
        List<MenuItem> items = currentOrder.getItems();
        for (int i = 0; i < items.size(); i++) {
            itemNames.add(items.get(i).toString());
        }
    }

    private void updateCosts() {
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