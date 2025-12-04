package com.example.cs213_project5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DonutActivity extends AppCompatActivity {
    private RecyclerView donutRecyclerView;
    private TextView subtotalTextView;
    private DonutAdapter donutAdapter;
    private List<DonutItem> donutItems;
    private double donutSubtotal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut);

        donutRecyclerView = findViewById(R.id.donutRecyclerView);
        subtotalTextView = findViewById(R.id.subtotalTextView);
        subtotalTextView.setText("Subtotal: $0.00");

        donutItems = createDonutList();
        donutAdapter = new DonutAdapter(this, donutItems, this::onDonutClicked);
        donutRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        donutRecyclerView.setAdapter(donutAdapter);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        Button btnOrders = findViewById(R.id.btnOrders);
        btnOrders.setOnClickListener(v ->
                startActivity(new Intent(DonutActivity.this, OrderActivity.class))
        );
    }

    private List<DonutItem> createDonutList() {
        List<DonutItem> items = new ArrayList<>();

        items.add(new DonutItem("Plain", "Cake Donut", 2.19, R.drawable.plain_cake_donut));
        items.add(new DonutItem("Glazed", "Cake Donut", 2.19, R.drawable.glazed_cake_donut));
        items.add(new DonutItem("Chocolate Frosted", "Cake Donut", 2.19, R.drawable.chocolate_frosed_cake_donut));

        items.add(new DonutItem("Plain", "Donut Hole", 0.39, R.drawable.plaindonuthole));
        items.add(new DonutItem("Jelly", "Donut Hole", 0.39, R.drawable.jellydonuthole));
        items.add(new DonutItem("Chocolate", "Donut Hole", 0.39, R.drawable.chocolatedonuthole));

        items.add(new DonutItem("Pumpkin Spice", "Seasonal Donut", 2.49, R.drawable.pumpkin_spice_seasonal_donut));
        items.add(new DonutItem("Spooky", "Seasonal Donut", 2.49, R.drawable.spooky_seasonal_donut));

        items.add(new DonutItem("Plain", "Yeast Donut", 1.99, R.drawable.plain_yeast_donut));
        items.add(new DonutItem("Glazed", "Yeast Donut", 1.99, R.drawable.glazed_yeast_donut));
        items.add(new DonutItem("Chocolate Frosted", "Yeast Donut", 1.99, R.drawable.chocolate_frosed_yeast_donut));
        items.add(new DonutItem("Strawberry Frosted", "Yeast Donut", 1.99, R.drawable.strawberry_frosted_yeast_donut));
        items.add(new DonutItem("Powdered Sugar", "Yeast Donut", 1.99, R.drawable.powdered_sugar_yeast_donut));
        items.add(new DonutItem("Cinnamon Sugar", "Yeast Donut", 1.99, R.drawable.cinnamon_sugar_yeast_donut));

        return items;
    }

    public void onDonutClicked(DonutItem donut, int position) {
        showQuantityDialog(donut, position);
    }

    private void showQuantityDialog(DonutItem donut, int position) {
        Spinner spinner = new Spinner(this);
        setupQuantitySpinner(spinner);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add " + donut.getFlavor() + donut.getType())
                .setMessage("Select quantity:")
                .setView(spinner)
                .setPositiveButton("Add to Order", (d, which) -> {
                    int quantity = (Integer) spinner.getSelectedItem();
                    addDonutToOrder(donut, quantity, position);
                })
                .setNegativeButton("Cancel", null)
                .create();

        dialog.show();
    }

    private void setupQuantitySpinner(Spinner spinner) {
        Integer[] quantities = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, quantities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPadding(50, 12, 16, 50);
    }

    private void addDonutToOrder(DonutItem donut, int quantity, int position) {
        if (quantity <= 0) {
            showQuantityError();
            return;
        }

        com.example.cs213_project5.menu.MenuItem menuItem = createDonutMenuItem(donut, quantity);
        ShareResource.getInstance().addMenuItem(menuItem);

        donutSubtotal += donut.getPrice() * quantity;
        updateSubtotal();

        showSuccessMessage(String.format(donut.getFlavor() + " " + donut.getType()), quantity);
    }

    private void updateSubtotal() {
        subtotalTextView.setText(String.format("Subtotal: $%.2f", donutSubtotal));
    }

    private void showQuantityError() {
        Toast.makeText(this, "Please select a valid quantity", Toast.LENGTH_SHORT).show();
    }

    private void showSuccessMessage(String donutName, int quantity) {
        String message = "Added " + quantity + " " + donutName + " to order";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private com.example.cs213_project5.menu.MenuItem createDonutMenuItem(DonutItem donutItem, int quantity) {
        String type = donutItem.getType();
        String flavor = donutItem.getFlavor();

        switch (type) {
            case "Yeast Donut":
                return new com.example.cs213_project5.menu.donut.YeastDonut(quantity, flavor);
            case "Cake Donut":
                return new com.example.cs213_project5.menu.donut.CakeDonut(quantity, flavor);
            case "Donut Hole":
                return new com.example.cs213_project5.menu.donut.DonutHole(quantity, flavor);
            case "Seasonal Donut":
                return new com.example.cs213_project5.menu.donut.SeasonalDonut(quantity, flavor);
            default:
                return null;
        }
    }
}