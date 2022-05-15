package com.example.miolaproject;



import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardStudentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    private CardView etudiantCard, groupCard,emploiCard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_student);
        mAuth= FirebaseAuth.getInstance();
        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView= findViewById(R.id.nav_view);
        toolbar= findViewById(R.id.toolbar);
        Menu menu =navigationView.getMenu();
        menu.findItem(R.id.nav_login).setVisible(false);


        navigationView.bringToFront();
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



    }
    @Override
    public void onBackPressed(){

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{super.onBackPressed();}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.nav_profile:
                Intent intent = new Intent(DashboardStudentActivity.this, ProfileTeacherActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_emp:
                Intent intent4 = new Intent(DashboardStudentActivity.this, EmploiActivity.class);
                startActivity(intent4);
                break;

            case R.id.nav_etud:
                Intent intent2 = new Intent(DashboardStudentActivity.this, TeacherActivity.class);
                startActivity(intent2);
                break;

            case R.id.nav_logout:
                mAuth.signOut();
                signout();
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void signout() {
        Intent mainActivity = new Intent(DashboardStudentActivity.this, LoginActivity.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivity);
        finish();
    }

    @Override
    public void onClick(View view) {
        /*switch(view.getId()){
            case R.id.etudiantCard:
                startActivity(new Intent(this, MesEtudiants.class));break;

            case R.id.emploiCard:
                startActivity(new Intent(this, DownloadEmploi.class));break;
        }*/

    }
}
