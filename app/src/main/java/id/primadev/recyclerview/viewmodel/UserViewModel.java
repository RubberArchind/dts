package id.primadev.recyclerview.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import id.primadev.recyclerview.UserRepository;
import id.primadev.recyclerview.database.entity.UserEntity;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<UserEntity>> allUser;
    private LiveData<Integer> getUser;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void insert(UserEntity userEntity){
        userRepository.insertUserTask(userEntity);
    }

    public LiveData<Integer> getOneUser(String username, String password){
        return getUser= userRepository.getOneUser(username,password);
    }

    public LiveData<List<UserEntity>> getAllUser(){
        return allUser= userRepository.getAllUser();
    }

}
