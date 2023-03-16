package com.auto.autoparts_fyp_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddNewProductActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText productName, productDescription,productPrice,productQuantity;
    private Button AddNewProductButton;
    private ImageView SelectProductImage;
    private ProgressDialog loadingBar;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String  Product_Description, Product_Price, Product_Name,Product_Quantity,saveCurrentDate, saveCurrentTime,CategoryName;
    private String productRandomKey, downloadImageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private String carMake,carModel,productCategory;

    private Spinner makeSpinner,modelSpinner,categorySpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        mAuth = FirebaseAuth.getInstance();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product_Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");


        final String make[]={"Toyota","Suzuki","Honda","Daihatsu","Nissan","Mitsubishi","Hyundai","Mazda","All"};

        final String toyota[]={"Corolla","Vitz","Passo","Aqua","Hilux","Surf","Prado","Camry"};

        final String suzuki[]={"Mehran","Alto","Cultus","Aqua","WagonR","Bolan","Swift","Ravi"};

        final String honda[]={"Civic","City","Accord","Aqua","Vezel","BRV","HRV","Fit"};

        final String daihatsu[]={"Mira","Move","Hijet","Cast","Copen","Tanto","Charade","Cuore"};

        final String nissan[]={"Moco","Dayz","Juke","Blue Bird","Sunny","March","Pino","Tiida"};

        final String mitsubishi[]={"EKwagon","Lancer","Galant","Mini Pajero","I","Minica"};

        final String hyundai[]={"SantaFe","Shehzore","Ioniq"};

        final String mazda[]={"Carol","Scrum","Flair","RX8"};

        final String all[]={"All"};

        final String category[]={"Engine/Mechanical","Car Exterior","Electrical/Lighting","Brakes","Car Care","Car Interior","Audio/Video","Tyres/Rims","Heating/Cooling"};

        makeSpinner=findViewById(R.id.make_spinner);//make Spinner
        modelSpinner=findViewById(R.id.model_spinner);//model Spinner
        categorySpinner=findViewById(R.id.category_spinner);//Category Spinner






        // make
        ArrayAdapter<String> makeAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,make);
        makeSpinner.setAdapter(makeAdapter);

        //

        //category
        ArrayAdapter<String> categoryAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,category);
        categorySpinner.setAdapter(categoryAdapter);


        SelectProductImage = (ImageView) findViewById(R.id.select_product_image);
        productName = (EditText) findViewById(R.id.product_name);
        productDescription = (EditText) findViewById(R.id.product_description);
        productPrice = (EditText) findViewById(R.id.product_price);
        productQuantity = (EditText) findViewById(R.id.product_quantity);




        AddNewProductButton = (Button) findViewById(R.id.add_new_product_button);

        loadingBar = new ProgressDialog(this);

        SelectProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });





        makeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelect=make[position];
                if(position==0)
                {
                    ArrayAdapter<String>toyotaAdapter=new ArrayAdapter<String>(AddNewProductActivity.this,android.R.layout.simple_spinner_dropdown_item,toyota);
                    modelSpinner.setAdapter(toyotaAdapter);
                }
                if(position==1)
                {
                    ArrayAdapter<String>suzukiAdapter=new ArrayAdapter<String>(AddNewProductActivity.this,android.R.layout.simple_spinner_dropdown_item,suzuki);
                    modelSpinner.setAdapter(suzukiAdapter);
                }
                if(position==2)
                {
                    ArrayAdapter<String>hondaAdapter=new ArrayAdapter<String>(AddNewProductActivity.this,android.R.layout.simple_spinner_dropdown_item,honda);
                    modelSpinner.setAdapter(hondaAdapter);
                }
                if(position==3)
                {
                    ArrayAdapter<String>daihatsuAdapter=new ArrayAdapter<String>(AddNewProductActivity.this,android.R.layout.simple_spinner_dropdown_item,daihatsu);
                    modelSpinner.setAdapter(daihatsuAdapter);
                }
                if(position==4)
                {
                    ArrayAdapter<String>nissanAdapter=new ArrayAdapter<String>(AddNewProductActivity.this,android.R.layout.simple_spinner_dropdown_item,nissan);
                    modelSpinner.setAdapter(nissanAdapter);
                }
                if(position==5)
                {
                    ArrayAdapter<String>mitsubishiAdapter=new ArrayAdapter<String>(AddNewProductActivity.this,android.R.layout.simple_spinner_dropdown_item,mitsubishi);
                    modelSpinner.setAdapter(mitsubishiAdapter);
                }
                if(position==6)
                {
                    ArrayAdapter<String>hyundaiAdapter=new ArrayAdapter<String>(AddNewProductActivity.this,android.R.layout.simple_spinner_dropdown_item,hyundai);
                    modelSpinner.setAdapter(hyundaiAdapter);
                }
                if(position==7)
                {
                    ArrayAdapter<String>mazdaAdapter=new ArrayAdapter<String>(AddNewProductActivity.this,android.R.layout.simple_spinner_dropdown_item,mazda);
                    modelSpinner.setAdapter(mazdaAdapter);
                }

                if(position==8)
                {
                    ArrayAdapter<String>allAdapter=new ArrayAdapter<String>(AddNewProductActivity.this,android.R.layout.simple_spinner_dropdown_item,all);
                    modelSpinner.setAdapter(allAdapter);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                ValidateProductData();
            }
        });




    } //on create Bracket


    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(AddNewProductActivity.this, VendorMainActivity.class);
        startActivity(intent);

    }







    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            SelectProductImage.setImageURI(ImageUri);
        }
    }

    private void ValidateProductData()
    {

        Product_Name = productName.getText().toString();
        Product_Description = productDescription.getText().toString();
        Product_Price = productPrice.getText().toString();
        Product_Quantity = productQuantity.getText().toString();
        carMake= makeSpinner.getSelectedItem().toString();
        carModel= modelSpinner.getSelectedItem().toString();
        productCategory= categorySpinner.getSelectedItem().toString();


        if (ImageUri == null)
        {
            Toast.makeText(this, "Product Image is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Product_Name))
        {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Product_Description))
        {
            Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Product_Price))
        {
            Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(Product_Quantity))
        {
            Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
        }

        else
        {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation()
    {
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        DatabaseReference pushedPostRef = ProductsRef.push();

        // Get the unique ID generated by a push()
        productRandomKey = pushedPostRef.getKey();

       // productRandomKey = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AddNewProductActivity.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AddNewProductActivity.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoToDatabase()
    {


        String currentVendorId = mAuth.getCurrentUser().getUid();
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("vid", currentVendorId);
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("image", downloadImageUrl);
        productMap.put("pname", Product_Name.toLowerCase());
        productMap.put("description", Product_Description);
        productMap.put("price", Product_Price);
        productMap.put("quantity", Product_Quantity);
        productMap.put("make",carMake );
        productMap.put("model", carModel);
        productMap.put("category", productCategory);



        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(AddNewProductActivity.this, VendorMainActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AddNewProductActivity.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}
