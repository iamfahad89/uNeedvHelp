package com.company.uneedvhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class CustomerLoginActivity extends AppCompatActivity {
    private EditText mName;
    private EditText mPassword;
    private Button mLogin;
    private TextView mUserRegistered;
    private int counter=5;
    private TextView mInfo;
    private FirebaseAuth fireBaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        mName = findViewById(R.id.Name);
        mPassword = findViewById(R.id.Password);
        mLogin = findViewById(R.id.btnLogin);
        mUserRegistered = findViewById(R.id.UserRegister);
        mInfo = findViewById(R.id.tvInfo);
        fireBaseAuth = FirebaseAuth.getInstance();

        mInfo.setText("No. of attempts remaining: 5");

        if(fireBaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mUserRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCustomerRegistrationActivity();
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mName.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                boolean validation = true;

                if(TextUtils.isEmpty(email)) {
                    mName.setError("Valid Email is Required");
                    validation = false;
                }

                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("Valid Password is Required");
                    validation = false;
                }

                if(!validation) return;

                fireBaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(CustomerLoginActivity.this, "You are Logged in.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            Toast.makeText(CustomerLoginActivity.this, "Your email or password is not correct. ", Toast.LENGTH_LONG).show();
                            counter--;
                            mInfo.setText("No. of attempts remaining: " + counter);

                            if (counter == 0) {
                                mLogin.setEnabled(false);
                            }
                        }
                    }
                });
            }
        });

}

    public void OpenCustomerRegistrationActivity(){
        Intent intent = new Intent(CustomerLoginActivity.this, CustomerRegistrationActivity.class);
        startActivity(intent);
    }

    private void validate(String Name, String Password) {
        if ((Name.equals("admin")) && (Password.equals("123456"))){
            Intent intent = new Intent(CustomerLoginActivity.this, HomepageActivity.class);
            startActivity(intent);
        } else {

            counter--;
            mInfo.setText("No. of attempts remaining: " + String.valueOf(counter));

            if (counter == 0) {
                mLogin.setEnabled(false);
            }
        }

    }
}
