package com.example.myapplication;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RestockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);
        final DatabaseDriverAndroidHelper mydb = new DatabaseDriverAndroidHelper(this);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.restock_container);

        Button restock = (Button) findViewById(R.id.restock_button);
        restock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText itemId = (EditText) findViewById(R.id.restock_item_id);
                int ItemId = Integer.parseInt(itemId.getText().toString());

                EditText itemQ = (EditText) findViewById(R.id.restock_item_q);
                int ItemQ = Integer.parseInt(itemQ.getText().toString());

                List<Integer> itemIds = new ArrayList<Integer>();
                List<List<String>> items = mydb.getAllItemsHelper();
                for(List<String> item : items){
                    itemIds.add( Integer.parseInt(item.get(0)));
                }
                boolean validItemId = itemIds.contains(ItemId);
                boolean success = false;
                if(validItemId){
                    success = mydb.updateInventoryQuantity(ItemQ, ItemId);
                }
                else{
                    TextView invalidId = new TextView(getApplicationContext());
                    String invalidIdMessage = "Please enter a valid item ID";
                    invalidId.setText(invalidIdMessage);
                    layout.addView(invalidId);
                }
                if(success){
                    TextView restockSuccess = new TextView(getApplicationContext());
                    String successMessage = "Item successfully restocked";
                    restockSuccess.setText(successMessage);
                    layout.addView(restockSuccess);
                }
                else{
                    TextView error = new TextView(getApplicationContext());
                    String errorMessage = "Something went wrong";
                    error.setText(errorMessage);
                    layout.addView(error);
                }
            }
        });
    }
}
