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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cs213_project5.menu.coffee.AddIns;
import com.example.cs213_project5.menu.coffee.Coffee;
import com.example.cs213_project5.menu.coffee.CupSize;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class CoffeeActivity extends AppCompatActivity {
    private Spinner sizeSpinner, quantitySpinner;
    private ChipGroup addInsGroup;
    private TextView subtotalText;
    private Coffee coffeeOrder; // model object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);
        coffeeOrder = new Coffee(); // default: Short, qty=1, no add-ins

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnOrders).setOnClickListener(v ->
                startActivity(new Intent(CoffeeActivity.this, OrdersActivity.class))
        );
        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(v -> placeOrder());
        sizeSpinner = findViewById(R.id.coffeeSizeSpinner);
        quantitySpinner = findViewById(R.id.coffeeQuantitySpinner);
        subtotalText = findViewById(R.id.subtotalText);
        addInsGroup = findViewById(R.id.addInChipGroup);

        setupSizeSpinner();
        setupQuantitySpinner();
        setupAddInsChips();

        updateSubtotal();
    }

    private void setupSizeSpinner() {
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.coffeesize,
                android.R.layout.simple_spinner_item
        );
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String sizeText = parent.getItemAtPosition(pos).toString();
                CupSize size = CupSize.valueOf(sizeText);
                coffeeOrder.setQuantity(coffeeOrder.getQuantity()); // keep same quantity
                coffeeOrder = new Coffee(coffeeOrder.getQuantity(), size, coffeeOrderAddIns());
                updateSubtotal();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void setupQuantitySpinner() {
        Integer[] qty = {1,2,3,4,5,6,7,8,9,10};

        ArrayAdapter<Integer> quantityAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                qty
        );
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantitySpinner.setAdapter(quantityAdapter);

        quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                int quantity = (int) parent.getItemAtPosition(pos);
                coffeeOrder = new Coffee(quantity, coffeeOrder.getSize(), coffeeOrderAddIns());
                updateSubtotal();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

     private void setupAddInsChips() {
        addInsGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            ArrayList<AddIns> selected = coffeeOrderAddIns();

            coffeeOrder = new Coffee(
                    coffeeOrder.getQuantity(),
                    coffeeOrder.getSize(),
                    selected
            );

            updateSubtotal();
        });
    }

    // return list of AddIns currently selected
    private ArrayList<AddIns> coffeeOrderAddIns() {
        ArrayList<AddIns> list = new ArrayList<>();

        List<Integer> checkedIds = addInsGroup.getCheckedChipIds();
        for (int i = 0; i < checkedIds.size(); i++) {
            int id = checkedIds.get(i);
            Chip chip = findViewById(id);

            if (chip != null) {
                String addInText = chip.getText().toString();
                AddIns a = AddIns.fromString(addInText);

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

        // Add the coffee to the singleton order
        share.addMenuItem(coffeeOrder);

        new AlertDialog.Builder(this)
                .setTitle("Order Added")
                .setMessage("Added " + coffeeOrder + " to current order.")
                .setPositiveButton("OK", null)
                .show();
    }

    private void updateSubtotal() {
        subtotalText.setText(String.format("$%.2f", coffeeOrder.price()));
        subtotalText.setEnabled(false);
        subtotalText.setFocusable(false);
        subtotalText.setClickable(false);
    }
}
