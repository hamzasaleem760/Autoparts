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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DeleteProductDetailsActivity extends AppCompatActivity {
    private TextView productName,productDescription,productPrice,productQuantity,make,model,category;
    private ImageView productImage;
    private String productID="";
    private Button deleteBtn;

    private DatabaseReference ProductsRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product_details);




        productImage=(ImageView) findViewById(R.id.product_image_details_delete);
        productName = (TextView) findViewById(R.id.product_name_details_delete);
        productDescription = (TextView) findViewById(R.id.product_description_details_delete);
        productPrice = (TextView) findViewById(R.id.product_price_details_delete);
        productQuantity = (TextView) findViewById(R.id.product_quantity_details_delete);
        make = (TextView) findViewById(R.id.product_make_details_delete);
        model = (TextView) findViewById(R.id.product_model_details_delete);
        category = (TextView) findViewById(R.id.product_category_details_delete);
        deleteBtn = findViewById(R.id.delete_product_btn);

        productID = getIntent().getStringExtra("pid");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);

        getProductDetails(productID);

       deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                deleteThisProduct();
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

    private void deleteThisProduct()
    {
        ProductsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Intent intent = new Intent(DeleteProductDetailsActivity.this, VendorMainActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(DeleteProductDetailsActivity.this, "The Product Is deleted successfully.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
