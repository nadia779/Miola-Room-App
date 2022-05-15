package com.example.miolaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText RegEmail ;
    private EditText RegPass ;
    private Button RegButton ;
    private TextView tvLoginHere ;
    private FirebaseAuth mAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegEmail = findViewById(R.id.email);
        RegPass = findViewById(R.id.password);
        RegButton = findViewById(R.id.register);
        tvLoginHere=findViewById(R.id.loginHere);
        mAuth=FirebaseAuth.getInstance();

        RegButton.setOnClickListener(view -> {
            createUser();
        });
        tvLoginHere.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this ,LoginActivity.class));

        });
    }
    private void createUser(){
        String email, password;
        email = RegEmail.getText().toString();
        password = RegPass.getText().toString();
        if (TextUtils.isEmpty(email)) {
          RegEmail.setError("Email cant be empty");
          RegEmail.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            RegPass.setError("Password cant be empty");
            RegPass.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful()){
                         Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                     }else{
                         Toast.makeText(RegisterActivity.this, "Registration failed!! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                     }
                }
            });
        }
    }
    }
