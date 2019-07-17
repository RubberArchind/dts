package dts.id.caloriesdatabase.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;


import java.util.List;

import dts.id.caloriesdatabase.CaloriesRepository;
import dts.id.caloriesdatabase.db.entity.LogCaloriesEntity;

public class LogCaloriesViewModel extends AndroidViewModel {
    //Todo 5 Membuat View Model sebagai untuk komunikasi antara UI dan Repository
    private CaloriesRepository mCaloriesRepository;
    private LiveData<List<LogCaloriesEntity>> listLogCalories;

    public LogCaloriesViewModel(@NonNull Application application) {
        super(application);
        mCaloriesRepository = new CaloriesRepository(application);
    }


    public void InsertLogCalories(LogCaloriesEntity logCaloriesEntity){
        mCaloriesRepository.InsertCalories(logCaloriesEntity);
    }


    public void deleteAll() {mCaloriesRepository.deleteAllCalories();}

    public LiveData<List<LogCaloriesEntity>> GetListLogCategories(String date){
        return listLogCalories = mCaloriesRepository.GetAllCaloriesByDate(date);
    }

}
