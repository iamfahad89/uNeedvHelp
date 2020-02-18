package com.company.uneedvhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    FirebaseAuth fireBaseAuth;

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
        fireBaseAuth = FirebaseAuth.getInstance();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomerLoginActivity.class));
                finish();
            }
        });

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

                if(!isValidPhone(phone)){
                    mPhone.setError("Please enter a valid phone number");
                    validation = false;
                }

                if(!isValidEmail(email)){
                    mEmail.setError("Please enter a valid email");
                    validation = false;
                }

                if(!isValidPassword(password)){
                    mPassword.setError("Password has to be at least 6 character. \nPassword should contain at least one uppercase letter, one lowercase letter, one number, and one special character. ");
                    validation = false;
                }

                if(TextUtils.isEmpty(confirmPassword)){
                    mConfirmPassword.setError("Please Confirm Your Password");
                    validation = false;
                }


                if(!password.equals(confirmPassword)){
                    mConfirmPassword.setError("Your Confirm Password does not match");
                    validation = false;
                }

                if(!validation){
                    return;
                }

                fireBaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CustomerRegistrationActivity.this, "You are Signed Up. ", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), CustomerLoginActivity.class));
                        } else{
                            Toast.makeText(CustomerRegistrationActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    // Method to check if an email is valid
    protected boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    // Method to check if a phone number is valid
    protected boolean isValidPhone(String phone) {
        return (!TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches());
    }

    // Method to check if a phone number is valid
    protected boolean isValidPassword(String password) {
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean isAtLeast6   = password.length() >= 6;
        boolean hasSpecial   = !password.matches("[A-Za-z0-9 ]*");

        return hasLowercase && hasUppercase  && isAtLeast6 && hasSpecial;
    }
}
