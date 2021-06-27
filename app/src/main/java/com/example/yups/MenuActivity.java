package com.example.yups;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import fragments.Groups_fragment;
import fragments.Phrases_fragment;
import fragments.Setting_fragment;
import fragments.User_fragment;

public class MenuActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);
        fab = (FloatingActionButton)findViewById(R.id.fab);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Phrases_fragment()).commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_phrase();
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment fragment = null;

                    switch (item.getItemId())
                    {
                        case R.id.phrases:
                            fragment = new Phrases_fragment();
                            break;

                        case R.id.groups:
                            fragment = new Groups_fragment();
                            break;

                        case R.id.profile:
                            fragment = new User_fragment();
                            break;

                        case R.id.settings:
                            fragment = new Setting_fragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

                    return true;
                }
            };

    private void new_phrase() {
        Toast.makeText(this, "Not implemented yet", Toast.LENGTH_LONG).show();
    }
}
