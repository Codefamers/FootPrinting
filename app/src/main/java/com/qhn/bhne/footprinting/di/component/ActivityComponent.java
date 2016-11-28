package com.qhn.bhne.footprinting.di.component;

import android.app.Activity;
import android.content.Context;


import com.qhn.bhne.footprinting.di.module.ActivityModule;
import com.qhn.bhne.footprinting.di.scope.ContextLife;
import com.qhn.bhne.footprinting.di.scope.PerActivity;
import com.qhn.bhne.footprinting.mvp.ui.activities.CreateProjectActivity;
import com.qhn.bhne.footprinting.mvp.ui.activities.MapActivity;
import com.qhn.bhne.footprinting.mvp.ui.activities.ShowProjectActivity;

import dagger.Component;

/**
 * Created by qhn
 * on 2016/10/26 0026.
 */
@PerActivity//如果component中依赖的有其他Component注入的时候也要将该component的module实例化
@Component(dependencies = ApplicationComponent.class,modules =ActivityModule.class )
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(ShowProjectActivity mainActivity);
    void inject(MapActivity mapActivity);
    void inject(CreateProjectActivity createProjectActivity);
  /*

    void inject(NewsChannelActivity newsChannelActivity);

    void inject(NewsPhotoDetailActivity newsPhotoDetailActivity);

    void inject(PhotoActivity photoActivity);

    void inject(PhotoDetailActivity photoDetailActivity);*/
}
