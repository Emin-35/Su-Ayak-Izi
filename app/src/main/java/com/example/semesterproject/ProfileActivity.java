package com.example.semesterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileActivity extends AppCompatActivity implements DBHelper.UpdateCallback, NavigationBarView.OnItemSelectedListener{
    private ImageView profileHolder;
    private TextView userNameProfile,userEmailProfile,nameUpdate,lastnameUpdate;
    private RadioButton updateFemale,updateMale;
    private DBHelper dbHelper;
    private Button updateButton;
    @Override
    public void onUpdateComplete() {
        Toast.makeText(ProfileActivity.this, "Bilgileriniz Güncellendi.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ProfileActivity.this, TranslationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_tab);

        profileHolder = findViewById(R.id.profileHolder);

        userNameProfile = findViewById(R.id.userNameProfile);
        userEmailProfile = findViewById(R.id.userEmailProfile);

        nameUpdate = findViewById(R.id.nameUpdate);
        lastnameUpdate = findViewById(R.id.lastnameUpdate);
        updateFemale = findViewById(R.id.updateFemale);
        updateMale = findViewById(R.id.updateMale);
        updateButton = findViewById(R.id.updateButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle update button click
                Toast.makeText(ProfileActivity.this, "Update Button", Toast.LENGTH_SHORT).show();
            }
        });

        //To set the screen via bottom navigation tools, I created an helper method named: NavigationHelper
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewProfile);
        bottomNavigationView.setOnItemSelectedListener(this);

        // Set the "navigation_profile" item as the default selected option
        MenuItem profileMenuItem = bottomNavigationView.getMenu().findItem(R.id.navigation_profile);
        profileMenuItem.setChecked(true);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.navigation_home) {
            // Handle home button click
            // Replace content with home layout
            Intent intent = new Intent(ProfileActivity.this, TranslationActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.navigation_profile) {
            // We don't need to handle profile page because we're already in it
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
