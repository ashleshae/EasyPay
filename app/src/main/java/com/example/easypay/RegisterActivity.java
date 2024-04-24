package com.example.easypay;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import com.example.easypay.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    DatabaseHelper databaseHelper;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(RegisterActivity.this);
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double UserID = Math.random() * 10000;
                String FullName = binding.FullName.getText().toString();
                String email = binding.UserEmail.getText().toString();
                String MobileNo = binding.UserMobileNo.getText().toString();
                String Password = binding.Password.getText().toString();
                String ConfirmPassword = binding.ConfirmPassword.getText().toString();
                awesomeValidation.addValidation(RegisterActivity.this,R.id.FullName, "[a-zA-Z\\s]+",R.string.fnameErr);
                awesomeValidation.addValidation(RegisterActivity.this,R.id.UserEmail,android.util.Patterns.EMAIL_ADDRESS,R.string.emailErr);
                awesomeValidation.addValidation(RegisterActivity.this,R.id.UserMobileNo,RegexTemplate.TELEPHONE,R.string.phoneErr);
                awesomeValidation.addValidation(RegisterActivity.this,R.id.Password,regexPassword,R.string.PassErr);
                awesomeValidation.addValidation(RegisterActivity.this,R.id.ConfirmPassword,R.id.Password,R.string.confirmErr);
                if (FullName.equals("") || email.equals("") || MobileNo.equals("") || Password.equals("") || ConfirmPassword.equals("")) {
                    Toast.makeText(RegisterActivity.this, "All fields are mandatory.", Toast.LENGTH_SHORT).show();
                } else {
                    if (Password.equals(ConfirmPassword)) {
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);
                        if (!checkUserEmail) {
                            if(awesomeValidation.validate()) {
                                Boolean insert = databaseHelper.insertData(FullName, email, MobileNo, Password);
                                if (insert) {
                                    Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                }
                            } else { Toast.makeText(RegisterActivity.this, "Enter Valid Details", Toast.LENGTH_SHORT).show(); }
                        } else {
                            Toast.makeText(RegisterActivity.this, "User Already Exists! Please Login", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Already account present navigation
        TextView btn1 = findViewById(R.id.AccountPresent);
        class MyOnClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        }
        MyOnClickListener listener1 = new MyOnClickListener();
        btn1.setOnClickListener(listener1);
    }
}