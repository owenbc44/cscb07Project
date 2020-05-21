package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class NewAccountActivity extends Activity {
    public static long accId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        final DatabaseDriverAndroidHelper mydb = new DatabaseDriverAndroidHelper(this);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.new_account_container);

        Button makeAccount = (Button) findViewById(R.id.new_acc_button);
        makeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText customerId = (EditText) findViewById(R.id.new_acc_customer_id);
                int custId = Integer.parseInt(customerId.getText().toString());

                List<Integer> custs = mydb.getUsersByRoleHelper(3);
                boolean success = false;

                if(custs.contains(custId)){
                    accId = mydb.insertAccount(custId, true);
                    success = true;
                    Log.d("tag", "beepboop");
                }
                if(success){
                    Intent startIntent = new Intent(getApplicationContext(), Popup4Activity.class);
                    startActivity(startIntent);
                }
                else{
                    Log.d("tag", "beepboop2");
                    TextView error = new TextView(getApplicationContext());
                    String errorMessage = "Please enter a valid customer ID";
                    error.setText(errorMessage);
                    layout.addView(error);
                }
            }
        });

    }
}
