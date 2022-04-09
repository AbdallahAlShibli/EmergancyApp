package com.tarek.emergancyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button emergency, signOut;
    private FirebaseFirestore Ff = FirebaseFirestore.getInstance();
    private FirebaseAuth FA = FirebaseAuth.getInstance();
    private FirebaseUser FU = FA.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emergency = findViewById(R.id.emergency_call);
        signOut = findViewById(R.id.sign_out);

        signOut.setOnClickListener(view -> {
            FA.signOut();
            startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        });


        emergency.setOnClickListener(view -> {


            int resID = getResources().getIdentifier("alert0", "raw", getPackageName());

            MediaPlayer mediaPlayer = MediaPlayer.create(this, resID);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);


            CollectionReference userData = Ff.collection("status");
            Map<String, Object> data1 = new HashMap<>();
            data1.put("emergencyCall", FU.getUid());

            try {
                Thread.sleep(4000);
                userData.document(FA.getUid()).set(data1);
                Toast.makeText(getApplicationContext(), "Alert sent to the hospital!", Toast.LENGTH_SHORT).show();

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            try {
                try {
                    Thread.sleep(20000);
                    mediaPlayer.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }

        });
    }
}