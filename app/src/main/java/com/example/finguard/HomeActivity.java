package com.example.finguard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FloatingActionButton fabAdd;
    private BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize Views
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        fabAdd = findViewById(R.id.fab_add);
        mAuth = FirebaseAuth.getInstance();
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Enable Toolbar as ActionBar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup Drawer Toggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // Handle Navigation Item Clicks
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_dashboard) {
                showFragment(new DashboardFragment());
            } else if (id == R.id.nav_expense) {
                showFragment(new ExpenseFragment());
            } else if (id == R.id.nav_income) {
                showFragment(new IncomeFragment());
            } else if (id == R.id.nav_logout) {
                // Logout User
                mAuth.signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            // Close Drawer after Click
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Floating Action Button Click Listener
        fabAdd.setOnClickListener(v -> {
            IncomeExpenseBottomSheetFragment bottomSheet = new IncomeExpenseBottomSheetFragment();
            bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
        });

        // Bottom Navigation View Item Selected Listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.btm_menu_dashboard) {
                showFragment(new DashboardFragment());
                return true;
            } else if (item.getItemId() == R.id.btm_menu_income) {
                showFragment(new IncomeFragment());
                return true;
            } else if (item.getItemId() == R.id.btm_menu_expense) {
                showFragment(new ExpenseFragment());
                return true;
            } else {
                return false;
            }
        });


        // Show the default fragment on startup
        if (savedInstanceState == null) {
            showFragment(new DashboardFragment());
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
