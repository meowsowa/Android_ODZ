package it_school.sumdu.edu.odz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Calendar;

public class AddFinance extends AppCompatActivity {
    private EditText description, amount;
    private ToggleButton toggleBtn;
    Button datePickerBtn;
    private String datePickerText;
    private String categoryText = "Expense";

    private FinanceViewModel viewModel;
    private int year, month, day, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_finance);
        final Calendar calendar = Calendar.getInstance();

        description = findViewById(R.id.description);
        amount = findViewById(R.id.amount);
        toggleBtn = findViewById(R.id.toggleButton);
        viewModel = ViewModelProviders.of(this).get(FinanceViewModel.class);

        datePickerBtn = findViewById(R.id.dataPicker);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        changeDatePickerText();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onSaveClick(View view) {
        String descriptionText = description.getText().toString().trim();
        if (descriptionText.equals("")) {
            Toast.makeText(this, "Please, enter description field!", Toast.LENGTH_SHORT).show();
            return;
        }
        double amountNumber = Double.parseDouble(amount.getText().toString().trim());
        amountNumber = (double) Math.round(amountNumber * 100) / 100;;

        if (amountNumber <= 0) {
            Toast.makeText(this, "Please, enter amount!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (toggleBtn.isChecked()) {
            categoryText = "Income";
        }

        Finance finance = new Finance(
                0,
                categoryText,
                amountNumber,
                datePickerText,
                descriptionText
        );
        viewModel.insert(finance);
        Toast.makeText(this, "Finance record was added!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int y, int m, int d) {
        month = m + 1;
        day = d;
        year = y;
        changeDatePickerText();

        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(),"timePicker");
    }
    public void processTimePickerResult(int h, int m) {
        hour = h;
        minute = m;
    }

    private void changeDatePickerText() {
        String monthString = Integer.toString(month);
        if (month < 10) {
            monthString = 0 + "" + month;
        }
        datePickerText = year + "." + monthString + "." + day + " " + hour + ":" + minute;
        datePickerBtn.setText(datePickerText);
    }
}
