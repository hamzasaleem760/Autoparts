package com.auto.autoparts_fyp_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class EditProductDetailsActivity extends AppCompatActivity {
    private Button applyChangesBtn;
    private EditText name, price, description,quantity;
    private ImageView imageView;
    private String productID = "";
    private DatabaseReference productsRef;
    private Spinner makeSpinnerMaintain,modelSpinnerMaintain,categorySpinnerMaintain;


    private String carMakeMaintain,carModelMaintain,productCategoryMaintain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product_details);

        productID = getIntent().getStringExtra("pid");
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);



        applyChangesBtn = findViewById(R.id.apply_changes_btn);
        name = findViewById(R.id.product_name_maintain);
        price = findViewById(R.id.product_price_maintain);
        description = findViewById(R.id.product_description_maintain);
        quantity = findViewById(R.id.product_quantity_maintain);
        imageView = findViewById(R.id.product_image_maintain);

        final String make[]={"Toyota","Suzuki","Honda","Daihatsu","Nissan","Mitsubishi","Hyundai","Mazda"};

        final String toyota[]={"Corolla","Vitz","Passo","Aqua","Hilux","Surf","Prado","Camry"};

        final String suzuki[]={"Mehran","Alto","Cultus","Aqua","WagonR","Bolan","Swift","Ravi"};

        final String honda[]={"Civic","City","Accord","Aqua","Vezel","BRV","HRV","Fit"};

        final String daihatsu[]={"Mira","Move","Hijet","Cast","Copen","Tanto","Charade","Cuore"};

        final String nissan[]={"Moco","Dayz","Juke","Blue Bird","Sunny","March","Pino","Tiida"};

        final String mitsubishi[]={"EKwagon","Lancer","Galant","Mini Pajero","I","Minica"};

        final String hyundai[]={"SantaFe","Shehzore","Ioniq"};

        final String mazda[]={"Carol","Scrum","Flair","RX8"};

        final String category[]={"Engine/Mechanical","Car Exterior","Electrical/Lighting","Brakes","Car Care","Car Interior","Audio/Video","Tyres/Rims","Heating/Cooling"};

        makeSpinnerMaintain=findViewById(R.id.make_spinner_maintain);//make Spinner
        modelSpinnerMaintain=findViewById(R.id.model_spinner_maintain);//model Spinner
        categorySpinnerMaintain=findViewById(R.id.category_spinner_maintain);//Category Spinner






        // make
        ArrayAdapter<String> makeAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,make);
        makeSpinnerMaintain.setAdapter(makeAdapter);

        //

        //category
        ArrayAdapter<String> categoryAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,category);
        categorySpinnerMaintain.setAdapter(categoryAdapter);

        makeSpinnerMaintain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelect=make[position];
                if(position==0)
                {
                    ArrayAdapter<String>toyotaAdapter=new ArrayAdapter<String>(EditProductDetailsActivity.this,android.R.layout.simple_spinner_dropdown_item,toyota);
                    modelSpinnerMaintain.setAdapter(toyotaAdapter);
                }
                if(position==1)
                {
                    ArrayAdapter<String>suzukiAdapter=new ArrayAdapter<String>(EditProductDetailsActivity.this,android.R.layout.simple_spinner_dropdown_item,suzuki);
                    modelSpinnerMaintain.setAdapter(suzukiAdapter);
                }
                if(position==2)
                {
                    ArrayAdapter<String>hondaAdapter=new ArrayAdapter<String>(EditProductDetailsActivity.this,android.R.layout.simple_spinner_dropdown_item,honda);
                    modelSpinnerMaintain.setAdapter(hondaAdapter);
                }
                if(position==3)
                {
                    ArrayAdapter<String>daihatsuAdapter=new ArrayAdapter<String>(EditProductDetailsActivity.this,android.R.layout.simple_spinner_dropdown_item,daihatsu);
                    modelSpinnerMaintain.setAdapter(daihatsuAdapter);
                }
                if(position==4)
                {
                    ArrayAdapter<String>nissanAdapter=new ArrayAdapter<String>(EditProductDetailsActivity.this,android.R.layout.simple_spinner_dropdown_item,nissan);
                    modelSpinnerMaintain.setAdapter(nissanAdapter);
                }
                if(position==5)
                {
                    ArrayAdapter<String>mitsubishiAdapter=new ArrayAdapter<String>(EditProductDetailsActivity.this,android.R.layout.simple_spinner_dropdown_item,mitsubishi);
                    modelSpinnerMaintain.setAdapter(mitsubishiAdapter);
                }
                if(position==6)
                {
                    ArrayAdapter<String>hyundaiAdapter=new ArrayAdapter<String>(EditProductDetailsActivity.this,android.R.layout.simple_spinner_dropdown_item,hyundai);
                    modelSpinnerMaintain.setAdapter(hyundaiAdapter);
                }
                if(position==7)
                {
                    ArrayAdapter<String>mazdaAdapter=new ArrayAdapter<String>(EditProductDetailsActivity.this,android.R.layout.simple_spinner_dropdown_item,mazda);
                    modelSpinnerMaintain.setAdapter(mazdaAdapter);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        displaySpecificProductInfo();
        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                applyChanges();
            }
        });



    }//on create bracket


    private void applyChanges()
    {
        String pName = name.getText().toString();
        String pPrice = price.getText().toString();
        String pDescription = description.getText().toString();
        String pQuantity = quantity.getText().toString();
        carMakeMaintain= makeSpinnerMaintain.getSelectedItem().toString();
        carModelMaintain= modelSpinnerMaintain.getSelectedItem().toString();
        productCategoryMaintain= categorySpinnerMaintain.getSelectedItem().toString();

        if (pName.equals(""))
        {
            Toast.makeText(this, "Write down Product Name.", Toast.LENGTH_SHORT).show();
        }
        else if (pPrice.equals(""))
        {
            Toast.makeText(this, "Write down Product Price.", Toast.LENGTH_SHORT).show();
        }
        else if (pDescription.equals(""))
        {
            Toast.makeText(this, "Write down Product Description.", Toast.LENGTH_SHORT).show();
        }
        else if (pQuantity.equals(""))
        {
            Toast.makeText(this, "Write down Quantity Description.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pid", productID);
            productMap.put("description", pDescription);
            productMap.put("price", pPrice);
            productMap.put("pname", pName);
            productMap.put("quantity", pQuantity);
            productMap.put("make",carMakeMaintain );
            productMap.put("model", carModelMaintain);
            productMap.put("category", productCategoryMaintain);


            productsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(EditProductDetailsActivity.this, "Changes applied successfully.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(EditProductDetailsActivity.this, VendorMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }





    private void displaySpecificProductInfo()
    {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String pName = dataSnapshot.child("pname").getValue().toString();
                    String pPrice = dataSnapshot.child("price").getValue().toString();
                    String pDescription = dataSnapshot.child("description").getValue().toString();
                    String pQuantity = dataSnapshot.child("quantity").getValue().toString();
                    String pImage = dataSnapshot.child("image").getValue().toString();


                    name.setText(pName);
                    price.setText(pPrice);
                    description.setText(pDescription);
                    quantity.setText(pQuantity);
                    Picasso.get().load(pImage).into(imageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}

