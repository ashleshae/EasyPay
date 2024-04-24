package com.example.easypay;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import com.example.easypay.databinding.ActivityMainBinding;
import com.example.easypay.databinding.ActivityRegisterBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(MainActivity.this);
        DatabaseHelper db = new DatabaseHelper(this);

        binding.SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.InputUser.getText().toString();
                //db.getCurrentUser(email);
                String Password = binding.InputPassword.getText().toString();
                if(email.equals("") || Password.equals("")){
                    Toast.makeText(MainActivity.this, "All Fields are Mandatory!", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email,Password);
                    if(checkCredentials)
                    {
                        Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        TextView btn1 = findViewById(R.id.Register);
        class MyOnClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (MainActivity.this,RegisterActivity.class));
            }
        }
        MyOnClickListener listener1 = new MyOnClickListener();
        btn1.setOnClickListener(listener1);
    }
}