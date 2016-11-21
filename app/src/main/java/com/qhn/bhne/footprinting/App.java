package com.qhn.bhne.footprinting;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.qhn.bhne.footprinting.db.DaoMaster;
import com.qhn.bhne.footprinting.db.DaoSession;
import com.qhn.bhne.footprinting.entries.User;

import org.greenrobot.greendao.database.Database;


/**
 * Created by qhn
 * on 2016/11/16 0016.
 */

public class App extends Application {
    public static final boolean ENCRYPTED=true;
    private DaoSession daoSession;
    public static Context appContext;

    private  String userName;
    private String userPassword;

    public SharedPreferences getSharedPre() {
        return sharedPre;
    }

    public void setSharedPre(SharedPreferences sharedPre) {
        this.sharedPre = sharedPre;
    }

    private SharedPreferences sharedPre;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private  User user;
    public  User getUser() {
        return user;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public  void  setUser(User user) {
        this.user = user;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=this;
        initDB();
        initUser();

    }

    private void initUser() {
        sharedPre=getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);

        userName=sharedPre.getString("USER_NAME",null);
        userPassword=sharedPre.getString("USER_PASSWORD",null);
    }

    private void initDB() {
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(this,
                ENCRYPTED?"notes-db-encrypted":"notes-db");
        Database db=ENCRYPTED?helper.getEncryptedWritableDb("super-secret"):helper.getWritableDb();
        daoSession=new DaoMaster(db).newSession();
    }
}
