package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShoppingCartActivity extends Activity {

    private ArrayList<String> mProductNames = new ArrayList<>();
    private ArrayList<String> mProductPrices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        testAddProducts();

        Button storeCart = (Button) findViewById(R.id.store_cart_in_accounts);
        storeCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), AccountIdPromptActivity.class);
                startActivity(startIntent);
            }
        });

        ImageButton exit = (ImageButton) findViewById(R.id.exit_shopping_cart);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
