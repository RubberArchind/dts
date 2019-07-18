package id.primadev.recyclerview;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static id.primadev.recyclerview.Constant.BASE_URL;

public class RetrofitClient {

    private static Retrofit retrofit;

    //create retrofit instance
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
