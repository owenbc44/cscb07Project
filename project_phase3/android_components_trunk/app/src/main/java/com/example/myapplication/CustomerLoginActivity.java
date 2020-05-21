package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

        Button signIn = (Button) findViewById(R.id.customer_sign_in_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText id = (EditText) findViewById(R.id.customer_login_username);
                int userId = Integer.parseInt(id.getText().toString());
                Log.d("tag1", "beepboop");

                EditText pswd = (EditText) findViewById(R.id.customer_login_password);
                String password = pswd.getText().toString();
                Log.d("tag2", "beepboop2");
                boolean validId = false;
                boolean validPassword = false;
                List<Integer> custList = mydb.getUsersByRoleHelper(3);
                Log.d("tag3", "beepboop3");

                if(custList.contains(userId)) {
                    Log.d("tag4", "beepboop4");
                    validId = true;
                    String hashedPassword = mydb.getPassword(userId);
                    Log.d("tag5", "beepboop5");
                    validPassword = PasswordHelpers.comparePassword(hashedPassword, password);
                    Log.d("tag6", "beepboop6");
                }

                if(validId && validPassword) {
                    Log.d("tag7", "beepboop7");
                    Intent startIntent = new Intent(getApplicationContext(), CustomerActivity.class);
                    startActivity(startIntent);
                    Log.d("tag8", "beepboop8");
                }
                else{
                    Log.d("tag9", "beepboop9");
                    TextView error = new TextView(getApplicationContext());
                    String errorText = "You have entered and incorrect ID or password. Please try again";
                    error.setText(errorText);
                    layout.addView(error);
                }

            }
        });
    }
}
