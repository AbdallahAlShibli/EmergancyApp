package com.tarek.emergancyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private MaterialButton emergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emergency = findViewById(R.id.emergency_call);
        emergency.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "Alert sent to the hospital!", Toast.LENGTH_SHORT).show();
        });
    }
}