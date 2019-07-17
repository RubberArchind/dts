package dts.id.caloriesdatabase.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;

import dts.id.caloriesdatabase.R;
import dts.id.caloriesdatabase.ui.CalculateBMRFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CalculateBMRFragment calculateBMRFragment = new CalculateBMRFragment();
        FragmentTransaction fragmentTransaction =  this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, calculateBMRFragment)
                .addToBackStack(null)
                .commit();
    }
}
