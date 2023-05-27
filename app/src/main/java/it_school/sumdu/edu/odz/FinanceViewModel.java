package it_school.sumdu.edu.odz;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FinanceViewModel extends AndroidViewModel {

    private FinanceRepository mRepository;
    private LiveData<List<Finance>> mAllFinance;
    private LiveData<Double> mBalance;

    public FinanceViewModel(@NonNull Application application) {
        super(application);
        mRepository = new FinanceRepository(application);
        mAllFinance = mRepository.getAllFinances();
        mBalance = mRepository.getCurrentBalance();
    }

    LiveData<List<Finance>> getAllFinance() {
        return mAllFinance;
    }
    LiveData<Double> getCurrentBalance() {
        return mBalance;
    }
    LiveData<Double> searchResult() {
        return mBalance;
    }

    public void insert(Finance finance) {
        mRepository.insert(finance);
    }

    public LiveData<List<Finance>> search(String searchValue) {
        return mRepository.search(searchValue);
    }

    public void delete(Finance finance) {
        mRepository.delete(finance);
    }
}