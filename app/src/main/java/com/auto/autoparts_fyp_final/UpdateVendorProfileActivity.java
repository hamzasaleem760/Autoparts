package com.auto.autoparts_fyp_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class UpdateVendorProfileActivity extends AppCompatActivity {
    private EditText ShopNameEditText,VendorNameEditText, PhoneEditText, addressEditText,PasswordEditText;

    private Button applyChangesBtnEdit;

    private FirebaseUser currentUser;
    private DatabaseReference RootRef;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference VendorsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vendor_profile);

        mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getCurrentUser().getUid();

        VendorsRef = FirebaseDatabase.getInstance().getReference().child("Vendors").child(currentUserId);

        ShopNameEditText = (EditText) findViewById(R.id.shop_name_edit);
        VendorNameEditText = (EditText) findViewById(R.id.vendor_name_edit);
        PhoneEditText = (EditText) findViewById(R.id.vendor_phone_edit);
        addressEditText = (EditText) findViewById(R.id.shop_address_edit);
      //  PasswordEditText = (EditText) findViewById(R.id.vendor_password_edit);
        applyChangesBtnEdit = findViewById(R.id.apply_changes_btn_edit);



        displayVendorInfo();
        applyChangesBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                applyChanges();
            }
        });


    } //on create bracket

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(UpdateVendorProfileActivity.this, VendorMainActivity.class);
        startActivity(intent);

    }

    private void applyChanges()
    {
        String ShopName = ShopNameEditText.getText().toString();
        String ShopAddress = addressEditText.getText().toString();
        String VendorName = VendorNameEditText.getText().toString();
        String VendorPhone = PhoneEditText.getText().toString();
       // String VendorPassword = PasswordEditText.getText().toString();

        if (ShopName.equals(""))
        {
            Toast.makeText(this, "Write Shop Name", Toast.LENGTH_SHORT).show();
        }
        else if (ShopAddress.equals(""))
        {
            Toast.makeText(this, "Write Shop Address.", Toast.LENGTH_SHORT).show();
        }
        else if (VendorName.equals(""))
        {
            Toast.makeText(this, "Write Vendor Name.", Toast.LENGTH_SHORT).show();
        }
        else if (VendorPhone.equals(""))
        {
            Toast.makeText(this, "Write Phone Number.", Toast.LENGTH_SHORT).show();
        }
//        else if (VendorPassword.equals(""))
//        {
//            Toast.makeText(this, "Write Password.", Toast.LENGTH_SHORT).show();
//        }
        else
        {
            HashMap<String, Object> VendorMap = new HashMap<>();
            VendorMap.put("vsname", ShopName);
            VendorMap.put("vsaddress", ShopAddress);
            VendorMap.put("vname", VendorName);
            VendorMap.put("vphone", VendorPhone);
           // VendorMap.put("password",VendorPassword );



            VendorsRef.updateChildren(VendorMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(UpdateVendorProfileActivity.this, "Changes applied successfully.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(UpdateVendorProfileActivity.this, VendorMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }





    private void displayVendorInfo()
    {
        VendorsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String ShopName = dataSnapshot.child("vsname").getValue().toString();
                    String ShopAddress = dataSnapshot.child("vsaddress").getValue().toString();
                    String VendorName = dataSnapshot.child("vname").getValue().toString();
                    String VendorPhone = dataSnapshot.child("vphone").getValue().toString();
                   // String VendorPassword = dataSnapshot.child("password").getValue().toString();

                    ShopNameEditText.setText(ShopName);
                    addressEditText.setText(ShopAddress);
                    VendorNameEditText.setText(VendorName);
                    PhoneEditText.setText(VendorPhone);
                    //PasswordEditText.setText(VendorPassword);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}