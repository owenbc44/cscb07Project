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

public class EmployeeLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        final DatabaseDriverAndroidHelper mydb = new DatabaseDriverAndroidHelper(this);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.employee_login_container);

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
                List<Integer> emplyList = mydb.getUsersByRoleHelper(2);
                for(int i  = 0; i < emplyList.size(); i++){
                    if(emplyList.get(i) == userId){
                        validId = true;
                        String hashedPassword = mydb.getPassword(userId);
                        validPassword = PasswordHelpers.comparePassword(hashedPassword, password);
                    }
                }

                if(validId && validPassword) {
                    Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
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
