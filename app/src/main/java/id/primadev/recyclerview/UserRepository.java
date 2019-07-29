package id.primadev.recyclerview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import id.primadev.recyclerview.database.UserDatabase;
import id.primadev.recyclerview.database.dao.UserDao;
import id.primadev.recyclerview.database.entity.UserEntity;

public class UserRepository {
    private final UserDao userDao;

    public UserRepository(Context context){
        UserDatabase db = UserDatabase.getInstance(context);
        userDao = db.userDao();
    }


    public void insertUserTask(String username, String password) {
        insertUserTask(username, password, false);
    }

    public void insertUserTask(String username, String password, boolean encrypt) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userEntity.setEncrypt(encrypt);

//        if (encrypt){
//            userEntity.setPassword(AppUtils.generateHash(password));
//        }else userEntity.setPassword(null);

        insertUserTask(userEntity);
    }

    public void insertUserTask(final UserEntity userEntity) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                userDao.insertAll(userEntity);
                return null;
            }
        }.execute();
    }

    public LiveData<List<UserEntity>> getAllUser() {
        return userDao.getAllUser();
    }


    public LiveData<Integer> getOneUser(String username, String password){
        return userDao.getOneUser(username,password);
    }
}
