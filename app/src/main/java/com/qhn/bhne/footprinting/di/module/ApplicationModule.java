package com.qhn.bhne.footprinting.di.module;

import android.content.Context;


import com.qhn.bhne.footprinting.di.scope.ContextLife;
import com.qhn.bhne.footprinting.di.scope.PerApp;
import com.qhn.bhne.footprinting.mvp.App;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qhn
 * on 2016/10/27 0027.
 */
@Module
public class ApplicationModule {
    private App mApplication;

    public ApplicationModule(App mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @PerApp
    @ContextLife//返回的对象为单例并且生命周期与context相同，且该context的类型为Application类型
    public Context provideApplicationContext(){
        return  mApplication.getApplicationContext();
    }
}
