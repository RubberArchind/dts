package id.primadev.recyclerview;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import id.primadev.recyclerview.database.HeroesDatabase;
import id.primadev.recyclerview.database.dao.HeroesDao;
import id.primadev.recyclerview.database.entity.HeroesEntity;

public class HeroesRepository {
    private final HeroesDao heroesDao;

    public HeroesRepository(Context context) {
        HeroesDatabase db = HeroesDatabase.getInstance(context);
        heroesDao = db.heroesDao();
    }

    public void insertAllTask(int id,String name, String desc, String imgURL, String side){
        HeroesEntity heroesEntity = new HeroesEntity(id,name,desc,imgURL,side,false);
        insertAllTask(heroesEntity);
    }

    public void insertAllTask(final HeroesEntity heroesEntity) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                heroesDao.insertAll(heroesEntity);
                return null;
            }
        }.execute();
    }

    public void updateTask(int id,String name, String desc, String imgURL, String side,boolean fav){
        HeroesEntity heroesEntity = new HeroesEntity(id,name,desc,imgURL,side,fav);
        updateTask(heroesEntity);
    }

    public void updateTask(final HeroesEntity heroesEntity){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                heroesDao.updateTask(heroesEntity);
                return null;
            }
        }.execute();
    }

    public void deleteAllTask() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                heroesDao.deleteAll();
                return null;
            }
        }.execute();
    }

    public LiveData<List<HeroesEntity>> getAllHeroes() {
        return heroesDao.getAllHeroes();
    }

    public LiveData<List<HeroesEntity>> getHeroBySide(String side) {
        return heroesDao.getHeroBySide(side);
    }
}

