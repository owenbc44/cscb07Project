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

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final DatabaseDriverAndroidHelper mydb = new DatabaseDriverAndroidHelper(this);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.container);



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
                List<Integer> adminList = mydb.getUsersByRoleHelper(1);
                for(int i  = 0; i < adminList.size(); i++){
                    if(adminList.get(i) == userId){
                        validId = true;
                        String hashedPassword = mydb.getPassword(userId);
                        validPassword = PasswordHelpers.comparePassword(hashedPassword, password);
                    }
                }

                if(validId && validPassword) {
                    Intent startIntent = new Intent(getApplicationContext(), AdminActivity.class);
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
