package com.example.semesterproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class TranslationActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);

        // Set the "navigation_profile" item as the default selected option
        MenuItem profileMenuItem = bottomNavigationView.getMenu().findItem(R.id.navigation_home);
        profileMenuItem.setChecked(true);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.navigation_home) {
            // We don't need to handle home page because we're already in it
            return true;
        }
        else if (id == R.id.navigation_profile) {
            // Handle profile button click
            // Intent profile_tab.xml and replace content
            Intent intent = new Intent(TranslationActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.navigation_settings) {
            // Handle settings button click
            // Replace content with settings layout
            return true;
        }
        else
            return false;
    }
}