package com.qhn.bhne.footprinting.di.module;

import android.app.Activity;
import android.content.Context;


import com.qhn.bhne.footprinting.db.ConstructionDao;
import com.qhn.bhne.footprinting.db.DaoSession;
import com.qhn.bhne.footprinting.db.FileContentDao;
import com.qhn.bhne.footprinting.db.ProjectDao;
import com.qhn.bhne.footprinting.db.SpotDao;
import com.qhn.bhne.footprinting.db.UserDao;
import com.qhn.bhne.footprinting.di.scope.ContextLife;
import com.qhn.bhne.footprinting.di.scope.PerActivity;
import com.qhn.bhne.footprinting.mvp.App;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qhn
 * on 2016/10/26 0026.*/
//http://gold.xitu.io/entry/578cf2612e958a00543c45a4
//命名规则provider+任意字符,构造参数寻找规则是根据返回值来确定的，当俩个返回值相同时使用@Qulifiier来
// 区分同一种类型的对象但是属性不相同,@Scope作用局部单例

@Module
public class ActivityModule {
    private Activity activity;
    private DaoSession daoSession;
    //private User user;

    public ActivityModule(Activity activity) {
        this.activity = activity;
        daoSession= ((App) activity.getApplication()).getDaoSession();
        //this.user=user;
    }

    @Provides
    @PerActivity//生命周期与activity相同并且单例
    @ContextLife("Activity")//context的类型为activity
    public Context ProvideActivityContext() {
        return activity;
    }

    @Provides
    @PerActivity//生命周期与activity相同并且单例
    public Activity ProvideActivity() {
        return activity;
    }


    @Provides
    public ProjectDao ProvideProjectDao() {
        return daoSession.getProjectDao();
    }


    @Provides
    public ConstructionDao ProvideConstructionDao() {
        return daoSession.getConstructionDao();
    }


    @Provides
    public UserDao ProvidesUserDao() {
        return daoSession.getUserDao();
    }


    @Provides
    public FileContentDao ProvideFileContentDao() {
        return daoSession.getFileContentDao();
    }
    @Provides
    public SpotDao ProvideSpotDao() {
        return daoSession.getSpotDao();
    }

/*
    @Provides
    public User provideUser(UserDao userDao) {

        return e;
    }*/
}
