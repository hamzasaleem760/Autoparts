package com.auto.autoparts_fyp_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.google.firebase.iid.FirebaseInstanceId;

public class CustomerLoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    private Button LoginButton, PhoneLoginButton;
    private EditText CustomerEmail, CustomerPassword;
    private TextView NeedNewAccountLink, ForgetPasswordLinkCustomer;

    private DatabaseReference UsersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        mAuth = FirebaseAuth.getInstance();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Customers");

        ForgetPasswordLinkCustomer=(TextView) findViewById(R.id.forget_password_link);



        InitializeFields();


        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SendUserToRegisterActivity();
            }
        });


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AllowUserToLogin();
            }
        });

        ForgetPasswordLinkCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(CustomerLoginActivity.this, ResetPasswordActivity.class);
                startActivity(registerIntent);

            }
        });




    }




    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            SendUserToMainActivity();
        }
    }



    private void AllowUserToLogin() {
        String email = CustomerEmail.getText().toString();
        String password = CustomerPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
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
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful())
                                {
                                    if (mAuth.getCurrentUser().isEmailVerified())
                                    {
                                        SendUserToMainActivity();
                                        Toast.makeText(CustomerLoginActivity.this, "Logged in Successful...", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                    }
                                    else {


                                        Toast.makeText(CustomerLoginActivity.this, "Please Verify your account via your email", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                    }

                                }
                                else
                                {

                                    String message = task.getException().toString();
                                    Toast.makeText(CustomerLoginActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();


                                }

                            }
                        });



        }
    }




    private void InitializeFields()
    {
        LoginButton = (Button) findViewById(R.id.login_button);
        CustomerEmail = (EditText) findViewById(R.id.login_email);
        CustomerPassword = (EditText) findViewById(R.id.login_password);
        NeedNewAccountLink = (TextView) findViewById(R.id.need_new_account_link);
        //ForgetPasswordLink = (TextView) findViewById(R.id.forget_password_link);
        loadingBar = new ProgressDialog(this);
    }



    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(CustomerLoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void SendUserToRegisterActivity()
    {
        Intent registerIntent = new Intent(CustomerLoginActivity.this, RegisterCustomerActivity.class);
        startActivity(registerIntent);
    }
}
