package id.primadev.recyclerview.fragments;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import id.primadev.recyclerview.MainActivity;
import id.primadev.recyclerview.R;
import id.primadev.recyclerview.UserRepository;
import id.primadev.recyclerview.database.entity.UserEntity;


public class SignUpFragment extends Fragment {
    Button btnSignup;
    EditText username,password;
    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        username = view.findViewById(R.id.edt_username);
        password = view.findViewById(R.id.edt_password);
        btnSignup = view.findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                UserRepository userRepository = new UserRepository(getActivity());
                String strUsername = username.getText().toString();
                String strPassword = password.getText().toString();
                userRepository.insertUserTask(strUsername,strPassword);
                userRepository.getAllUser().observe(getActivity(),
                        new Observer<List<UserEntity>>() {
                            @Override
                            public void onChanged(@Nullable List<UserEntity> userEntities) {
                                ((MainActivity) getActivity()).loadFragment("acc",new AccountFragment());
                                Toast.makeText(getActivity(),"Signup Succes",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}
