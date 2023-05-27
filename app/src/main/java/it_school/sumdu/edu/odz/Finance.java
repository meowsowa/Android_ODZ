package it_school.sumdu.edu.odz;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "finance")
public class Finance {

    @PrimaryKey(autoGenerate = true)
    private int finance_id;
    private String category;
    private double amount;
    @NonNull
    private String date;
    private String description;

    public Finance(int finance_id, String category, double amount, @NonNull String date, String description) {
        this.finance_id = finance_id;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public int getFinance_id() {
        return finance_id;
    }

    public void setFinance_id(int finance_id) {
        this.finance_id = finance_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }
}


