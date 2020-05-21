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

public class CreateEmployeeActivity extends Activity {
    public static int employeeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("tag", "beepboop4");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee);
        Log.d("tag", "beepboop3");
        final DatabaseDriverAndroidHelper mydb = new DatabaseDriverAndroidHelper(this);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.create_employee_container);
        Log.d("tag", "beepboop2");
        Button createEmployee = (Button) findViewById(R.id.create_employee_button);
        createEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag", "beepboop0");
                EditText EmployeeName = (EditText) findViewById(R.id.euser_name);
                EditText EmployeeAge = (EditText) findViewById(R.id.euser_age);
                EditText EmployeeAddress = (EditText) findViewById(R.id.euser_address);
                EditText EmployeePassword = (EditText) findViewById(R.id.euser_password);

                Log.d("tag", "beepboop1");
                String employeeName = EmployeeName.getText().toString();
                int employeeAge = Integer.parseInt(EmployeeAge.getText().toString());
                String employeeAddress = EmployeeAddress.getText().toString();
                String employeePassword = EmployeePassword.getText().toString();

                boolean errorWhileInitialisingEmployee = true;

                while (errorWhileInitialisingEmployee) {

                    try {
                        employeeId = (int) mydb.insertNewUser(employeeName,
                                Integer.valueOf(employeeAge), employeeAddress, employeePassword);
                        Log.d("tag", "beepboop5");
                        int employeeRoleId = 2;

                        mydb.insertUserRole(employeeId, employeeRoleId);
                        Log.d("tag", "beepboop6");
                        errorWhileInitialisingEmployee = false;

                    } catch (NumberFormatException e) {
                        Log.d("tag", "beepboop8");
                    }
                }

                Log.d("tag", "beepboop7");
                Intent startIntent = new Intent(getApplicationContext(), Popup3Activity.class);
                startActivity(startIntent);

            }
        });
    }
}
