package com.example.cs213_project5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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

        btnOrders.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, OrdersActivity.class))
        );
    }


}
