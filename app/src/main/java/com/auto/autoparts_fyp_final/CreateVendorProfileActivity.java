package com.auto.autoparts_fyp_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateVendorProfileActivity extends AppCompatActivity {
    private Button SaveButton;
    private EditText shopAddress, longitude,latitude;
    private DatabaseReference VendorRef;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vendor_profile);


        VendorRef = FirebaseDatabase.getInstance().getReference().child("Vendors");
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        SaveButton = (Button) findViewById(R.id.save_btn_create);
        latitude = (EditText) findViewById(R.id.latitude);
        longitude = (EditText) findViewById(R.id.longitude);
        shopAddress = (EditText) findViewById(R.id.create_profile_shop_address);

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });


    }

    private void Save()
    {
        String Shop_Address = shopAddress.getText().toString();
        String Latitude = latitude.getText().toString();
        String Longitude = longitude.getText().toString();

        if (TextUtils.isEmpty(Shop_Address))
        {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Latitude))
        {
            Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Longitude))
        {
            Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
        }


        String currentVendorId = mAuth.getCurrentUser().getUid();
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("shopAddress", Shop_Address);
        productMap.put("latitude", Latitude);
        productMap.put("longitude", Longitude);

        VendorRef.child(currentVendorId).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(CreateVendorProfileActivity.this, VendorMainActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(CreateVendorProfileActivity.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(CreateVendorProfileActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
