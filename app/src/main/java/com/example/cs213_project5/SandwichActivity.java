package com.example.cs213_project5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cs213_project5.menu.sandwich.AddOns;
import com.example.cs213_project5.menu.sandwich.Bread;
import com.example.cs213_project5.menu.sandwich.Protein;
import com.example.cs213_project5.menu.sandwich.Sandwich;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class SandwichActivity extends AppCompatActivity {
    private Spinner breadSpinner, proteinSpinner, sandwichQuantitySpinner;
    private ChipGroup addOnsGroup;
    private TextView subtotalText;
    private Sandwich sandwichOrder; // model object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwich);
        sandwichOrder = new Sandwich(); // default: Bagel, Beef, qty=1, no add-ons

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnOrders).setOnClickListener(v ->
                startActivity(new Intent(SandwichActivity.this, OrdersActivity.class))
        );
        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(v -> placeOrder());

        breadSpinner = findViewById(R.id.breadSpinner);
        proteinSpinner = findViewById(R.id.proteinSpinner);
        sandwichQuantitySpinner = findViewById(R.id.sandwichQuantitySpinner);
        subtotalText = findViewById(R.id.subtotalText);
        addOnsGroup = findViewById(R.id.addOnsChipGroup);

        setupBreadSpinner();
        setupProteinSpinner();
        setupQuantitySpinner();
        setupAddOnsChips();

        updateSubtotal();
    }

    private void setupBreadSpinner() {
        ArrayAdapter<CharSequence> breadAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.bread_types,
                android.R.layout.simple_spinner_item
        );
        breadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breadSpinner.setAdapter(breadAdapter);

        breadSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String breadText = parent.getItemAtPosition(pos).toString();
                Bread bread = Bread.fromString(breadText);
                sandwichOrder = new Sandwich(sandwichOrder.getQuantity(), bread,
                        sandwichOrder.getProteinType(), sandwichAddOns());
                updateSubtotal();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void setupProteinSpinner() {
        ArrayAdapter<CharSequence> proteinAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.protein_types,
                android.R.layout.simple_spinner_item
        );
        proteinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proteinSpinner.setAdapter(proteinAdapter);

        proteinSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String proteinText = parent.getItemAtPosition(pos).toString();
                Protein protein = Protein.fromString(proteinText);
                sandwichOrder = new Sandwich(sandwichOrder.getQuantity(),
                        sandwichOrder.getBreadType(), protein, sandwichAddOns());
                updateSubtotal();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void setupQuantitySpinner() {
        Integer[] quantities = {1,2,3,4,5,6,7,8,9,10};

        ArrayAdapter<Integer> quantityAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                quantities
        );
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sandwichQuantitySpinner.setAdapter(quantityAdapter);

        sandwichQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                int quantity = (int) parent.getItemAtPosition(pos);
                sandwichOrder = new Sandwich(quantity, sandwichOrder.getBreadType(),
                        sandwichOrder.getProteinType(), sandwichAddOns());
                updateSubtotal();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void setupAddOnsChips() {
        addOnsGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            ArrayList<AddOns> selected = sandwichAddOns();

            sandwichOrder = new Sandwich(
                    sandwichOrder.getQuantity(),
                    sandwichOrder.getBreadType(),
                    sandwichOrder.getProteinType(),
                    selected
            );

            updateSubtotal();
        });
    }
    private ArrayList<AddOns> sandwichAddOns() {
        ArrayList<AddOns> list = new ArrayList<>();

        List<Integer> checkedIds = addOnsGroup.getCheckedChipIds();
        for (int i = 0; i < checkedIds.size(); i++) {
            int id = checkedIds.get(i);
            Chip chip = findViewById(id);

            if (chip != null) {
                String addOnText = chip.getText().toString();
                AddOns a = AddOns.fromString(addOnText);

                if (a != null) {
                    list.add(a);
                }
            }
        }
        return list;
    }

    private void placeOrder() {
        ShareResource share = ShareResource.getInstance();

        if (share.getCurrentOrder() == null) {
            share.placeCurrentOrder();
        }

        share.addMenuItem(sandwichOrder);

        new AlertDialog.Builder(this)
                .setTitle("Order Added")
                .setMessage("Added " + sandwichOrder + " to current order.")
                .setPositiveButton("OK", null)
                .show();
    }

    private void updateSubtotal() {
        subtotalText.setText(String.format("$%.2f", sandwichOrder.price()));
        subtotalText.setEnabled(false);
        subtotalText.setFocusable(false);
        subtotalText.setClickable(false);
    }
}