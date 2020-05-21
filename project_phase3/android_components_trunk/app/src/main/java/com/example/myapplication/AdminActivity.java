package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class AdminActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        final DatabaseDriverAndroidHelper mydb = new DatabaseDriverAndroidHelper(this);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.admin_container);

        Button exit = (Button) findViewById(R.id.admin_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(startIntent);
            }
        });

        Button promoteEmployee = (Button) findViewById(R.id.admin_promote);
        promoteEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> employeeIds = mydb.getUsersByRoleHelper(2);
                if(employeeIds.size() > 1){
                    Intent startIntent = new Intent(getApplicationContext(), AccountIdPromptActivity.class);
                    startActivity(startIntent);
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
