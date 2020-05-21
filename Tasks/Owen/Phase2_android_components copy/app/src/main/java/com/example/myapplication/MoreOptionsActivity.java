package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MoreOptionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_options);

        Button employeeLogin = (Button) findViewById(R.id.empl_login);
        employeeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), EmployeeLoginActivity.class);
                startActivity(startIntent);
            }
        });

        Button customerLogin = (Button) findViewById(R.id.cust_login);
        customerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), CustomerLoginActivity.class);
                startActivity(startIntent);
            }
        });

        Button exit = (Button) findViewById(R.id.more_opt_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(startIntent);
            }
        });
    }
}
