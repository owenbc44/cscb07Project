package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerActivity extends Activity {

    private ArrayList<String> mProductNames = new ArrayList<>();
    private ArrayList<String> mProductPrices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        testAddProducts();

        ImageButton retrieveCart = (ImageButton) findViewById(R.id.retrieve_cart_button);
        retrieveCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), AccountIdPromptActivity.class);
                startActivity(startIntent);
            }
        });

        ImageButton cart = (ImageButton) findViewById(R.id.shopping_cart_button);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), ShoppingCartActivity.class);
                startActivity(startIntent);
            }
        });

    }

    private void testAddProducts() {
        mProductNames.add("FISHING ROD");
        mProductPrices.add("10");
        mProductNames.add("HOCKEY STICK");
        mProductPrices.add("10");
        mProductNames.add("SKATES");
        mProductPrices.add("10");

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mProductNames, mProductPrices);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
