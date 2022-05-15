package com.example.miolaproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
    }

    public void goToUserList(View v) {
        startActivity(new Intent(this, DisplayingUsersActivity.class));
    }
    public void goToEmploiAct(View v) {
        startActivity(new Intent(this, EmploiActivity.class));
    }public void goToProfile(View v) {
        startActivity(new Intent(this, AdminActivity.class));
    }

}