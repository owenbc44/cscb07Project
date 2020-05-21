package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.security.PasswordHelpers;

import java.util.List;

public class CustomerLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        final DatabaseDriverAndroidHelper mydb = new DatabaseDriverAndroidHelper(this);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.customer_login_container);

        Button signIn = (Button) findViewById(R.id.sign_in_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText id = (EditText) findViewById(R.id.login_username);
                int userId = Integer.parseInt(id.getText().toString());

                EditText pswd = (EditText) findViewById(R.id.login_password);
                String password = pswd.getText().toString();
                boolean validId = false;
                boolean validPassword = false;
                List<Integer> custList = mydb.getUsersByRoleHelper(3);
                for(int i  = 0; i < custList.size(); i++){
                    if(custList.get(i) == userId){
                        validId = true;
                        String hashedPassword = mydb.getPassword(userId);
                        validPassword = PasswordHelpers.comparePassword(hashedPassword, password);
                    }
                }

                if(validId && validPassword) {
                    Intent startIntent = new Intent(getApplicationContext(), CustomerActivity.class);
                    startActivity(startIntent);
                }
                else{
                    TextView error = new TextView(getApplicationContext());
                    String errorText = "You have entered and incorrect ID or password. Please try again";
                    error.setText(errorText);
                    layout.addView(error);
                }

            }
        });
    }
}
