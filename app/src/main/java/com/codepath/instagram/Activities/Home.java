package com.codepath.instagram.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.codepath.instagram.Fragments.ComposeFragment;
import com.codepath.instagram.Fragments.PostFragment;
import com.codepath.instagram.Fragments.ProfileFragment;
import com.codepath.instagram.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/* Katie Mishra - FBU 2019 - krmishra@stanford.edu
   Home is the main screen of the app, which allows users to compose
   and post to Instagram. Users can also logout of the app.
 */
public class Home extends AppCompatActivity {

    private final String TAG = "Home";
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        fragment = new PostFragment();
                        break;
                    case R.id.action_compose:
                        fragment = new ComposeFragment();
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment();
                        break;
                    default: return true;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set feed to be default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}
