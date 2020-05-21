package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends Activity {

    private ArrayList<String> mProductNames = new ArrayList<>();
    private ArrayList<String> mProductPrices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        final DatabaseDriverAndroidHelper mydb = new DatabaseDriverAndroidHelper(this);

        List<List<String>> itemList = mydb.getAllItemsHelper();
        for (List<String> item : itemList) {
            mProductNames.add(item.get(0));
            mProductPrices.add(item.get(1));
        }
        initRecyclerView();

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

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mProductNames, mProductPrices);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
