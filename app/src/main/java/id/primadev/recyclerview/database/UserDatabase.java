package id.primadev.recyclerview.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import id.primadev.recyclerview.database.dao.UserDao;
import id.primadev.recyclerview.database.entity.UserEntity;

@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
  private static UserDatabase Instance;

  private static final String DATABASE_NAME= "db_user";

  public abstract UserDao userDao();

  public static  UserDatabase getInstance(Context context){
      if(Instance == null){
          synchronized (UserDatabase.class){
              if (Instance == null) {
                  Instance = Room.databaseBuilder(context, UserDatabase.class, DATABASE_NAME).build();
              }
          }
      }
      return Instance;
  }

}
