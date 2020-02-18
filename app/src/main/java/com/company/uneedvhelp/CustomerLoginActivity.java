package com.company.uneedvhelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomerLoginActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private Button Login;
    private Button UserRegistered;
    private int counter=5;
    private TextView Info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        Name = (EditText) findViewById(R.id.Name);
        Password = (EditText) findViewById(R.id.Password);
        Login = (Button)findViewById(R.id.btnLogin);
        UserRegistered = (Button)findViewById(R.id.UserRegister);
        Info = (TextView) findViewById(R.id.tvInfo);

        Info.setText("No. of attempts remaining: 5");

        UserRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCustomerRegistrationActivity();
            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String UserName = Name.getText().toString().trim();
                String UserPassword = Password.getText().toString().trim();

                boolean validation = true;

                if(TextUtils.isEmpty(UserName)) {
                    Name.setError("Valid User Id is Required");
                    validation = false;
                }

                if(TextUtils.isEmpty(UserPassword)) {
                    Password.setError("Valid Password is Required");
                    validation = false;
                }

                if(!validation) return;
                validate(Name.getText().toString(), Password.getText().toString());
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
            Info.setText("No. of attempts remaining: " + String.valueOf(counter));

            if (counter == 0) {
                Login.setEnabled(false);
            }
        }

    }
}
