package com.auto.autoparts_fyp_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.auto.autoparts_fyp_final.ViewHolder.ProductViewHolder;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsCustomerActivity extends AppCompatActivity
{
    private TextView productName,productDescription,productPrice,productQuantity,make,model,category;
    private ImageView productImage;
    private String productID="";
    private String vid="";
    ProductViewHolder holder;


    private Button addToCartButton;
    private ElegantNumberButton numberButton;
    private String state = "Normal";

    private FirebaseAuth mAuth;
    private String currentCustomerId;

    public static CustomerData currentOnlineUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_customer);



        productImage=(ImageView) findViewById(R.id.product_image_details_customer);
        productID=getIntent().getStringExtra("pid");
        productName = (TextView) findViewById(R.id.product_name_details_customer);
        productDescription = (TextView) findViewById(R.id.product_description_details_customer);
        productPrice = (TextView) findViewById(R.id.product_price_details_customer);
        productQuantity = (TextView) findViewById(R.id.product_quantity_details_customer);
        make = (TextView) findViewById(R.id.product_make_details_customer);
        model = (TextView) findViewById(R.id.product_model_details_customer);
        category = (TextView) findViewById(R.id.product_category_details_customer);
        addToCartButton = (Button) findViewById(R.id.add_to_cart_btn);
        numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);
        mAuth=FirebaseAuth.getInstance();

        getProductDetails(productID);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null )
                {
                    addingToCartList();

                }
                else
                {
                    Toast.makeText(ProductDetailsCustomerActivity.this, "Please Login First To Add Items to cart", Toast.LENGTH_SHORT).show();
                }



            }
        });




    }//on create
    private void getProductDetails(String productID) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    Products products = dataSnapshot.getValue(Products.class);

                    Picasso.get().load(products.getImage()).into(productImage);
                    productName.setText(products.getPname());
                    productDescription.setText(products.getDescription());
                    productPrice.setText(products.getPrice());
                    productQuantity.setText(products.getQuantity());
                    make.setText(products.getMake());
                    model.setText(products.getModel());
                    category.setText(products.getCategory());




                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
  }

    private void addingToCartList()
    {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            currentCustomerId = mAuth.getCurrentUser().getUid();
            String saveCurrentTime, saveCurrentDate;

            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentDate.format(calForDate.getTime());



            final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

            final HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("pid", productID);
            cartMap.put("pname", productName.getText().toString());
            cartMap.put("price", productPrice.getText().toString());
            cartMap.put("date", saveCurrentDate);
            cartMap.put("time", saveCurrentTime);
            cartMap.put("quantity", numberButton.getNumber());

            cartListRef.child("Customer View").child(currentCustomerId)
                    .child("Products").child(productID)
                    .updateChildren(cartMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                cartListRef.child("Vendor View").child(currentCustomerId)
                                        .child("Products").child(productID)
                                        .updateChildren(cartMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(ProductDetailsCustomerActivity.this, "Added to Cart List.", Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(ProductDetailsCustomerActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                }
                                                else
                                                {
                                                    String message = task.getException().toString();
                                                    Toast.makeText(ProductDetailsCustomerActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });
                            }
                        }
                    });

        }
        else
        {
            Toast.makeText(ProductDetailsCustomerActivity.this, "Login First", Toast.LENGTH_SHORT).show();

        }

    }




}