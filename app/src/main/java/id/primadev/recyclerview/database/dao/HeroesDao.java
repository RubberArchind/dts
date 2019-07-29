package id.primadev.recyclerview.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import id.primadev.recyclerview.database.entity.HeroesEntity;

@Dao
public interface HeroesDao {
    @Query("SELECT * FROM heroes_table ORDER BY id DESC")
    LiveData<List<HeroesEntity>> getAllHeroes();

    @Query("SELECT * FROM heroes_table WHERE side= :side ORDER BY id DESC")
    LiveData<List<HeroesEntity>> getHeroBySide(String side);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(HeroesEntity heroesEntity);

    @Query("DELETE FROM heroes_table")
    void deleteAll();

    @Update
    void updateTask(HeroesEntity heroesEntity);
}
