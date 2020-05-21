package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PopupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        TextView admin_id = (TextView) findViewById(R.id.admin_id_tv);
        String adminText = "Admin ID: " + Integer.toString(InitializationActivity.adminId);
        admin_id.setText(adminText);

        TextView emply_id = (TextView) findViewById(R.id.employee_id_tv);
        String emplyText = "Employee ID: " + Integer.toString(InitializationActivity.emplyId);
        emply_id.setText(emplyText);

        Button exit = (Button) findViewById(R.id.popup_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(startIntent);
            }
        });

    }
}
