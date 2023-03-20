package com.example.food_tanya.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.food_tanya.R;
import com.example.food_tanya.fragments.HomeFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class
MainActivity extends AppCompatActivity  {

    Fragment homeFragment;


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        //use toolbar as action bar
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


navigationView.setNavigationItemSelectedListener(this);*/
        auth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        homeFragment = new HomeFragment();
        loadFragment(homeFragment);


    }
    private void loadFragment(Fragment homeFragments){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,homeFragment);
        transaction.commit();
    }


  /*  @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/
   /* @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.nav_home:
                break;
           /* case R.id.nav_profile:
                Intent intent=new Intent(UserHomePage.this,UserProfile.class);
                startActivity(intent);
                break;
            case R.id.nav_refresh:
                startActivity(getIntent());
                overridePendingTransition(0,0);
                break;
           /* case R.id.nav_update_profile:
                Intent intent1=new Intent(UserProfile.this,UpdateProfileActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_delete_profile:
                break;
            case R.id.nav_update_email:
                Intent intent2=new Intent(UserProfile.this,UpdateEmailActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_settings:
                Toast.makeText(UserProfile.this,"menu_settings",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_change_password:
                break;*/
          /*  case R.id.menu_logout:
                authProfile.signOut();
                Toast.makeText(MainActivity.this,"You have logged out",Toast.LENGTH_LONG).show();
                Intent intent3=new Intent(MainActivity.this,LoginActivity.class);
                //clear stack to prevent user coming back to user procfile
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent3);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.main_menu,menu);
      return true;
  }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_logout){

            auth.signOut();
            startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            finish();

        }else if (id == R.id.menu_my_cart){
            startActivity(new Intent(MainActivity.this,CartActivity.class));
        }

        return true;
    }




}