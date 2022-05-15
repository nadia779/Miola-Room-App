package com.example.miolaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText LogEmail ;
    private EditText LogPass ;
    private Button LogButton ;
    private TextView tvRegisterHere ;
    private FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LogEmail = findViewById(R.id.username);
        LogPass = findViewById(R.id.password);
        LogButton = findViewById(R.id.login);
        tvRegisterHere=findViewById(R.id.newUser);
        mAuth=FirebaseAuth.getInstance();
        LogButton.setOnClickListener(view -> {
            loginUser();
        });
        tvRegisterHere.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this ,RegisterActivity.class));

        });
    }
        private void loginUser(){
            String email, password;
            email = LogEmail.getText().toString();
            password = LogPass.getText().toString();
            if (TextUtils.isEmpty(email)) {
                LogEmail.setError("Email cant be empty");
                LogEmail.requestFocus();
            }else if(TextUtils.isEmpty(password)) {
                LogPass.setError("Password cant be empty");
                LogPass.requestFocus();
            }else{
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "User logged successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Loggin failed!! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

        }

    }
}