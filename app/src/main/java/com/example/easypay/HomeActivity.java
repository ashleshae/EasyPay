package com.example.easypay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton profile = findViewById(R.id.Profilebg);
        ImageButton billsPage = findViewById(R.id.billsHome);
        class MyOnClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
            }
        }
        MyOnClickListener listener1 = new MyOnClickListener();
        profile.setOnClickListener(listener1);

        class MyOnClickListener2 implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, PaymentActivity.class));
            }
        }
        MyOnClickListener2 listener = new MyOnClickListener2();
        billsPage.setOnClickListener(listener);

    }
}