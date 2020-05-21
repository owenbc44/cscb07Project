package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreateCustomerActivity extends Activity {
    public static int customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);
        final DatabaseDriverAndroidHelper mydb = new DatabaseDriverAndroidHelper(this);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.create_customer_container);

        Button create = (Button) findViewById(R.id.create_customer_button);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText CustomerName = (EditText) findViewById(R.id.cuser_name);
                EditText CustomerAge = (EditText) findViewById(R.id.cuser_age);
                EditText CustomerAddress = (EditText) findViewById(R.id.cuser_address);
                EditText CustomerPassword = (EditText) findViewById(R.id.cuser_password);


                String customerName = CustomerName.getText().toString();
                int customerAge = Integer.parseInt(CustomerAge.getText().toString());
                String customerAddress = CustomerAddress.getText().toString();
                String customerPassword = CustomerPassword.getText().toString();
                Log.d("tag", "beepboop");
                boolean errorWhileInitialisingCustomer = true;

                while (errorWhileInitialisingCustomer) {

                    try {
                        customerId = (int) mydb.insertNewUser(customerName,
                                Integer.valueOf(customerAge), customerAddress, customerPassword);
                        Log.d("tag", "beeepbooop");
                        int customerRoleId = 3;

                        mydb.insertUserRole(customerId, customerRoleId);
                        Log.d("tag", "beeeepboooop");
                        errorWhileInitialisingCustomer = false;

                    } catch (NumberFormatException e) {
                        TextView error = new TextView(getApplicationContext());
                        String errorText = "Please ensure that all fields have been entered correctly";
                        error.setText(errorText);
                        layout.addView(error);
                    }
                }
                Log.d("tag", "beeeeepbooooop");

                if(!errorWhileInitialisingCustomer){
                    Intent startIntent = new Intent(getApplicationContext(), Popup2Activity.class);
                    startActivity(startIntent);
                }
            }
        });
    }
}
