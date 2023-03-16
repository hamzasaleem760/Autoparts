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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class VendorLoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    private Button LoginButton, PhoneLoginButton;
    private EditText VendorEmail, VendorPassword;
    private TextView NeedNewAccountLink, ForgetPasswordLink;

    private DatabaseReference UsersRef;

    private FirebaseUser currentUser;
    private DatabaseReference RootRef;
    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_login);

        mAuth = FirebaseAuth.getInstance();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Vendors");

        ForgetPasswordLink=(TextView) findViewById(R.id.vendor_forget_password_link);

        ForgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(VendorLoginActivity.this, ResetPasswordActivity.class);
                startActivity(registerIntent);

            }
        });


        InitializeFields();


        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SendVendorToRegisterActivity();
            }
        });


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AllowVendorToLogin();
            }
        });






    }




    @Override
    protected void onStart()
    {
        super.onStart();

    }//on start



    private void AllowVendorToLogin()
    {
        String email = VendorEmail.getText().toString();
        String password = VendorPassword.getText().toString();


        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter password...", Toast.LENGTH_SHORT).show();
        }




        else
        {
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Please wait....");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                if (mAuth.getCurrentUser().isEmailVerified())
                                {
                                    Toast.makeText(VendorLoginActivity.this, "Logged in Successful...", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null ) {
                                        String currentVendorId = user.getUid();
                                        Query query = FirebaseDatabase.getInstance().getReference("Vendors").orderByChild("vid").equalTo(currentVendorId);
                                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.getValue() != null)
                                                {
                                                    Intent intent = new Intent(VendorLoginActivity.this, VendorMainActivity.class);
                                                    startActivity(intent);
                                                }
                                                else
                                                {
                                                    Toast.makeText(VendorLoginActivity.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                }
                                else {


                                    Toast.makeText(VendorLoginActivity.this, "Please Verify your account via your email", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                }




                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(VendorLoginActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });


        }//else bracket

    }//allow function bracket



    private void InitializeFields()
    {
        LoginButton = (Button) findViewById(R.id.vendor_login_button);
        VendorEmail = (EditText) findViewById(R.id.vendor_login_email);
        VendorPassword = (EditText) findViewById(R.id.vendor_login_password);
        NeedNewAccountLink = (TextView) findViewById(R.id.vendor_need_new_account_link);
        //ForgetPasswordLink = (TextView) findViewById(R.id.vendor_forget_password_link);
        loadingBar = new ProgressDialog(this);
    }





    private void SendVendorToVendorMainActivity()
    {
        Intent mainIntent = new Intent(VendorLoginActivity.this, VendorMainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void SendVendorToRegisterActivity()
    {
        Intent registerIntent = new Intent(VendorLoginActivity.this, RegisterVendorActivity.class);
        startActivity(registerIntent);
    }



}

