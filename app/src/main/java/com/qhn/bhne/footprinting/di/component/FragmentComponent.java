package com.qhn.bhne.footprinting.di.component;

import android.app.Activity;
import android.content.Context;


import com.qhn.bhne.footprinting.di.module.FragmentModule;
import com.qhn.bhne.footprinting.di.scope.ContextLife;
import com.qhn.bhne.footprinting.di.scope.PerFragment;

import dagger.Component;

/**
 * Created by qhn
 * on 2016/10/27 0027.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

   /* void inject(OnlineMusicFragment OnlineFragment);
    void inject(RecommendMusicFragment recommendFragment);
    void inject(SongMenuFragment songMenuFragment);
    void inject(MVListFragment mvListFragment);
    void inject(RankFragment rankFragment);*/
}
