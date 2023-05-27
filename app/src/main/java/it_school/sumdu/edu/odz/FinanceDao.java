package it_school.sumdu.edu.odz;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FinanceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFinance(Finance finance);

    @Update
    void updateFinance(Finance finance);

    @Delete
    void deleteFinance(Finance finance);

    @Query("DELETE FROM finance")
    void clearDB();

    @Query("SELECT * FROM finance ORDER BY date DESC")
    LiveData<List<Finance>> getAllFinances();

    @Query("SELECT (SELECT sum(amount) FROM finance WHERE category='Income') - (SELECT sum(amount) FROM finance WHERE category='Expense')")
    LiveData<Double> getCurrentBalance();

    @Query("SELECT * FROM finance WHERE finance.description LIKE :s ORDER BY date DESC")
    LiveData<List<Finance>> search(String s);
}
