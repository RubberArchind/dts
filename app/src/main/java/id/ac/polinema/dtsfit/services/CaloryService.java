package id.ac.polinema.dtsfit.services;

import java.util.List;

import id.ac.polinema.dtsfit.models.Calory;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CaloryService {

    // TODO: Definisikan service getCalories()
    @GET("/RubberArchind/FAKE-REST-API/calories")
    Call<List<Calory>> getCalories();

    // TODO: Definisikan service addCalory()
    @POST("/RubberArchind/FAKE-REST-API/calories")
    Call<Calory> addCalory(@Body Calory calory);

    // TODO: Definisikan service editCalory()
    @PUT("/RubberArchind/FAKE-REST-API/calories/{id}")
    Call<Calory> editCalory(@Path("Id") int id, @Body Calory calory);
}
