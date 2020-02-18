package com.company.uneedvhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerRegistrationActivity extends AppCompatActivity {
    EditText mFirstName, mLastName, mEmail, mPassword, mConfirmPassword, mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    ProgressBar mProgressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        mFirstName = findViewById(R.id.Name);
        mLastName = findViewById(R.id.lastName);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.Password);
        mConfirmPassword = findViewById(R.id.confirm_pw);
        mPhone = findViewById(R.id.phone);
        mRegisterBtn = findViewById(R.id.btnLogin);
        mLoginBtn =  findViewById(R.id.login);
        mProgressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = mFirstName.getText().toString().trim();
                String lastName = mLastName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();
                String phone = mPhone.getText().toString().trim();

                boolean validation = true;

                if(TextUtils.isEmpty(firstName)){
                    mFirstName.setError("First Name is Required");
                    validation = false;
                }

                if(TextUtils.isEmpty(lastName)){
                    mLastName.setError("Last Name is Required");
                    validation = false;
                }

                if(TextUtils.isEmpty(phone)){
                    mPhone.setError("Phone Number is Required");
                    validation = false;
                }

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    validation = false;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Please Enter Your Password");
                    validation = false;
                }

                if(TextUtils.isEmpty(confirmPassword)){
                    mConfirmPassword.setError("Please Enter Confirm Password");
                    validation = false;
                }

                if(password.length() < 6){
                    mPassword.setError("Password must be longer than 6 characters");
                    validation = false;
                }

                if(!password.equals(confirmPassword)){
                    mConfirmPassword.setError("Your Confirm Password does not match");
                    validation = false;
                }

                if(!validation){
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CustomerRegistrationActivity.this, "You are Signed Up. ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), CustomerLoginActivity.class));
                        } else{
                            Toast.makeText(CustomerRegistrationActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
