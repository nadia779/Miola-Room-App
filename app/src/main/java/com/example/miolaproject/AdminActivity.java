package com.example.miolaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class AdminActivity extends AppCompatActivity {

    private EditText fullNameEdt, emailEdt, SemesterEdt, phoneEdt, RoleEdt, ImageEdt, departementEdt, passEdt;
    private Button submitUserBtn, showbttn;
    private String username, email, semester, phone, departement, image, password, role;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        db = FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        fullNameEdt = findViewById(R.id.userFullName);
        emailEdt = findViewById(R.id.EmailUser);
        SemesterEdt = findViewById(R.id.UserSemester);
        ImageEdt = findViewById(R.id.UserImage);
        phoneEdt = findViewById(R.id.UserPhone);
        departementEdt = findViewById(R.id.UserDepartement);
        passEdt = findViewById(R.id.PassUser);
        RoleEdt = findViewById(R.id.UserRole);
        submitUserBtn = findViewById(R.id.idBtnSubmitUser);
        showbttn = findViewById(R.id.idBtnShowUser);


        // adding onclick listener to view data in new activity


      showbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity on button click
                Intent i = new Intent(AdminActivity.this,DisplayingUsersActivity.class);
                startActivity(i);
            }
        });

        submitUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = fullNameEdt.getText().toString();
                email = emailEdt.getText().toString();
                semester = SemesterEdt.getText().toString();
                phone = phoneEdt.getText().toString();
                image = ImageEdt.getText().toString();
                password = passEdt.getText().toString();
                departement = departementEdt.getText().toString();
                role = RoleEdt.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    fullNameEdt.setError("Please enter user Name");
                } else if (TextUtils.isEmpty(email)) {
                    emailEdt.setError("Please enter user email");

                } else if (TextUtils.isEmpty(password)) {
                    passEdt.setError("Please enter user password");
                } else if (TextUtils.isEmpty(semester)) {
                    SemesterEdt.setError("Please enter user semester");

                } else if (TextUtils.isEmpty(departement)) {
                    departementEdt.setError("Please enter user departement");
                } else if (TextUtils.isEmpty(role)) {
                    RoleEdt.setError("Please enter user role");
                } else if (TextUtils.isEmpty(phone)) {
                    phoneEdt.setError("Please enter user phone");
                } else if (TextUtils.isEmpty(image)) {
                    ImageEdt.setError("Please enter user image");
                } else {
                    addDataToFirestore(username, email, password, role, departement, image, semester, phone );
                }
            }

                });

    }

    private void addDataToFirestore(String username, String email, String password, String role, String departement, String image, String semester, String phone) {
       mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AdminActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminActivity.this,AdminActivity.class));
                }else{
                    Toast.makeText(AdminActivity.this, "Registration failed!! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                }
            }
        });
        CollectionReference dbUsers = db.collection("Users");
        Users users = new Users(username,  email ,  semester ,  phone ,  departement ,  image, password , role);
        dbUsers.add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(AdminActivity.this, "User has been added to Firebase FireStore", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminActivity.this, "Fail to add user \n" + e, Toast.LENGTH_SHORT).show();
            }
        });

        }
    }
