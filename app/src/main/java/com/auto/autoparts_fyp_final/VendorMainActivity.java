package com.auto.autoparts_fyp_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VendorMainActivity extends AppCompatActivity {
    private Button applyChangesBtnEdit;

    private FirebaseUser currentUser;
    private DatabaseReference RootRef;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference VendorsRef;

    private Button addProduct,deleteProduct,viewProduct,viewOrders,updateProduct,logout,updateProfile;
    private TextView ShopNameShow,VendorNameShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_main);

        mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getCurrentUser().getUid();

        VendorsRef = FirebaseDatabase.getInstance().getReference().child("Vendors").child(currentUserId);

        ShopNameShow = (TextView) findViewById(R.id.shop_name_show);
        VendorNameShow = (TextView) findViewById(R.id.vendor_name_show);

        addProduct = (Button) findViewById(R.id.add_product);

        deleteProduct = (Button) findViewById(R.id.delete_product);

        viewProduct = (Button) findViewById(R.id.view_product);
        viewOrders = (Button) findViewById(R.id.view_orders);
        updateProduct = (Button) findViewById(R.id.update_product);

        updateProfile  = (Button) findViewById(R.id.update_vendor_profile);

        logout = (Button) findViewById(R.id.vendor_logout_btn);

        displayVendorInfo();


        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(VendorMainActivity.this, AddNewProductActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
            }
        });

        viewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent(VendorMainActivity.this, ViewProductActivity.class);
                viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(viewIntent);

            }
        });

        viewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent(VendorMainActivity.this, ViewOrderActivity.class);
                viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(viewIntent);

            }
        });

        updateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent(VendorMainActivity.this, EditProductActivity.class);
                viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(viewIntent);

            }
        });

        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent(VendorMainActivity.this, DeleteProductActivity.class);
                viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(viewIntent);

            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent(VendorMainActivity.this, UpdateVendorProfileActivity.class);
                viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(viewIntent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent LogoutIntent = new Intent(VendorMainActivity.this, MainActivity.class);
                LogoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(LogoutIntent);
                Toast.makeText(VendorMainActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();

            }
        });





    }//on create bracket


    private void displayVendorInfo()
    {
        VendorsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String ShopName = dataSnapshot.child("vsname").getValue().toString();
                    String VendorName = dataSnapshot.child("vname").getValue().toString();

                    ShopNameShow.setText(ShopName);
                    VendorNameShow.setText(VendorName);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
