package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InitializationActivity extends Activity {
    private EditText AdminName;
    private EditText AdminAge;
    private EditText AdminAddress;
    private EditText AdminPassword;
    private Button CreateAdmin;

    private EditText EmployeeName;
    private EditText EmployeeAge;
    private EditText EmployeeAddress;
    private EditText EmployeePassword;
    private Button CreateEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialization);
        AdminName = (EditText) findViewById(R.id.init_aname);
        AdminAge = (EditText) findViewById(R.id.init_aage);
        AdminAddress = (EditText) findViewById(R.id.init_aaddress);
        AdminPassword = (EditText) findViewById(R.id.init_apassword);
//        CreateAdmin = (Button) findViewById(R.id.init_acreate);
//        CreateAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        EmployeeName = (EditText) findViewById(R.id.init_ename);
        EmployeeAge = (EditText) findViewById(R.id.init_eage);
        EmployeeAddress = (EditText) findViewById(R.id.init_eaddress);
        EmployeePassword = (EditText) findViewById(R.id.init_epassword);
        CreateEmployee = (Button) findViewById(R.id.init_ecreate);


    }
}
