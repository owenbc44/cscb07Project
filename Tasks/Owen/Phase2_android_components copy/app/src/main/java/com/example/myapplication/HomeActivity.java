package com.example.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeActivity extends Activity {
    private DatabaseDriverAndroidHelper mydb = new DatabaseDriverAndroidHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.home_layout);

            Button setupNewDB = (Button) findViewById(R.id.home_new_db);
            setupNewDB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mydb.getUserRole(1) == 0) {
                        Intent startIntent = new Intent(getApplicationContext(), InitializationActivity.class);
                        startActivity(startIntent);
                    }

                }
            });

        Button adminLogin = (Button) findViewById(R.id.home_alogin);
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mydb.getUserRole(1) == 1) {
                Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(startIntent);
                }
                else {
                    TextView error = new TextView(getApplicationContext());
                    String errorText = "Please initialize the databse!";
                    error.setText(errorText);
                    layout.addView(error);
                }
            }
        });

        Button moreOptions = (Button) findViewById(R.id.home_more_opt);
        moreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mydb.getUserRole(1) == 1) {
                    Intent startIntent = new Intent(getApplicationContext(), MoreOptionsActivity.class);
                    startActivity(startIntent);
                }
                else {
                    TextView error = new TextView(getApplicationContext());
                    String errorText = "Please initialize the databse!";
                    error.setText(errorText);
                    layout.addView(error);
                }
            }
        });

    }
    public DatabaseDriverAndroidHelper getMydb() {
        return this.mydb;
    }
    public void setMydb() {
        this.mydb = new DatabaseDriverAndroidHelper(this);
    }
}
