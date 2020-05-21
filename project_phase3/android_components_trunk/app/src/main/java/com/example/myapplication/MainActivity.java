package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DatabaseDriverAndroidHelper mydb = new DatabaseDriverAndroidHelper(this);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.employee_container);

        Button authenticate = (Button) findViewById(R.id.auth);
        authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button newAccount = (Button) findViewById(R.id.new_acc);
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), NewAccountActivity.class);
                startActivity(startIntent);
            }
        });

        Button newEmployee = (Button) findViewById(R.id.new_empl);
        newEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), CreateEmployeeActivity.class);
                startActivity(startIntent);
            }
        });

        Button newCustomer = (Button) findViewById(R.id.new_cust);
        newCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), CreateCustomerActivity.class);
                startActivity(startIntent);
            }
        });

        Button restock = (Button) findViewById(R.id.restock);
        restock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), RestockActivity.class);
                startActivity(startIntent);
            }
        });

        Button shopOnCustomersBehalf = (Button) findViewById(R.id.shop_on_customers_behalf);
        shopOnCustomersBehalf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), CustomerIdPromptActivity.class);
                startActivity(startIntent);
            }
        });

        Button exit = (Button) findViewById(R.id.empl_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(startIntent);
            }
        });




    }
}
