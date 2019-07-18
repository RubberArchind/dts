package id.primadev.recyclerview.services;

import java.util.List;

import id.primadev.recyclerview.model.Heroes;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HeroesServices {

    @GET("/RubberArchind/fake-api/heroes")
    Call<List<Heroes>> getAllHeroes();


}
