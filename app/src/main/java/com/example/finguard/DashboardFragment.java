package com.example.finguard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardFragment extends Fragment {

    private TextView tvTotalIncome, tvTotalExpense, tvBalance;
    private DatabaseReference incomeRef, expenseRef;
    private double totalIncome = 0, totalExpense = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        tvTotalIncome = view.findViewById(R.id.tv_total_income);
        tvTotalExpense = view.findViewById(R.id.tv_total_expense);
        tvBalance = view.findViewById(R.id.tv_balance);

        incomeRef = FirebaseDatabase.getInstance().getReference("Income");
        expenseRef = FirebaseDatabase.getInstance().getReference("Expense");

        fetchTotalIncome();
        fetchTotalExpense();

        return view;
    }

    private void fetchTotalIncome() {
        incomeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalIncome = 0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    double amount = data.child("amount").getValue(Double.class);
                    totalIncome += amount;
                }
                tvTotalIncome.setText("Total Income"+"৳ " + totalIncome);
                updateBalance();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void fetchTotalExpense() {
        expenseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalExpense = 0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    double amount = data.child("amount").getValue(Double.class);
                    totalExpense += amount;
                }
                tvTotalExpense.setText("Total Expense"+"৳ " + totalExpense);
                updateBalance();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void updateBalance() {
        double balance = totalIncome - totalExpense;
        tvBalance.setText("Total balance"+"৳ " + balance);
    }
}
