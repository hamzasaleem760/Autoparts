package com.auto.autoparts_fyp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectAccountTypeActivity extends AppCompatActivity {
    private Button CustomerBtn;
    private Button VendorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account_type);

        CustomerBtn = (Button) findViewById(R.id.customer_btn);
        VendorBtn = (Button) findViewById(R.id.vendor_btn);

        CustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(SelectAccountTypeActivity.this, CustomerLoginActivity.class);
                startActivity(intent);
            }
        });

        VendorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(SelectAccountTypeActivity.this, VendorLoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
