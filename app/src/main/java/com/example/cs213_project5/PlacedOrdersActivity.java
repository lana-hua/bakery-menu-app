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

public class PlacedOrdersActivity extends AppCompatActivity {
    private Spinner orderNumberSpinner;
    private TextView orderTotal, grandTotal;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> itemNames;
    private int selectedIndex = -1;

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
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                selectedIndex = index;
                loadOrderItems(index);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

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

    private void showDeleteMessage(int orderNum) {
        String message = "Deleted Order Number " + orderNum;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateTotals() {
        OrderList orderList = ShareResource.getInstance().getOrderList();

        if(orderList.getOrders().isEmpty()) {
            grandTotal.setText("Grand Total: $0.00");

        } else{
            grandTotal.setText("Grand Total: $" + String.format("%.2f", orderList.getTotalCost()));
        }
    }


}