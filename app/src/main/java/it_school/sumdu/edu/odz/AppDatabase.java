package it_school.sumdu.edu.odz;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = { Finance.class }, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FinanceDao financeDao();
    private static final String DATABASE_NAME = "finance_database";
    private static AppDatabase instance;

    static AppDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(instance).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        public final FinanceDao fDao;

        PopulateDbAsync(AppDatabase db) {
            fDao = db.financeDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
//            fDao.clearDB();
            fDao.insertFinance(new Finance(1, "Income", 23000.50, "2023.04.16 12:33", "Salary"));
            fDao.insertFinance(new Finance(2, "Income", 200.30, "2023.04.19 19:10", "From friends"));
            fDao.insertFinance(new Finance(3, "Expense", 731.08, "2022.04.19 22:01", "Taxes"));
            return null;
        }
    }
}