package id.primadev.recyclerview;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.util.List;

import id.primadev.recyclerview.adapter.HeroesAdapterDc;
import id.primadev.recyclerview.adapter.HeroesAdapterMarvel;
import id.primadev.recyclerview.database.HeroesDatabase;
import id.primadev.recyclerview.database.entity.HeroesEntity;
import id.primadev.recyclerview.database.entity.UserEntity;
import id.primadev.recyclerview.fragments.AccountFragment;
import id.primadev.recyclerview.fragments.DcFragment;
import id.primadev.recyclerview.fragments.FavoriteFragment;
import id.primadev.recyclerview.fragments.MarvelFragment;
import id.primadev.recyclerview.model.Heroes;
import id.primadev.recyclerview.model.HeroesToPopulate;
import id.primadev.recyclerview.retrofit.RetrofitClient;
import id.primadev.recyclerview.services.HeroesServices;
import id.primadev.recyclerview.viewmodel.HeroesViewModel;
import id.primadev.recyclerview.viewmodel.UserViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        DcFragment.OnFragmentInteractionListener,
        MarvelFragment.OnFragmentInteractionListener{

    private HeroesServices heroesServices;
    private UserViewModel userViewModel;
    private HeroesViewModel heroesViewModel;
    BottomNavigationView bottomNavigationView;
    String tag;
    HeroesAdapterDc heroesAdapterDc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        heroesServices = RetrofitClient.createService(HeroesServices.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        heroesViewModel = ViewModelProviders.of(this).get(HeroesViewModel.class);
        heroesViewModel.getAllHeroes().observe(this, new Observer<List<HeroesEntity>>() {
            @Override
            public void onChanged(@Nullable List<HeroesEntity> heroesEntities) {
                if (heroesEntities==null){
                    populateDb();
                }
            }
        });
        heroesViewModel.getHeroBySide("DC").observe(this, new Observer<List<HeroesEntity>>() {
            @Override
            public void onChanged(@Nullable List<HeroesEntity> heroesEntities) {
                if (heroesEntities==null){
                    populateDb();
                }
            }
        });
        heroesViewModel.getHeroBySide("MARVEL").observe(this, new Observer<List<HeroesEntity>>() {
            @Override
            public void onChanged(@Nullable List<HeroesEntity> heroesEntities) {
                if (heroesEntities==null){
                    populateDb();
                }
            }
        });

        loadFragment("dc",new DcFragment().newInstance());

        bottomNavigationView = findViewById(R.id.bn_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        heroesViewModel.getAllHeroes().observe(this, new Observer<List<HeroesEntity>>() {
            @Override
            public void onChanged(@Nullable List<HeroesEntity> heroesEntities) {
                for (HeroesEntity heroesEntity : heroesEntities){
                    System.out.println("-----------------------------");
                    System.out.println(heroesEntity.getId());
                    System.out.println(heroesEntity.getHeroesName());
                    System.out.println(heroesEntity.getSide());
                }
            }
        });
    }

    private void populateDb() {
        Call<List<HeroesToPopulate>> call = heroesServices.heroesDcToPopulate();
        call.enqueue(new Callback<List<HeroesToPopulate>>() {
            @Override
            public void onResponse(Call<List<HeroesToPopulate>> call, Response<List<HeroesToPopulate>> response) {
                List<HeroesToPopulate> heroesToPopulates = response.body();
                if (heroesToPopulates!=null) {
                    for (HeroesToPopulate heroes : heroesToPopulates) {
//                        System.out.println("---------------------");
//                        System.out.println(heroes.getId());
//                        System.out.println(heroes.getName());
//                        System.out.println(heroes.getDesc());
                        heroesViewModel.insert(heroes.getId(),heroes.getName(), heroes.getDesc(), heroes.getImgURL(), "DC");
                    }
                }else{
                    Log.d("Response error","");
                }
            }

            @Override
            public void onFailure(Call<List<HeroesToPopulate>> call, Throwable t) {
            }
        });

        Call<List<HeroesToPopulate>> call1 = heroesServices.heroesMarvelToPopulate();
        call1.enqueue(new Callback<List<HeroesToPopulate>>() {
            @Override
            public void onResponse(Call<List<HeroesToPopulate>> call, Response<List<HeroesToPopulate>> response) {
                List<HeroesToPopulate> heroesToPopulates = response.body();
                if (heroesToPopulates!=null) {
                    for (HeroesToPopulate heroes : heroesToPopulates) {
//                        System.out.println("---------------------");
//                        System.out.println(heroes.getId());
//                        System.out.println(heroes.getName());
//                        System.out.println(heroes.getDesc());
                        heroesViewModel.insert(heroes.getId(),heroes.getName(), heroes.getDesc(), heroes.getImgURL(), "MARVEL");
                    }
                }else{
                    Log.d("Response error","");
                }
            }

            @Override
            public void onFailure(Call<List<HeroesToPopulate>> call, Throwable t) {

            }
        });
    }

    public boolean loadFragment(String tag,Fragment fragment) {
        bottomNavigationView = findViewById(R.id.bn_main);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(fragment,tag)
                    .replace(R.id.fl_container, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.dc_menu:
                tag = "dc";
                fragment = new DcFragment().newInstance();
                break;
            case R.id.marvel_menu:
                tag = "marvel";
                fragment = new MarvelFragment().newInstance();
                break;

            case R.id.favorite_menu:
                tag = "fav";
                fragment = new FavoriteFragment();
                break;
            case R.id.account_menu:
                tag = "acc";
                fragment = new AccountFragment();
                break;
                //5 Success
                //6 signup
        }   return loadFragment(tag,fragment);
    }

    public void onDcFragmentCreated(final View view, final HeroesAdapterDc adapter){
//        Call<List<Heroes>> call = heroesServices.getAllHeroesDc();
//        call.enqueue(new Callback<List<Heroes>>() {
//            @Override
//            public void onResponse(Call<List<Heroes>> call, Response<List<Heroes>> response) {
//                List<Heroes> heroes = response.body();
//                if(heroes!=null) {
//                    adapter.setHeroesList(heroes);
//                }else{
//                    Log.d("Response error","");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Heroes>> call, Throwable t) {
//                Snackbar.make(view,"Oops,Load data gagal!!",Snackbar.LENGTH_LONG).show();
//            }
//        });

    }


    public void onMarvelFragmentCreated(final View view, final HeroesAdapterMarvel adapter){
//        Call<List<Heroes>> call = heroesServices.getAllHeroesMarvel();
//        call.enqueue(new Callback<List<Heroes>>() {
//            @Override
//            public void onResponse(Call<List<Heroes>> call, Response<List<Heroes>> response) {
//                List<Heroes> heroes = response.body();
//                if(heroes!=null) {
//                    adapter.setHeroesList(heroes);
//                }else{
//                    Log.d("Response error","");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Heroes>> call, Throwable t) {
//                Snackbar.make(view,"Oops,Load data gagal!!",Snackbar.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        loadFragment("dc",new DcFragment().newInstance());
        bottomNavigationView.setSelectedItemId(R.id.dc_menu);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_exit)
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        finishAffinity();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

}
