package com.auto.autoparts_fyp_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.auto.autoparts_fyp_final.ViewHolder.ProductViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {
    private TextView productName,productDescription,productPrice,productQuantity,make,model,category;
    private ImageView productImage;
    private String productID="";
    ProductViewHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productImage=(ImageView) findViewById(R.id.product_image_details);
        productID=getIntent().getStringExtra("pid");
        productName = (TextView) findViewById(R.id.product_name_details);
        productDescription = (TextView) findViewById(R.id.product_description_details);
        productPrice = (TextView) findViewById(R.id.product_price_details);
        productQuantity = (TextView) findViewById(R.id.product_quantity_details);
        make = (TextView) findViewById(R.id.product_make_details);
        model = (TextView) findViewById(R.id.product_model_details);
        category = (TextView) findViewById(R.id.product_category_details);


        getProductDetails(productID);



    }// on create bracket

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
}
