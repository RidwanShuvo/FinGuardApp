package com.example.finguard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finguard.adapters.TransactionAdapter;
import com.example.finguard.models.Transaction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IncomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private List<Transaction> transactionList;
    private DatabaseReference incomeRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        transactionList = new ArrayList<>();
        adapter = new TransactionAdapter(transactionList);
        recyclerView.setAdapter(adapter);

        incomeRef = FirebaseDatabase.getInstance().getReference("Income");

        fetchIncomeTransactions();

        return view;
    }

    private void fetchIncomeTransactions() {
        incomeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transactionList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Transaction transaction = data.getValue(Transaction.class);
                    transactionList.add(transaction);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
