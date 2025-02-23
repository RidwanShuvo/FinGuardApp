package com.example.finguard;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.finguard.models.Transaction;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class IncomeExpenseInputDialogFragment extends DialogFragment {

    private EditText etDate, etType, etAmount;
    private Button btnSave, btnCancel;
    private String category; // "Income" or "Expense"

    public static IncomeExpenseInputDialogFragment newInstance(String category) {
        IncomeExpenseInputDialogFragment fragment = new IncomeExpenseInputDialogFragment();
        Bundle args = new Bundle();
        args.putString("category", category);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income_expense_input_dialog, container, false);

        etDate = view.findViewById(R.id.et_date);
        etType = view.findViewById(R.id.et_type);
        etAmount = view.findViewById(R.id.et_amount);
        btnSave = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);

        category = getArguments().getString("category"); // Get "Income" or "Expense"

        etDate.setOnClickListener(v -> showDatePickerDialog());

        btnSave.setOnClickListener(v -> saveTransaction());

        btnCancel.setOnClickListener(v -> dismiss());

        return view;
    }

    private void saveTransaction() {
        String date = etDate.getText().toString().trim();
        String type = etType.getText().toString().trim();
        String amountStr = etAmount.getText().toString().trim();

        if (date.isEmpty() || type.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(category);
        String key = databaseReference.push().getKey();

        if (key == null) {
            Toast.makeText(getContext(), "Failed to generate transaction ID", Toast.LENGTH_SHORT).show();
            return;
        }

        Transaction transaction = new Transaction(key, date, type, amount);

        databaseReference.child(key).setValue(transaction)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), category + " transaction added!", Toast.LENGTH_SHORT).show();
                    dismiss();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Firebase Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });

        // 🔍 Debug Log
        System.out.println("Transaction Data → Category: " + category + ", Date: " + date + ", Type: " + type + ", Amount: " + amount);
    }



    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year1, month1, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            etDate.setText(date);
        }, year, month, day);
        datePickerDialog.show();
    }
}
