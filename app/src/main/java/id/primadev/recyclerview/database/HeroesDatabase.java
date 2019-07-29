package id.primadev.recyclerview.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

import id.primadev.recyclerview.database.dao.HeroesDao;
import id.primadev.recyclerview.database.entity.HeroesEntity;

@Database(entities = {HeroesEntity.class}, version = 3,exportSchema = false)
public abstract class HeroesDatabase extends RoomDatabase {

    private static HeroesDatabase INSTANCE;
    public abstract HeroesDao heroesDao();

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE heroes_table "
                    + " ADD COLUMN favorite INTEGER");
        }
    };

    public synchronized static HeroesDatabase getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context,HeroesDatabase.class,"db_heroes")
                    .addMigrations(MIGRATION_2_3)
                    .build();
        }
        return INSTANCE;
    }


}
