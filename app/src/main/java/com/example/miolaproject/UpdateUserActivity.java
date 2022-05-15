package com.example.miolaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateUserActivity extends AppCompatActivity {
    // creating variables for our edit text
    private EditText fullNameEdt ,emailEdt , SemesterEdt ,phoneEdt , RoleEdt , ImageEdt , departementEdt , passEdt ;
    private Button updateUserBtn , deleteUserBtn;
    //creating strings fo storing our editText
    private  String username,email,semester,phone,departement,image,password,role ;
    // creating a variable for firebasefirestore.
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        Users users = (Users) getIntent().getSerializableExtra("user");
        db=FirebaseFirestore.getInstance();
        fullNameEdt=findViewById(R.id.userUpdateFullName);
        emailEdt=findViewById(R.id.UpdateEmailUser);
        SemesterEdt=findViewById(R.id.UpdateSemester);
        ImageEdt=findViewById(R.id.UpdateImage);
        phoneEdt=findViewById(R.id.UpdatePhone);
        departementEdt=findViewById(R.id.UpdateDepartement);
        passEdt=findViewById(R.id.UpdatePassUser);
        RoleEdt=findViewById(R.id.UpdateRole);
        updateUserBtn=findViewById(R.id.idBtnUpdateUser);
        deleteUserBtn=findViewById(R.id.idBtnDeleteUser);

        fullNameEdt.setText(users.getFullName());
        emailEdt.setText(users.getEmail());
        SemesterEdt.setText(users.getSemester());
        phoneEdt.setText(users.getPhone());
        RoleEdt.setText(users.getRole());
        ImageEdt.setText(users.getImage());
        departementEdt.setText(users.getDepartement());
        passEdt.setText(users.getPassword());

        deleteUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calling method to delete the user.
                deleteUser(users);
            }
        });

        updateUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=fullNameEdt.getText().toString();
                email=emailEdt.getText().toString();
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
                    updateUser( users , username, email, password, role, departement, image, semester, phone );
                }
            }
            
        });
    }

    private void deleteUser(Users users) {
        db.collection("Users").document(users.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // inside on complete method we are checking
                // if the task is success or not.
                if (task.isSuccessful()){
                    // this method is called when the task is success
                    // after deleting we are starting our MainActivity.
                    Toast.makeText(UpdateUserActivity.this,"User has been deleted from database" ,Toast.LENGTH_LONG).show();
                    Intent i = new Intent(UpdateUserActivity.this , AdminActivity.class);
                    startActivity(i);
                }else {
                    // if the delete operation is failed
                    // we are displaying a toast message.
                    Toast.makeText(UpdateUserActivity.this,"Deletion failed!!! " ,Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void updateUser( Users users , String username, String email, String password, String role, String departement, String image, String semester, String phone) {
        // inside this method we are passing our updated values
        // inside our object class and later on we
        // will pass our whole object to firebase Firestore.
        Users updatedCourse =new Users(username,  email ,  semester ,  phone ,  departement ,  image, password , role);
        // after passing data to object class we are
        // sending it to firebase with specific document id.
        // below line is use to get the collection of our Firebase Firestore.
        db.collection("Users").
                // below line is use toset the id of
                // document where we have to perform
                // update operation.
                        document(users.getId()).set(updatedCourse).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // on successful completion of this process
                // we are displaying the toast message.
                Toast.makeText(UpdateUserActivity.this, "users has been updated..", Toast.LENGTH_LONG).show();
            }
            }).addOnFailureListener(new OnFailureListener() {
            // inside on failure method we are
            // displaying a failure message.
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateUserActivity.this, "Fail to update the data..", Toast.LENGTH_LONG).show();
            }
        });


    }
}