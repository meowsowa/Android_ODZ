package it_school.sumdu.edu.odz;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FinanceRepository {
    private FinanceDao financeDao;
    private LiveData<List<Finance>> mAllFinances;
    private LiveData<Double> mAmount;

    FinanceRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        financeDao = db.financeDao();
        mAllFinances = financeDao.getAllFinances();
        mAmount = financeDao.getCurrentBalance();
    }

    LiveData<List<Finance>> getAllFinances(){
        return mAllFinances;
    }
    LiveData<Double> getCurrentBalance(){
        return mAmount;
    }

    public void insert(Finance finance){
        new InsertAsyncTask(financeDao).execute(finance);
    }
    public LiveData<List<Finance>> search(String searchValue) {
        return financeDao.search("%" + searchValue + "%");
    }

    public void delete(Finance finance) {
        financeDao.deleteFinance(finance);
    }
    private static class InsertAsyncTask extends AsyncTask<Finance, Void, Void> {

        private FinanceDao mAsyncTaskDao;

        InsertAsyncTask(FinanceDao financeDao) {
            mAsyncTaskDao = financeDao;
        }

        @Override
        protected Void doInBackground(final Finance... finances) {
            mAsyncTaskDao.insertFinance(finances[0]);
            return null;
        }
    }
}
