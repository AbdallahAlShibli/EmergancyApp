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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class JoinApp extends AppCompatActivity {
    private TextInputEditText email, password, name, age, nationality, phone, idCard, address, hospital;
    private Button register;
    private TextView login;

    private FirebaseFirestore Ff = FirebaseFirestore.getInstance();
    private FirebaseAuth FA = FirebaseAuth.getInstance();
    private FirebaseUser FU = FA.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jion_app);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        nationality = findViewById(R.id.nationality);
        phone = findViewById(R.id.phone);
        idCard = findViewById(R.id.id_card);
        address = findViewById(R.id.address);
        hospital = findViewById(R.id.hospital);
        login = findViewById(R.id.login);
        register = findViewById(R.id.sign_up);

        login.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), JoinApp.class));
        });

        register.setOnClickListener(view -> {

            final String Email = email.getText().toString().trim();
            final String Password = password.getText().toString().trim();
            final String Name = name.getText().toString();
            final String Age = age.getText().toString();
            final String Nationality = nationality.getText().toString();
            final String Phone = phone.getText().toString().trim();
            final String IDCard = idCard.getText().toString().trim();
            final String Address = address.getText().toString();
            final String Hospital = hospital.getText().toString();


            if (checkEmail(Email) && checkPassword() && checkBox(Name) && checkBox(Age) && checkBox(Nationality) && checkBox(Phone) && checkBox(IDCard) && checkBox(Address) && checkBox(Hospital)){
                FA.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(JoinApp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Register is successfully!", Toast.LENGTH_SHORT).show();

                            CollectionReference userData = Ff.collection("users");
                            Map<String, Object> data1 = new HashMap<>();
                            data1.put("email", Email);
                            data1.put("name", Name);
                            data1.put("age", Age);
                            data1.put("nationality", Nationality);
                            data1.put("phone", Phone);
                            data1.put("idCard", IDCard);
                            data1.put("address", Address);
                            data1.put("hospital", Hospital);




                            try {
                                Thread.sleep(2000);

                                userData.document(FA.getUid()).set(data1);

                                startActivity(new Intent(getApplication(), MainActivity.class));
                                finish();

                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }




                        }else{
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

    private boolean checkBox(String character) {

        boolean check = false;

        if (!character.isEmpty()) return !TextUtils.isEmpty(character);


        return check;
    }

}