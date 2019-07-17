package dts.id.caloriesdatabase.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dts.id.caloriesdatabase.db.dao.LogCaloriesDao;
import dts.id.caloriesdatabase.db.entity.LogCaloriesEntity;

@Database(entities = {LogCaloriesEntity.class}, version = 1)
public abstract class CaloriesRoomDatabase extends RoomDatabase {
    //Todo 3 Membuat Class yang Mengektend RoomDatabase
    private static CaloriesRoomDatabase caloriesRoomDatabase;

    private static final String DATABASE_NAME = "CaloriesDatabase";

    public abstract LogCaloriesDao logCaloriesDao();

    public static CaloriesRoomDatabase GetDatabase(Context context){
        if(caloriesRoomDatabase==null){
            synchronized (CaloriesRoomDatabase.class){
                if(caloriesRoomDatabase==null){
                    caloriesRoomDatabase = Room.databaseBuilder(context, CaloriesRoomDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return caloriesRoomDatabase;
    }
}
