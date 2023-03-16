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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterVendorActivity extends AppCompatActivity {
    private Button CreateAccountButton;
    private EditText InputVendorName, InputVendorPhoneNumber, InputVendorPassword ,InputVendorEmail,InputShopName;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private DatabaseReference VendorRef;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vendor);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();

        InputShopName  = (EditText) findViewById(R.id.register_shop_name_input);

        //InputShopAddress = (EditText) findViewById(R.id.register_shop_address_input);

        InputVendorName = (EditText) findViewById(R.id.register_vendor_name_input);

        InputVendorPhoneNumber = (EditText) findViewById(R.id.register_vendor_phone_number_input);

        InputVendorEmail  = (EditText) findViewById(R.id.register_vendor_email_input);

        InputVendorPassword = (EditText) findViewById(R.id.register_vendor_password_input);



        CreateAccountButton = (Button) findViewById(R.id.register_vendor_btn);






        loadingBar = new ProgressDialog(this);


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccount();

            }
        });
    }



    private void CreateAccount()
    {
        final String vsname=InputShopName.getText().toString();
       // final String vsaddress=InputShopAddress.getText().toString();
        final String vname = InputVendorName.getText().toString();
        final String vphone = InputVendorPhoneNumber.getText().toString();
        final String email=InputVendorEmail.getText().toString().trim();
        final String password = InputVendorPassword.getText().toString();
        final String verified="false";
        final String status ="unblocked";




        if (TextUtils.isEmpty(vsname))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
//        else if (TextUtils.isEmpty(vsaddress))
//        {
//            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
//        }
        else if (TextUtils.isEmpty(vname))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(vphone))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait, while we wre creating new account for you...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                VendorData vendorData=new VendorData
                                        (
                                                vsname,vname,vphone,email,password,verified,status

                                        );

                                FirebaseDatabase.getInstance().getReference("Vendors")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(vendorData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {

                                            mAuth.getCurrentUser().sendEmailVerification()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful())
                                                            {
                                                                SendUserToLoginActivity();
                                                                VendorRef = FirebaseDatabase.getInstance().getReference().child("Vendors");
                                                                String currentVendorId = mAuth.getCurrentUser().getUid();
                                                                HashMap<String, Object> VendorMap = new HashMap<>();
                                                                VendorMap.put("vid", currentVendorId);
                                                                VendorRef.child(currentVendorId).updateChildren(VendorMap)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task)
                                                                            {
                                                                                if (task.isSuccessful())
                                                                                {
                                                                                    loadingBar.dismiss();
                                                                                    Toast.makeText(RegisterVendorActivity.this, "id is added successfully..", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                else
                                                                                {
                                                                                    loadingBar.dismiss();
                                                                                    String message = task.getException().toString();
                                                                                    Toast.makeText(RegisterVendorActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        });
                                                                Toast.makeText(RegisterVendorActivity.this, "Vendor Account Created Successfully", Toast.LENGTH_SHORT).show();
                                                                loadingBar.dismiss();

                                                            }

                                                        }
                                                    });



                                        }
                                        else
                                        {
                                            Toast.makeText(RegisterVendorActivity.this, "Account Not Created", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                        }
                                    }
                                });


                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(RegisterVendorActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }



    private void SendUserToLoginActivity()
    {
        Intent loginIntent = new Intent(RegisterVendorActivity.this, VendorLoginActivity.class);
        startActivity(loginIntent);
    }


    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(RegisterVendorActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

}

