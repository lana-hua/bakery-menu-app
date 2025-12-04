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

/**
 * Represents the activity for displaying and ordering donuts.
 * This class is used to allow users to select donuts from a RecyclerView and add them to their current order.
 * @author Sharon Chen
 */
public class DonutActivity extends AppCompatActivity {
    private RecyclerView donutRecyclerView;
    private TextView subtotalTextView;
    private DonutAdapter donutAdapter;
    private List<DonutItem> donutItems;
    private double donutSubtotal = 0.0;

    /**
     * Initializes the activity and sets up the UI components.
     * @param savedInstanceState the saved instance state
     */
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

    /**
     * Creates a List of available donut items with their details.
     * @return a List of DonutItem objects
     */
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

    /**
     * Handles click events on donut items in the RecyclerView.
     * @param donut the clicked donut item
     * @param position the position of the clicked item
     */
    public void onDonutClicked(DonutItem donut, int position) {
        showQuantityDialog(donut, position);
    }

    /**
     * Shows a dialog for selecting the quantity of donuts to add.
     * @param donut the donut item to add
     * @param position the position of the donut item
     */
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

    /**
     * Sets up the quantity spinner with values 1-10.
     * @param spinner the spinner to set up
     */
    private void setupQuantitySpinner(Spinner spinner) {
        Integer[] quantities = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, quantities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPadding(50, 12, 50, 12);
    }

    /**
     * Adds the selected donut to the order.
     * @param donut the donut item to add
     * @param quantity the quantity to add
     * @param position the position of the donut item
     */
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

    /**
     * Updates the subtotal TextView with the current donut subtotal.
     */
    private void updateSubtotal() {
        subtotalTextView.setText(String.format("Subtotal: $%.2f", donutSubtotal));
    }

    /**
     * Shows an error message for invalid quantity selection.
     */
    private void showQuantityError() {
        Toast.makeText(this, "Please select a valid quantity", Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows a success message after adding donuts to the order.
     * @param donutName the name of the donut added
     * @param quantity the quantity added
     */
    private void showSuccessMessage(String donutName, int quantity) {
        String message = "Added " + quantity + " " + donutName + " to order";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Creates a MenuItem object from a DonutItem for the order.
     * @param donutItem the donut item to convert
     * @param quantity the quantity of donuts
     * @return the created MenuItem object
     */
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