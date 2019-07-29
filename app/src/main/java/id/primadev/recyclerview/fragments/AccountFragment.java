package id.primadev.recyclerview.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import id.primadev.recyclerview.Constant;
import id.primadev.recyclerview.MainActivity;
import id.primadev.recyclerview.R;
import id.primadev.recyclerview.UserRepository;
import id.primadev.recyclerview.database.entity.UserEntity;
import id.primadev.recyclerview.viewmodel.UserViewModel;

import static id.primadev.recyclerview.Constant.USERNAME_KEY;


public class AccountFragment extends Fragment {
    TextView txtSignup;
    Button btnLogin;
    EditText edtUsername, edtPassword;
    private UserViewModel userViewModel;
    private SharedPreferences sharedPreferences;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sharedPreferences = getActivity().getSharedPreferences("user_sharedpreference", Context.MODE_PRIVATE);
        if(sharedPreferences.getString(USERNAME_KEY,null)!=null){
            ((MainActivity) getActivity()).loadFragment("success",new SuccessLogin());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_account, container, false);
        userViewModel= ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        txtSignup = view.findViewById(R.id.txtSignup);
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).loadFragment("signup",new SignUpFragment());
            }
        });

        edtUsername = view.findViewById(R.id.edtUsername);
        edtPassword = view.findViewById(R.id.edtPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = edtUsername.getText().toString();
                final String password = edtPassword.getText().toString();
                LiveData<Integer> res =userViewModel.getOneUser(username,password);
                res.observe(getActivity(), new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        if (integer==1){
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(USERNAME_KEY,username);
                            editor.apply();
                            Toast.makeText(getActivity(),"Login Success",Toast.LENGTH_SHORT).show();
                            ((MainActivity) getActivity()).loadFragment("success",new SuccessLogin());
                        }else{
                            Toast.makeText(getActivity(),"Login Failed, Check Username and Password",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }


}
