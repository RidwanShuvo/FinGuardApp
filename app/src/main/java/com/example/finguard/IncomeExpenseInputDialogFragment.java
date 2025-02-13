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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class IncomeExpenseInputDialogFragment extends DialogFragment {

    private EditText etDate, etType, etAmount;
    private Button btnSave, btnCancel;
    private String type;

    public static IncomeExpenseInputDialogFragment newInstance(String type) {
        IncomeExpenseInputDialogFragment fragment = new IncomeExpenseInputDialogFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
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

        type = getArguments().getString("type");

        etDate.setOnClickListener(v -> showDatePickerDialog());

        btnSave.setOnClickListener(v -> {
            String date = etDate.getText().toString();
            String type = etType.getText().toString();
            String amountStr = etAmount.getText().toString();

            if (date.isEmpty() || type.isEmpty() || amountStr.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount = Double.parseDouble(amountStr);
            String key = FirebaseDatabase.getInstance().getReference("transactions").push().getKey();

            Transaction transaction = new Transaction(key, date, type, amount);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("transactions");
            databaseReference.child(key).setValue(transaction).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Failed to save data", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnCancel.setOnClickListener(v -> dismiss());

        return view;
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
