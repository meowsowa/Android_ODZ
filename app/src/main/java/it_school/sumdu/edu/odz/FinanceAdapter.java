package it_school.sumdu.edu.odz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class FinanceAdapter extends RecyclerView.Adapter<FinanceAdapter.ViewHolder> {
    private final LayoutInflater layoutInflater;
    private List<Finance> financeList;
    FinanceAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (financeList != null) {
            Finance finance = financeList.get(position);
            holder.bind(finance);
        }
    }

    @Override
    public int getItemCount() {
        if (financeList != null) {
            return financeList.size();
        }
        else {
            return 0;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    void setFinanceList (List<Finance> finances) {
        financeList = finances;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView type, description, date, amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
        }

        public void bind(Finance finance) {
            String category = finance.getCategory();
            String a = new MoneyString(finance.getAmount()).convertToString();
            type.setText(finance.getCategory());
            description.setText(finance.getDescription());
            String amountOut;
            if (Objects.equals(category, "Income")) {
                amountOut = "+" + a + "";
            } else {
                amountOut = "-" + a + "";
                type.setTextColor(Color.parseColor("#DC2A30"));
            }
            amount.setText(amountOut);
            String d = finance.getDate();
            date.setText(d);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectedFinanceHolder.setSelectedFinance(finance);

                    Intent intent = new Intent(itemView.getContext(), RecordActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
