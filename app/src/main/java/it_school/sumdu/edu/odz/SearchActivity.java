package it_school.sumdu.edu.odz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    FinanceViewModel viewModel;
    FinanceAdapter adapter;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        Intent intent = getIntent();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        int gridCount = getResources().getInteger(R.integer.grid_column_count);
        adapter = new FinanceAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridCount));

        viewModel = ViewModelProviders.of(this).get(FinanceViewModel.class);

        if (intent != null) {
            String searchValue = intent.getStringExtra("searchValue");

            viewModel.search(searchValue).observe(this, new Observer<List<Finance>>() {
                @Override
                public void onChanged(List<Finance> finances) {
                    Toast.makeText(getApplicationContext(), "We found "+finances.size() + " record(s) by your request: \""+searchValue+"\"", Toast.LENGTH_LONG).show();

                    adapter.setFinanceList(finances);
                }
            });
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
