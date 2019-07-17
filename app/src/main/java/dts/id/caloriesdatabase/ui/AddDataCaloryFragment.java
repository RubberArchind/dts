package dts.id.caloriesdatabase.ui;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import dts.id.caloriesdatabase.CaloriesRepository;
import dts.id.caloriesdatabase.R;
import dts.id.caloriesdatabase.db.entity.LogCaloriesEntity;
import dts.id.caloriesdatabase.viewModel.LogCaloriesViewModel;

import static dts.id.caloriesdatabase.ui.CalculateBMRFragment.KEY_MIN;
/**
 * A simple {@link Fragment} subclass.
 */
public class AddDataCaloryFragment extends Fragment {

    private Spinner spinnerFoodTime;
    private EditText edtFoodInformation;
    private EditText edtFoodCalory;
    private Button buttonSaveFood;
    private SharedPreferences mSharedPreferences;
    private String date;
    private LogCaloriesViewModel logCaloriesViewModel;
    private float jumlah;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_add_data_calory, container, false);
        initiateComponent(mView);
        logCaloriesViewModel = ViewModelProviders.of(getActivity()).get(LogCaloriesViewModel.class);
        return mView;
    }

    private void initiateComponent(View mView) {
        spinnerFoodTime = mView.findViewById(R.id.spinnerFoodTime);
        edtFoodInformation = mView.findViewById(R.id.edtFoodInformation);
        edtFoodCalory = mView.findViewById(R.id.edtFoodCalory);
        buttonSaveFood = mView.findViewById(R.id.buttonSaveFood);


        mSharedPreferences = getActivity().getSharedPreferences(CalculateBMRFragment.KEY_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        date = mSharedPreferences.getString(CalculateBMRFragment.KEY_DATE, "");

        buttonSaveFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                String waktuMakan = spinnerFoodTime.getSelectedItem().toString();
                String makanan = edtFoodInformation.getText().toString();
                String kaloriMakanan = edtFoodCalory.getText().toString();
                String tanggal = date;

                if (Validation(makanan, kaloriMakanan)) {
                    //Todo 6 Memanggil method yang ada di viem model untuk menginsert Db
                    LogCaloriesEntity logCaloriesEntity = new LogCaloriesEntity(waktuMakan,makanan,kaloriMakanan,tanggal);
                    logCaloriesViewModel.InsertLogCalories(logCaloriesEntity);
                    jumlah = mSharedPreferences.getFloat(KEY_MIN,0)-Float.parseFloat(kaloriMakanan);
                    SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                    mEditor.putFloat(KEY_MIN,jumlah);
                    mEditor.apply();
                    DialogSave(mView, "Tersimpan!!", "Data Berhasil Disimpan. Apakah Anda Akan Memasukkan Data Lagi?");
                } else {
                    new AlertDialog.Builder(mView.getContext())
                            .setTitle("Attention!!")
                            .setMessage("Pastikan Semua Data Terisi")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).show();
                }

            }
        });
    }

    private boolean Validation(String makanan, String kaloriMakanan) {
        if ((makanan.trim().length() > 0) && (kaloriMakanan.trim().length() > 0)) {
            return true;
        } else return false;
    }

    private void DialogSave(View mView, String title, String message) {
        new AlertDialog.Builder(mView.getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Tambah Data", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        spinnerFoodTime.setSelection(0);
                        edtFoodInformation.setText("");
                        edtFoodCalory.setText("");
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                        FragmentTransaction fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentManager.replace(R.id.fragment_container, new LogCaloriesFragment())
                                .addToBackStack(null)
                                .commit();
                    }
                })
                .show();
    }

}
