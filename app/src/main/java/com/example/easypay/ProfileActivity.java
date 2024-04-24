package com.example.easypay;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import android.os.Bundle;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private TextView NameTextView;
    private TextView PhoneTextView;
    private TextView EmailTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        DatabaseHelper db = new DatabaseHelper(this);

        profileImageView = findViewById(R.id.displayImg);
        NameTextView = findViewById(R.id.displayName);
        PhoneTextView = findViewById(R.id.displayPhone);
        EmailTextView = findViewById(R.id.displayEmail);

        NameTextView.setText(db.getUsername());
        PhoneTextView.setText(db.getPhoneNo());
        EmailTextView.setText(db.getEmail());

        TextView PaymentPage = findViewById(R.id.Pay_Details);
        PaymentPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });

        Button editProfile = findViewById(R.id.EditButton);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EditProfileActivity.class);
                startActivity(intent);
            }
        });


    }
}