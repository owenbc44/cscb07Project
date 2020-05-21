package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.math.BigDecimal;

public class InitializationActivity extends Activity {
    private DatabaseDriverAndroidHelper mydb = new DatabaseDriverAndroidHelper(this);
    public static int adminId;
    public static int emplyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialization);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.init_layout);
        Button initDatabase = (Button) findViewById(R.id.init_database);
        initDatabase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText AdminName;
                    EditText AdminAge;
                    EditText AdminAddress;
                    EditText AdminPassword;

                    EditText EmployeeName;
                    EditText EmployeeAge;
                    EditText EmployeeAddress;
                    EditText EmployeePassword;
                    AdminName = (EditText) findViewById(R.id.init_aname);
                    AdminAge = (EditText) findViewById(R.id.init_aage);
                    AdminAddress = (EditText) findViewById(R.id.init_aaddress);
                    AdminPassword = (EditText) findViewById(R.id.init_apassword);

                    String adminName = AdminName.getText().toString();
                    int adminAge = Integer.parseInt(AdminAge.getText().toString());
                    String adminAddress = AdminAddress.getText().toString();
                    String adminPassword = AdminPassword.getText().toString();


                    EmployeeName = (EditText) findViewById(R.id.init_ename);
                    EmployeeAge = (EditText) findViewById(R.id.init_eage);
                    EmployeeAddress = (EditText) findViewById(R.id.init_eaddress);
                    EmployeePassword = (EditText) findViewById(R.id.init_epassword);

                    String employeeName = EmployeeName.getText().toString();
                    int employeeAge = Integer.parseInt(EmployeeAge.getText().toString());
                    String employeeAddress = EmployeeAddress.getText().toString();
                    String employeePassword = EmployeePassword.getText().toString();


                    try {
                        // has item id 1
                        mydb.insertItem("FISHING_ROD", new BigDecimal("10.00"));
                        // has item id 2
                        mydb.insertItem("HOCKEY_STICK", new BigDecimal("10.00"));
                        // has item id 3
                        mydb.insertItem("SKATES", new BigDecimal("10.00"));
                        // has item id 4
                        mydb.insertItem("RUNNING_SHOES", new BigDecimal("10.00"));
                        // has item id 5
                        mydb.insertItem("PROTEIN_BAR", new BigDecimal("10.00"));

                        // initialise inventory with 100 of each item
                        mydb.insertInventory(1, 100);
                        mydb.insertInventory(2, 100);
                        mydb.insertInventory(3, 100);
                        mydb.insertInventory(4, 100);
                        mydb.insertInventory(5, 100);

                        mydb.insertRole("ADMIN"); // has role id 1
                        mydb.insertRole("EMPLOYEE"); // has role id 2
                        mydb.insertRole("CUSTOMER"); // has role id 3

                    } catch (Exception e) {
                        System.out.println("Warning: An unexpected Exception occurred. Please try again");
                    }

                    boolean errorWhileInitialisingAdmin = true;

                    while (errorWhileInitialisingAdmin) {

                        try {
                            adminId = (int) mydb.insertNewUser(adminName,
                                    Integer.valueOf(adminAge), adminAddress, adminPassword);

                            int adminRoleId = 1;

                            mydb.insertUserRole(adminId, adminRoleId);

                            errorWhileInitialisingAdmin = false;

                        } catch (NumberFormatException e) {
                            System.out.println("Warning: Number Format Exception occured. Please try again");
                        }

                        boolean errorWhileInitialisingEmployee = true;

                        while (errorWhileInitialisingEmployee) {

                            try {
                                emplyId = (int) mydb.insertNewUser(employeeName,
                                        employeeAge, employeeAddress, employeePassword);

                                int emplyRoleId = 2;

                                mydb.insertUserRole(emplyId, emplyRoleId);

                                errorWhileInitialisingEmployee = false;

                            } catch (NumberFormatException e) {
                                System.out.println("Warning: Number Format Exception occurred. Please try again");
                            }

                        }

                    }
                    Intent popup = new Intent(getApplicationContext(), PopupActivity.class);
                    startActivity(popup);
                }
            });
        }
    }


