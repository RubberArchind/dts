package id.primadev.recyclerview.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import id.primadev.recyclerview.MainActivity;
import id.primadev.recyclerview.R;

import static id.primadev.recyclerview.Constant.USERNAME_KEY;


public class SuccessLogin extends Fragment {
    TextView welcomeMsg;
    Button btnLogout;
    private SharedPreferences sharedPreferences;
    public SuccessLogin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_success_login, container, false);
        this.sharedPreferences = getActivity().getSharedPreferences("user_sharedpreference", Context.MODE_PRIVATE);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(USERNAME_KEY);
                editor.apply();
                ((MainActivity) getActivity()).loadFragment("acc",new AccountFragment());
                Toast.makeText(getActivity(),"Logout Success",Toast.LENGTH_SHORT).show();
            }
        });
        String username = sharedPreferences.getString(USERNAME_KEY,null);
        welcomeMsg = view.findViewById(R.id.txtSuccess);
        if(username != null) {
            welcomeMsg.setText("Welcome " +username);
        }
//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
////                Log.i("KeyPressed", "keyCode: " + keyCode);
//                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
//                        getActivity().finish();
//                    return true;
//                }
//                return false;
//            }
//        });
    }


}
