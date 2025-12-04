package com.example.cs213_project5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cs213_project5.menu.coffee.AddIns;
import com.example.cs213_project5.menu.coffee.Coffee;
import com.example.cs213_project5.menu.coffee.CupSize;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Coffee Activity that shows the Order Coffee view, where user can order coffee with different customizations.
 * From the Order Donuts view you can navigate to the Menu or to Current Order.
 * Wired each button to each different activity and their corresponding view
 * @author Lana Huang
 */
public class CoffeeActivity extends AppCompatActivity {
    private Spinner sizeSpinner, quantitySpinner;
    private ChipGroup addInsGroup;
    private TextView subtotalText;
    private Coffee coffeeOrder;

    /**
     * This method is executed only once when first creating the Coffee activity.
     * It initializes and sets up buttons, Spinner, and ChipGroup that is used for user interaction.
     * It updates the Subtotal.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);
        coffeeOrder = new Coffee(); // default: Short, qty=1, no add-ins

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnOrders).setOnClickListener(v ->
                startActivity(new Intent(CoffeeActivity.this, OrderActivity.class))
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

    /**
     * This method initializes the spinner with the coffeesize array and sets up the adapter.
     * This includes the different coffee sizes: Short, Tall, Venti, Grande.
     */
    private void setupSizeSpinner() {
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this, R.array.coffeesize, android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * Event handler when selected implmented in the anonymous inner class.
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String sizeText = parent.getItemAtPosition(pos).toString();
                CupSize size = CupSize.valueOf(sizeText);
                coffeeOrder.setQuantity(coffeeOrder.getQuantity());
                coffeeOrder = new Coffee(coffeeOrder.getQuantity(), size, coffeeOrderAddIns());
                updateSubtotal();
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
     * This method initializes the spinner with quantity amount and sets up the adapter.
     */
    private void setupQuantitySpinner() {
        Integer[] qty = {1,2,3,4,5,6,7,8,9,10};

        ArrayAdapter<Integer> quantityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, qty);
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantitySpinner.setAdapter(quantityAdapter);

        quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * Event handler when selected implmented in the anonymous inner class.
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                int quantity = (int) parent.getItemAtPosition(pos);
                coffeeOrder = new Coffee(quantity, coffeeOrder.getSize(), coffeeOrderAddIns());
                updateSubtotal();
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
     * This method initializes the ChipGroup with each AddIn.
     * Adds selected AddIns to the coffee.
     */
     private void setupAddInsChips() {
        addInsGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            ArrayList<AddIns> selected = coffeeOrderAddIns();

            coffeeOrder = new Coffee(coffeeOrder.getQuantity(), coffeeOrder.getSize(), selected);

            updateSubtotal();
        });
    }

    /**
     * This method gets the selected AddIns from the ChipGroup and returns that in a list.
     * For each Chip in ChipGroup, if selected add to list, otherwise don't add to list.
     * @return ArrayList of coffee order AddIns
     */
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

    /**
     * This method is used to actually place the coffee into the current order.
     * It uses the global resource from the ShareResource class to get the current order.
     * It then adds the MenuItem to the current order and sends a confirmation as a AlertDialog
     */
    private void placeOrder() {
        ShareResource share = ShareResource.getInstance();
        if (share.getCurrentOrder() == null) {
            share.placeCurrentOrder();
        }
        share.addMenuItem(coffeeOrder);

        new AlertDialog.Builder(this).setTitle("Order Added").setMessage("Added " + coffeeOrder + " to current order.").setPositiveButton("OK", null).show();
    }

    /**
     * This method is used to dynamically update the subtotals each time the coffee order is customized.
     */
    private void updateSubtotal() {
        subtotalText.setText(String.format("$%.2f", coffeeOrder.price()));
        subtotalText.setEnabled(false);
        subtotalText.setFocusable(false);
        subtotalText.setClickable(false);
    }
}
