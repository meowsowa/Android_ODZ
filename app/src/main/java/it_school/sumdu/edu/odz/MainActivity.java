package it_school.sumdu.edu.odz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView am;
    FinanceViewModel viewModel;
    FinanceAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new FinanceAdapter(this);
        recyclerView.setAdapter(adapter);
        int gridCount = getResources().getInteger(R.integer.grid_column_count);

        recyclerView.setLayoutManager(new GridLayoutManager(this, gridCount));

        viewModel = ViewModelProviders.of(this).get(FinanceViewModel.class);
        am = findViewById(R.id.amountSum);

        SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBtn(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        viewModel.getCurrentBalance().observe(this, new Observer<Double>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(Double balance) {
                am.setText(new MoneyString(balance).convertToString());
                if (balance < 0) {
                    am.setTextColor(Color.parseColor("#DC2A30"));
                }
            }
        });
        viewModel.getAllFinance().observe(this, new Observer<List<Finance>>() {
            @Override
            public void onChanged(List<Finance> finances) {
                adapter.setFinanceList(finances);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void searchBtn(String searchValue) {
        searchValue = searchValue.trim();
        if (searchValue.length() >= 3) {
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("searchValue", searchValue);
            startActivity(intent);
        }

        Toast.makeText(getApplicationContext(), "Please, enter 3 or more symbols to search", Toast.LENGTH_LONG).show();
    }

    public void openAddActivity(View view) {
        Intent addFinanceIntent = new Intent(this, AddFinance.class);
        startActivity(addFinanceIntent);
    }
}
