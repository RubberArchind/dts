package id.primadev.recyclerview;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.primadev.recyclerview.adapter.HeroesAdapter;
import id.primadev.recyclerview.model.Heroes;
import id.primadev.recyclerview.services.HeroesServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DcFragment extends Fragment{

    private HeroesAdapter heroesAdapter;
    private RecyclerView rv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.dc_fragment,container,false);
        rv = view.findViewById(R.id.rvContact);

        //        create handler for retrofit instance
        HeroesServices services = RetrofitClient.getRetrofitInstance().create(HeroesServices.class);
        Call<List<Heroes>> call = services.getAllHeroes();

        //Execute asyncronously
        call.enqueue(new Callback<List<Heroes>>() {
            @Override
            public void onResponse(Call<List<Heroes>> call, Response<List<Heroes>> response) {
                loadDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Heroes>> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to load", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void loadDataList(List<Heroes> heroesList) {
        heroesAdapter = new HeroesAdapter(heroesList);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(heroesAdapter);
    }








}
