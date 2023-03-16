package com.auto.autoparts_fyp_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class RegisterCustomerActivity extends AppCompatActivity
{

    private Button CreateAccountButton;
    private EditText CustomerEmail, CustomerPassword, CustomerFirstName, CustomerLastName, CustomerPhoneNumber;
    private TextView AlreadyHaveAccountLink;

    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private ProgressDialog loadingBar;
    private FirebaseUser currentUser;
    private DatabaseReference CustomerRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);


        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();



        InitializeFields();


        AlreadyHaveAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToLoginActivity();
            }
        });


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewAccount();
            }
        });
    }


    private void CreateNewAccount() {
        final String email = CustomerEmail.getText().toString();
        final String password = CustomerPassword.getText().toString();
        final String cfname = CustomerFirstName.getText().toString();
        final String clname = CustomerLastName.getText().toString();
        final String phone= CustomerPhoneNumber.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter password...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(cfname))
        {
            Toast.makeText(this, "Please enter first name...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(clname))
        {
            Toast.makeText(this, "Please enter last name...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please enter phone number...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait, while we wre creating new account for you...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                CustomerData customerData=new CustomerData
                                        (
                                                cfname,clname,email,phone,password

                                );

                                FirebaseDatabase.getInstance().getReference("Customers")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(customerData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            mAuth.getCurrentUser().sendEmailVerification().
                                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful())
                                                            {
                                                                SendUserToLoginActivity();
                                                                CustomerRef = FirebaseDatabase.getInstance().getReference().child("Customers");
                                                                String currentCustomerId = mAuth.getCurrentUser().getUid();
                                                                HashMap<String, Object> CustomerMap = new HashMap<>();
                                                                CustomerMap.put("cid", currentCustomerId);
                                                                CustomerRef.child(currentCustomerId).updateChildren(CustomerMap)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task)
                                                                            {
                                                                                if (task.isSuccessful())
                                                                                {
                                                                                    loadingBar.dismiss();
                                                                                    Toast.makeText(RegisterCustomerActivity.this, "id is added successfully..", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                else
                                                                                {
                                                                                    loadingBar.dismiss();
                                                                                    String message = task.getException().toString();
                                                                                    Toast.makeText(RegisterCustomerActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        });
                                                                Toast.makeText(RegisterCustomerActivity.this, "Customer Account Created Successfully", Toast.LENGTH_SHORT).show();
                                                                loadingBar.dismiss();


                                                            }
                                                            else
                                                            {
                                                                Toast.makeText(RegisterCustomerActivity.this,task.getException().getMessage(),
                                                                        Toast.LENGTH_LONG).show();
                                                                loadingBar.dismiss();
                                                            }


                                                        }
                                                    });




                                        }
                                        else
                                        {
                                            Toast.makeText(RegisterCustomerActivity.this, "Account Not Created", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                        }
                                    }
                                });


                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(RegisterCustomerActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }




    private void InitializeFields()
    {
        CreateAccountButton = (Button) findViewById(R.id.register_button);

        CustomerEmail = (EditText) findViewById(R.id.register_email);

        CustomerPassword = (EditText) findViewById(R.id.register_password);

        CustomerFirstName = (EditText) findViewById(R.id.register_first_name);

        CustomerLastName  = (EditText) findViewById(R.id.register_last_name);

        CustomerPhoneNumber= (EditText) findViewById(R.id.register_customer_number);


        AlreadyHaveAccountLink = (TextView) findViewById(R.id.already_have_account_link);

        loadingBar = new ProgressDialog(this);
    }


    private void SendUserToLoginActivity()
    {
        Intent loginIntent = new Intent(RegisterCustomerActivity.this, CustomerLoginActivity.class);
        startActivity(loginIntent);
    }


    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(RegisterCustomerActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

}
