package id.primadev.recyclerview.services;

import java.util.List;

import id.primadev.recyclerview.database.entity.HeroesEntity;
import id.primadev.recyclerview.model.Heroes;
import id.primadev.recyclerview.model.HeroesToPopulate;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HeroesServices {

    @GET("/RubberArchind/fake-api/heroesdc")
    Call<List<Heroes>> getAllHeroesDc();


    @GET("/RubberArchind/fake-api/heroesmarvel")
    Call<List<Heroes>> getAllHeroesMarvel();

    @GET("/RubberArchind/fake-api/heroesdc")
    Call<List<HeroesToPopulate>> heroesDcToPopulate();


    @GET("/RubberArchind/fake-api/heroesmarvel")
    Call<List<HeroesToPopulate>> heroesMarvelToPopulate();

}
