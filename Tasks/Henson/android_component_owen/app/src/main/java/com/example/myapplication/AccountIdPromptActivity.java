package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class AccountIdPromptActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_id_prompt);
        final DatabaseDriverAndroidHelper mydb = new DatabaseDriverAndroidHelper(this);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.container);

        Button promote = (Button) findViewById(R.id.promote_button);
        promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText EmplyId = (EditText) findViewById(R.id.username);
                int employeeId = Integer.parseInt(EmplyId.getText().toString());
                List<Integer> emplys = mydb.getUsersByRoleHelper(2);
                boolean success = false;
                if(emplys.size() > 1){
                    if(emplys.contains(employeeId)){
                        success = mydb.updateUserRole(1, employeeId);
                        if(success){
                            TextView successMessage = new TextView(getApplicationContext());
                            String messageText = "Employee successfully promoted to admin!";
                            successMessage.setText(messageText);
                            layout.addView(successMessage);
                        }
                    }

                }
                else{
                    TextView error = new TextView(getApplicationContext());
                    String errorMessage = "You don't have enough employees to do that";
                    error.setText(errorMessage);
                    layout.addView(error);
                }
            }
        });

    }
}
