package dts.id.caloriesdatabase.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dts.id.caloriesdatabase.R;
import dts.id.caloriesdatabase.db.CaloriesRoomDatabase;
import dts.id.caloriesdatabase.db.entity.LogCaloriesEntity;
import dts.id.caloriesdatabase.viewModel.LogCaloriesViewModel;


public class CalculateBMRFragment extends Fragment {

    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton;
    private EditText edtTinggi, edtBeratBadan, edtUsia;
    private Spinner spinnerAktivitas;
    private Button buttonHitung;
    public static SharedPreferences mSharedPreferences;
    public static final String KEY_SHARED_PREF_NAME = "Calories";
    public static final String KEY_BMR = "Calories_Needed";
    public static final String KEY_DATE = "Date";
    public static final String KEY_MIN = "Minus";

    private LogCaloriesViewModel logCaloriesViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_calculate_bmr, container, false);
        initiateComponent(mView);
        logCaloriesViewModel = ViewModelProviders.of(getActivity()).get(LogCaloriesViewModel.class);
        return mView;
    }

    private void initiateComponent(View mView) {
        mRadioGroup = mView.findViewById(R.id.radioGroup);
        edtTinggi = mView.findViewById(R.id.edtTinggiBadan);
        edtBeratBadan = mView.findViewById(R.id.edtBeratBadan);
        edtUsia = mView.findViewById(R.id.edtUsia);
        spinnerAktivitas = mView.findViewById(R.id.spinnerAktivitasFisik);
        buttonHitung = mView.findViewById(R.id.buttonHitung);

        mSharedPreferences = getActivity().getSharedPreferences(KEY_SHARED_PREF_NAME, Context.MODE_PRIVATE);


        clickListener(mView);
    }

    private void clickListener(final View mView) {
        buttonHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadioButton = mView.findViewById(mRadioGroup.getCheckedRadioButtonId());
                String jenisKelamin = mRadioButton.getText().toString();
                int idAktivitas = spinnerAktivitas.getSelectedItemPosition();
                if (validation(edtBeratBadan.getText().toString(), edtTinggi.getText().toString(), edtUsia.getText().toString())) {
                    float hasilBmr = hitungBmr(jenisKelamin,
                            Float.valueOf(edtBeratBadan.getText().toString()),
                            Float.valueOf(edtTinggi.getText().toString()),
                            Float.valueOf(edtUsia.getText().toString()),
                            idAktivitas);
                    Spanned message = Html.fromHtml("Kebutuhan Kalori Anda Setiap Hari Adalah ..... " + " <b>" + String.valueOf(hasilBmr) + " </b>");
                    BmrDialog(mView, "Perhitungan Kalori!", message, hasilBmr);
                } else {
                    AttentionDialog(mView, "Attention!", "Semua Field Harus Diisi");
                }
            }
        });
    }

    private float hitungBmr(String jenisKelamin, float beratBadan, float tinggiBadan, float usia, int idAktivitas) {
        float bmrResult = 0f;

        if (jenisKelamin.equalsIgnoreCase("pria")) {
            bmrResult = 66 + (13.7f * beratBadan) + (5f * tinggiBadan) - (6.8f * usia);
        } else {
            bmrResult = 655 + (9.6f * beratBadan) + (1.8f * tinggiBadan) - (4.7f * usia);
        }

        if (idAktivitas == 0) {
            bmrResult *= 1.2f;
        } else if (idAktivitas == 1) {
            bmrResult *= 1.375f;
        } else if (idAktivitas == 2) {
            bmrResult *= 1.55f;
        } else if (idAktivitas == 3) {
            bmrResult *= 1.725f;
        } else {
            bmrResult *= 1.9f;
        }

        return bmrResult;
    }

    private void AttentionDialog(View mView, String title, CharSequence message) {
        new AlertDialog.Builder(mView.getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }
    private void BmrDialog(View mView, String title, CharSequence message, final float bmrResult) {
        new AlertDialog.Builder(mView.getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                        mEditor.putFloat(KEY_BMR,bmrResult);
                        mEditor.putFloat(KEY_MIN,bmrResult);

                        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        mEditor.putString(KEY_DATE ,date);
                        mEditor.apply();

                        logCaloriesViewModel.deleteAll();

                        FragmentTransaction fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentManager.replace(R.id.fragment_container,new LogCaloriesFragment())
                                .addToBackStack(null)
                                .commit();
                    }
                })
                .setNegativeButton("Hitung Ulang", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    private boolean validation(String beratBadan, String tinggiBadan, String usia) {
        if ((beratBadan.trim().length() == 0) || (tinggiBadan.trim().length() == 0) || (usia.trim().length() == 0)) {
            return false;
        } else return true;
    }


}
