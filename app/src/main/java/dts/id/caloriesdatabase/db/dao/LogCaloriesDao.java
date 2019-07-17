package dts.id.caloriesdatabase.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.sql.Date;
import java.util.List;

import dts.id.caloriesdatabase.db.entity.LogCaloriesEntity;

@Dao
public interface LogCaloriesDao {
    //Todo 2 Melengkapi DAO

    @Insert
    void InsertLogCalories(LogCaloriesEntity logCaloriesEntity);

    @Query("SELECT * FROM log_calories WHERE tanggal = :date ORDER BY waktu_makan")
    LiveData<List<LogCaloriesEntity>> GetAllLogCalories(String date);

    @Query("DELETE FROM log_calories")
    void deleteAll();
}
