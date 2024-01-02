package com.example.shuryasnack;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shuryasnack.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private TextView userEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;

        // Find the TextView in the navigation header
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0); // 0 assumes the first header layout
        userEmailTextView = headerView.findViewById(R.id.userEmailTextView);

        // Initialize Firebase Authentication
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in.
            String userEmail = currentUser.getEmail();

            // Display user's email in TextView or any other UI element
            userEmailTextView.setText(userEmail);
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_produk, R.id.nav_riwayat, R.id.nav_pendapatan, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Set up navigation item selected listener
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_logout) {
                // Handle logout
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logout Berhasil!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SigninActivity.class);
                startActivity(intent);
                return true; // Return true to consume the event
            }

            // Handle other navigation items using NavigationUI
            boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
            if (handled) {
                drawer.closeDrawers(); // Close the drawer when the item is selected
            }

            return handled;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}