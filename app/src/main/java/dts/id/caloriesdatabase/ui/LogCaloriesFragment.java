package dts.id.caloriesdatabase.ui;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import dts.id.caloriesdatabase.R;
import dts.id.caloriesdatabase.db.CaloriesRoomDatabase;
import dts.id.caloriesdatabase.db.entity.LogCaloriesEntity;
import dts.id.caloriesdatabase.ui.adapter.LogCaloriesAdapter;
import dts.id.caloriesdatabase.viewModel.LogCaloriesViewModel;

public class LogCaloriesFragment extends Fragment {

    private LogCaloriesViewModel logCaloriesViewModel;
    private Button mButtonAddFood;
    private RecyclerView mRecyclerCalories;
    private LogCaloriesAdapter adapterLogCalories;

    private TextView tvCaloriesInformation,tvJumlah;
    private SharedPreferences mSharedPreferences;
    private float bmr;
    private String date;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.log_calories_fragment, container, false);
        initiateComponent(mView);
//        reset=1;
//        if(reset==1) {
//            reset=0;
//            logCaloriesViewModel.deleteAll();
//        }
        return mView;
    }


    private void initiateComponent(View mView) {
        mButtonAddFood = mView.findViewById(R.id.buttonAddFood);
        tvCaloriesInformation = mView.findViewById(R.id.tvCaloriesInformation);
        tvJumlah = mView.findViewById(R.id.tvJumlah);

        mRecyclerCalories = mView.findViewById(R.id.recyclerCalories);
        mRecyclerCalories.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSharedPreferences = getActivity().getSharedPreferences(CalculateBMRFragment.KEY_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        bmr = mSharedPreferences.getFloat(CalculateBMRFragment.KEY_BMR,0);
        date = mSharedPreferences.getString(CalculateBMRFragment.KEY_DATE,"");
        float i = mSharedPreferences.getFloat(CalculateBMRFragment.KEY_MIN,0);

        tvCaloriesInformation.setText("Kebutuhan Kalori Anda "+ bmr);
        if(i>0) {
            tvJumlah.setText("Kekurangan kalori anda " + i);
        }else if(i==0){
            tvJumlah.setText("Selamat kebutuhan kalori anda sudah tercukupi!!!");
        }else{
            tvJumlah.setText("Kalori anda berlebihan");
        }
        mButtonAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentManager.replace(R.id.fragment_container,new AddDataCaloryFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        adapterLogCalories = new LogCaloriesAdapter(getActivity());
        mRecyclerCalories.setAdapter(adapterLogCalories);
        //Todo 7 Menggunakan View Model untuk Mengeset adapter
        logCaloriesViewModel = ViewModelProviders.of(getActivity()).get(LogCaloriesViewModel.class);
            logCaloriesViewModel.GetListLogCategories(date).observe(getActivity(), new Observer<List<LogCaloriesEntity>>() {
                @Override
                public void onChanged(List<LogCaloriesEntity> logCaloriesEntities) {
                    adapterLogCalories.SetListLogCalories(logCaloriesEntities);
                }
            });
    }
}
