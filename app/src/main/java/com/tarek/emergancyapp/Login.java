package com.tarek.emergancyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private TextInputEditText email, password;
    private Button login;
    private TextView register;

    private FirebaseAuth FA = FirebaseAuth.getInstance();
    private FirebaseUser FU = FA.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.sign_in);
        register = findViewById(R.id.register);

        register.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), JoinApp.class));
        });

        login.setOnClickListener(view -> {
            String Email = email.getText().toString().trim();
            String Password = password.getText().toString().trim();

            if (checkEmail(Email) && checkPassword()){

                FA.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login is successfully!", Toast.LENGTH_SHORT).show();

                            try {
                                Thread.sleep(2000);



                                startActivity(new Intent(getApplication(), MainActivity.class));
                                finish();

                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), "Error!, Check the Password or Email!", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }

        });


    }


    private boolean checkPassword() {
        String FPassword = password.getText().toString();
        if (!FPassword.isEmpty()) {
            if (FPassword.length() >= 6 && FPassword.length() <= 16) {
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "Please check your password!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {

            Toast.makeText(getApplicationContext(), "Please check your password!", Toast.LENGTH_SHORT).show();
            return false;

        }
    }

    private boolean checkEmail(String character) {

        boolean check = false;

        if (!character.isEmpty()) {

            return !TextUtils.isEmpty(character) && Patterns.EMAIL_ADDRESS.matcher(character).matches();


        } else {
            Toast.makeText(getApplicationContext(), "Please check your Email!", Toast.LENGTH_SHORT).show();
            return check;
        }
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