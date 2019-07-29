package id.primadev.recyclerview.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import id.primadev.recyclerview.HeroesRepository;
import id.primadev.recyclerview.database.entity.HeroesEntity;

public class HeroesViewModel extends AndroidViewModel {
    private HeroesRepository heroesRepository;
    private LiveData<List<HeroesEntity>> allHeroes;


    public HeroesViewModel(@NonNull Application application) {
        super(application);
        heroesRepository = new HeroesRepository(application);
    }

    public void insert(int id,String name,String desc,String imgURL,String side)
    {heroesRepository.insertAllTask(id,name,desc,imgURL,side);}

    public void update(int id,String name,String desc,String imgURL,String side,boolean fav)
    {heroesRepository.updateTask(id,name,desc,imgURL,side,fav);}

    public LiveData<List<HeroesEntity>> getAllHeroes(){
        return allHeroes= heroesRepository.getAllHeroes();
    }

    public LiveData<List<HeroesEntity>> getHeroBySide(String side){
        return allHeroes= heroesRepository.getHeroBySide(side);
    }

    public void deleteAll(){heroesRepository.deleteAllTask();}

}
