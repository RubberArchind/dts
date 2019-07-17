package dts.id.caloriesdatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import dts.id.caloriesdatabase.db.CaloriesRoomDatabase;
import dts.id.caloriesdatabase.db.dao.LogCaloriesDao;
import dts.id.caloriesdatabase.db.entity.LogCaloriesEntity;


public class CaloriesRepository {
    //Todo 4 Membuat Calories Repository
    private  CaloriesRoomDatabase caloriesRoomDatabase;
    private  static  LogCaloriesDao logCaloriesDao;


    public CaloriesRepository(Context context){
        caloriesRoomDatabase = CaloriesRoomDatabase.GetDatabase(context);
        logCaloriesDao = caloriesRoomDatabase.logCaloriesDao();
    }

    public void InsertCalories(LogCaloriesEntity logCaloriesEntity){
        new InsertserCaloriesAsynTask().execute(logCaloriesEntity);
    }

    private class InsertserCaloriesAsynTask extends AsyncTask<LogCaloriesEntity,Void,Void>{

        @Override
        protected Void doInBackground(LogCaloriesEntity... logCaloriesEntities) {
            logCaloriesDao.InsertLogCalories(logCaloriesEntities[0]);
            return null;
        }
    }

    public void deleteAllCalories()  {
        new deleteAllAsyncTask().execute();
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            logCaloriesDao.deleteAll();
            return null;
        }
    }

    public LiveData<List<LogCaloriesEntity>> GetAllCaloriesByDate(String date){
        return logCaloriesDao.GetAllLogCalories(date);
    }

}
