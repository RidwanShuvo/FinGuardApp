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
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_dashboard) {
                    showDashboard();
                } else if (id == R.id.nav_expense) {
                    showExpense();
                } else if (id == R.id.nav_income) {
                    showIncome();
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
            }
        });

        // Floating Action Button Click Listener
        fabAdd.setOnClickListener(v -> {
            IncomeExpenseBottomSheetFragment bottomSheet = new IncomeExpenseBottomSheetFragment();
            bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
        });

        // Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menu_dashboard) {
                showDashboard();
            } else if (id == R.id.menu_income) {
                showIncome();
            } else if (id == R.id.menu_expense) {
                showExpense();
            }

            return true;
        });

        // Fetch transactions to update dashboard
        fetchTransactions();
    }

    private void showDashboard() {
        double totalIncome = 0; // TODO: Calculate total income
        double totalExpense = 0; // TODO: Calculate total expense

        // Update UI with total income and expense
        TextView tvTotalIncome = findViewById(R.id.tv_total_income);
        TextView tvTotalExpense = findViewById(R.id.tv_total_expense);

        tvTotalIncome.setText("Total Income: " + totalIncome);
        tvTotalExpense.setText("Total Expense: " + totalExpense);
    }

    private void showIncome() {
        double totalIncome = 0; // TODO: Calculate total income

        // Update UI with total income
        TextView tvTotalIncome = findViewById(R.id.tv_total_income);
        tvTotalIncome.setText("Total Income: " + totalIncome);
    }

    private void showExpense() {
        double totalExpense = 0; // TODO: Calculate total expense

        // Update UI with total expense
        TextView tvTotalExpense = findViewById(R.id.tv_total_expense);
        tvTotalExpense.setText("Total Expense: " + totalExpense);
    }

    private void fetchTransactions() {
        // Add your Firebase code to fetch transactions here
    }

    private void updateDashboard(double totalIncome, double totalExpense) {
        TextView tvTotalIncome = findViewById(R.id.tv_total_income);
        TextView tvTotalExpense = findViewById(R.id.tv_total_expense);

        tvTotalIncome.setText("Total Income: " + totalIncome);
        tvTotalExpense.setText("Total Expense: " + totalExpense);
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
