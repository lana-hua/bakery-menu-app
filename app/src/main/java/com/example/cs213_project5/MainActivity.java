package com.example.cs213_project5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Main Activity that shows the Main Menu Screen.
 * From the Main Menu you can navigate to Donut, Coffee, Sandwich, Current Order, and Orders Placed screens
 * Wired each button to each different activity and their corresponding view
 * @author Lana Huang
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This method is executed when the system first creates the activity.
     * It instantiates each button on the Main Menu including:
     *  Order Coffee
     *  Order Donuts
     *  Order Sandwich
     *  View Current Order
     *  View All Orders
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnCoffee = findViewById(R.id.btnCoffee);
        Button btnDonut = findViewById(R.id.btnDonut);
        Button btnSandwich = findViewById(R.id.btnSandwich);
        Button btnOrders = findViewById(R.id.btnCurrentOrder);
        Button btnAllOrders = findViewById(R.id.btnAllOrders);

        btnCoffee.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CoffeeActivity.class))
        );

        btnDonut.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, DonutActivity.class))
        );

        btnSandwich.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SandwichActivity.class))
        );

        btnOrders.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, OrdersActivity.class))
        );

        btnAllOrders.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, PlacedOrdersActivity.class))
        );
    }


}
