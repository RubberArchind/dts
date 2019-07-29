package id.primadev.recyclerview.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

import id.primadev.recyclerview.R;
import id.primadev.recyclerview.adapter.HeroesAdapterDc;
import id.primadev.recyclerview.adapter.HeroesAdapterMarvel;
import id.primadev.recyclerview.database.entity.HeroesEntity;
import id.primadev.recyclerview.model.Heroes;
import id.primadev.recyclerview.viewmodel.HeroesViewModel;

import static id.primadev.recyclerview.Constant.USERNAME_KEY;

public class MarvelFragment extends Fragment implements HeroesAdapterMarvel.onRvListener{

    private HeroesAdapterMarvel heroesAdapterMarvel;
    private RecyclerView rv;
    private List<HeroesEntity> heroesList;
    private OnFragmentInteractionListener mListener;
    private HeroesViewModel heroesViewModel;
    private SharedPreferences sharedPreferences;


    public MarvelFragment(){

    }

    public static MarvelFragment newInstance(){
        MarvelFragment fragment = new MarvelFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            mListener =(OnFragmentInteractionListener) context;
        }else{
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_marvel,container,false);
        HeroesAdapterMarvel heroesAdapterMarvel = new HeroesAdapterMarvel(getContext());
        this.heroesList = heroesAdapterMarvel.heroesList;
        heroesViewModel = ViewModelProviders.of(this).get(HeroesViewModel.class);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        Context context = getActivity();
        heroesViewModel.getHeroBySide("MARVEL").observe(this, new Observer<List<HeroesEntity>>() {
            @Override
            public void onChanged(@Nullable List<HeroesEntity> heroesEntities) {
                if (heroesEntities!=null) {
                    heroesAdapterMarvel.setHeroesList(heroesEntities);
                    heroesList = heroesEntities;
                }
            }
        });
        rv = view.findViewById(R.id.rvContact);
        rv.setLayoutManager(new LinearLayoutManager(context));
        heroesAdapterMarvel = new HeroesAdapterMarvel(context);
        heroesAdapterMarvel.setListener(this);
        rv.setAdapter(heroesAdapterMarvel);

        if (mListener != null) {
            mListener.onMarvelFragmentCreated(getView(), heroesAdapterMarvel);

        }
    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void onFavClick(View view, int position) {
        final ToggleButton tb = view.findViewById(R.id.togFav);
//        tb.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_fav_border));
        this.sharedPreferences = getActivity().getSharedPreferences("user_sharedpreference", Context.MODE_PRIVATE);
        final String username = sharedPreferences.getString(USERNAME_KEY, null);
        final HeroesEntity heroesEntity = heroesList.get(position);
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (username != null) {
                    if (b) {
                        tb.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_fav));
                        tb.setChecked(true);
                        heroesViewModel
                                .update(heroesEntity.getId(),
                                        heroesEntity.getHeroesName(),
                                        heroesEntity.getDesc(),
                                        heroesEntity.getImgUrl(),
                                        heroesEntity.getSide(),
                                        true);
                        Toast.makeText(getActivity(), "Add Favorite Heroes Success", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getActivity(), position, Toast.LENGTH_SHORT).show();
                    } else {
                        tb.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_fav_border));
                        tb.setChecked(false);
                        heroesViewModel
                                .update(heroesEntity.getId(),
                                        heroesEntity.getHeroesName(),
                                        heroesEntity.getDesc(),
                                        heroesEntity.getImgUrl(),
                                        heroesEntity.getSide(),
                                        false);
                        Toast.makeText(getActivity(), "Remove Favorite Heroes Success", Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(getActivity(),"Please login to add your favorite heroes!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onMarvelFragmentCreated(final View view, final HeroesAdapterMarvel adapter);
    }
}