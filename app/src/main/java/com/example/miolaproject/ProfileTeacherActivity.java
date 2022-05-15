package com.example.miolaproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ProfileTeacherActivity extends AppCompatActivity {
    private View view;
    private TextView departement;
    private TextView fullname;

    private TextView email;
    private ImageView profilepicture;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth mAuth;

    private DocumentReference docReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_teacher);
        this.view=view;
        departement=view.findViewById(R.id.departement);
      
        fullname=view.findViewById(R.id.fullname);
        email=view.findViewById(R.id.profil_email);
        email.getPaint().setUnderlineText(true);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + email.getText().toString()));
                intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                startActivity(intent);
            }
        });
        profilepicture=view.findViewById(R.id.profilepicture);
        getProfile();
    }

    private void getProfile() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();

        db.collection("Users").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)try {
                    throw error;
                }catch (FirebaseFirestoreException e){
                    Toast.makeText(ProfileTeacherActivity.this, "", Toast.LENGTH_SHORT).show();
                }
                if(value!=null && value.exists()){
                    Uri uri=Uri.parse(value.getString("photpURL"));
                    Picasso.get().load(uri).into(profilepicture, new Callback() {
                        @Override
                        public void onSuccess() {
                            departement.setText(value.getString("departement"));
                            fullname.setText(value.getString("fullname"));
                            email.setText(user.getEmail().trim());
                            view.setVisibility(View.VISIBLE);
                            try {
                               findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                            }catch (NullPointerException e){}
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(ProfileTeacherActivity.this, "Error try again!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


}
