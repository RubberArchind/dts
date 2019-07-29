package id.primadev.recyclerview.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import id.primadev.recyclerview.database.entity.UserEntity;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user_table ORDER BY id desc")
    LiveData<List<UserEntity>> getAllUser();

    @Query("SELECT COUNT(*) FROM user_table WHERE username = :user AND password = :pass LIMIT 1")
    LiveData<Integer> getOneUser(String user,String pass);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UserEntity user);

    @Update
    void update(UserEntity userEntity);
}
