package it_school.sumdu.edu.odz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

public class RecordActivity extends AppCompatActivity {
    FinanceViewModel viewModel;
    Finance finance;


    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_record);
        finance = SelectedFinanceHolder.getSelectedFinance();
        viewModel = ViewModelProviders.of(this).get(FinanceViewModel.class);

        TextView view = findViewById(R.id.descriptionRecord);
        view.setText(finance.getDescription());
        view = findViewById(R.id.amountRecord);
        String category = finance.getCategory();
        String a = new MoneyString(finance.getAmount()).convertToString();
        String amountOut;
        if (Objects.equals(category, "Income")) {
            amountOut = "+" + a + "";
        } else {
            amountOut = "-" + a + "";
        }
        view.setText(amountOut);
        view = findViewById(R.id.dateRecord);
        view.setText(finance.getDate());
        view = findViewById(R.id.typeRecord);
        view.setText(category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onClickDeleteRecord(View view) {
        showDeleteConfirmationDialog();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform the delete operation here
                        deleteItem();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private void deleteItem() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                viewModel.delete(finance);
            }
        });
        Toast.makeText(this, "Finance record was removed!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
