package id.ac.polinema.dtsfit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import id.ac.polinema.dtsfit.adapters.CaloriesAdapter;
import id.ac.polinema.dtsfit.fragments.CaloryFragment;
import id.ac.polinema.dtsfit.fragments.ProfileFragment;
import id.ac.polinema.dtsfit.fragments.SaveCaloryFragment;
import id.ac.polinema.dtsfit.generator.ServiceGenerator;
import id.ac.polinema.dtsfit.models.Calory;
import id.ac.polinema.dtsfit.services.CaloryService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements
        CaloryFragment.OnFragmentInteractionListener,
        SaveCaloryFragment.OnFragmentInteractionListener {

    private Profile profile;

    // TODO: Definisikan CaloryService caloryService
    private CaloryService caloryService;
    private List<Calory> calories;
    public static int jumCalory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Profile profile = Application.provideProfile();

        // TODO: Instansiasi nilai caloryService dengan menggunakan ServiceGenerator
        caloryService = ServiceGenerator.createService(CaloryService.class);

        Fragment startFragment = (profile.getBmr() != 0)
                ? CaloryFragment.newInstance()
                : ProfileFragment.newInstance();
        changeFragment(startFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_profile:
                changeFragment(ProfileFragment.newInstance());
                return true;
            case R.id.action_calories:
                changeFragment(CaloryFragment.newInstance());
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeFragment(Fragment fragment) {
        changeFragment(fragment, false);
    }

    private void changeFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment);
        if (addToBackStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onCaloryFragmentCreated(final View view, final CaloriesAdapter adapter, final TextView caloryText) {
        // TODO: Implementasikan load data calory
        Call<List<Calory>> call = caloryService.getCalories();
        call.enqueue(new Callback<List<Calory>>() {
            @Override
            public void onResponse(Call<List<Calory>> call, Response<List<Calory>> response) {
                List<Calory> calories = response.body();

                for(Calory calory:calories){
                    jumCalory += calory.getCalory();
                }

                caloryText.setText("Your calory "+jumCalory+" cal");

                adapter.setCalories(calories);
            }

            @Override
            public void onFailure(Call<List<Calory>> call, Throwable t) {
                Snackbar.make(view,"Oops,Load data gagal!!",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onAddCaloryButtonClicked() {
        // TODO: Implementasikan aksi tombol tambah calory pada CaloryFragment
        changeFragment(SaveCaloryFragment.newInstance(null));
    }

    @Override
    public void onCaloryClicked(Calory calory) {
        // TODO: Implementasikan aksi item calory ketika dipilih pada CaloryFragment
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.ARG_CALORY,calory);
        SaveCaloryFragment saveCaloryFragment = new SaveCaloryFragment();
        saveCaloryFragment.setArguments(bundle);
        changeFragment(SaveCaloryFragment.newInstance(calory));
    }

    @Override
    public void onSaveMenuClicked(final View view, Calory calory) {
        // TODO: Implementasikan aksi ketika menu simpan ditekan pada SaveCaloryFragment
    }


}
