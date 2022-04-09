package com.tarek.emergancyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {
    private Button enter;


    private FirebaseAuth FA = FirebaseAuth.getInstance();
    private FirebaseUser FU = FA.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);


        enter = findViewById(R.id.enter);
        enter.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Login.class));
        });

    }

    @Override
    protected void onPostResume() {

        FU = FA.getCurrentUser();
        if (FU != null) {

            startActivity(new Intent(getApplication(), MainActivity.class));
            finish();

        }
        super.onPostResume();
    }
}