package com.example.miolaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DisplayingUsersActivity extends AppCompatActivity {
    // creating variables for our recycler view,
    // array list, adapter, firebase firestore
    // and our progress bar.
    private RecyclerView userRV;
    private ArrayList<Users> usersArrayList;
    private UserRvAdapter usersRVAdapter;
    private FirebaseFirestore db;
    ProgressBar loadingPB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaying_users);

        // initializing our variables.
        userRV = findViewById(R.id.idRVUsers);
        loadingPB = findViewById(R.id.idProgressBar);

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();
        // creating our new array list
        usersArrayList = new ArrayList<>();
        userRV.setHasFixedSize(true);
        userRV.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        usersRVAdapter = new UserRvAdapter(usersArrayList, this);
        // setting adapter to our recycler view.
        userRV.setAdapter(usersRVAdapter);
        // below line is use to get the data from Firebase Firestore.
        // previously we were saving data on a reference of Users
        // now we will be getting the data from the same reference.
        db.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // after getting the data we are calling on success method
                // and inside this method we are checking if the received
                // query snapshot is empty or not.
                if (!queryDocumentSnapshots.isEmpty()) {
                    // if the snapshot is not empty we are
                    // hiding our progress bar and adding
                    // our data in a list.
                    loadingPB.setVisibility(View.GONE);
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        // after getting this list we are passing
                        // that list to our object class.
                        Users c = d.toObject(Users.class);
                        // and we will pass this object class
                        // inside our arraylist which we have
                        // created for recycler view.
                        usersArrayList.add(c);
                        // below is the updated line of code which we have to
                        // add to pass the document id inside our modal class.
                        // we are setting our document id with d.getId() method
                        c.setId(d.getId());
                    }
                        // after adding the data to recycler view.
                        // we are calling recycler view notifuDataSetChanged
                        // method to notify that data has been changed in recycler view.
                        usersRVAdapter.notifyDataSetChanged();
                    } else{
                        // if the snapshot is empty we are displaying a toast message.
                        Toast.makeText(DisplayingUsersActivity.this, "No data found in Database", Toast.LENGTH_LONG).show();
                    }
                }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DisplayingUsersActivity.this, "Fail to get the data.", Toast.LENGTH_LONG).show();

            }
        });
    }
}
