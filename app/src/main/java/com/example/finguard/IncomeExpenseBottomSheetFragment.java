package com.example.finguard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class IncomeExpenseBottomSheetFragment extends BottomSheetDialogFragment {

    private Button btnIncome, btnExpense;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income_expense_bottom_sheet, container, false);

        btnIncome = view.findViewById(R.id.btn_income);
        btnExpense = view.findViewById(R.id.btn_expense);

        btnIncome.setOnClickListener(v -> {
            dismiss(); // Close the bottom sheet before opening dialog
            IncomeExpenseInputDialogFragment inputDialog = IncomeExpenseInputDialogFragment.newInstance("Income");
            inputDialog.show(getParentFragmentManager(), "IncomeInputDialog");
        });

        btnExpense.setOnClickListener(v -> {
            dismiss(); // Close the bottom sheet before opening dialog
            IncomeExpenseInputDialogFragment inputDialog = IncomeExpenseInputDialogFragment.newInstance("Expense");
            inputDialog.show(getParentFragmentManager(), "ExpenseInputDialog");
        });

        return view;
    }
}
