package com.qhn.bhne.footprinting.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;


import com.qhn.bhne.footprinting.di.scope.ContextLife;
import com.qhn.bhne.footprinting.di.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qhn
 * on 2016/10/27 0027.
 */
@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @PerFragment
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return fragment.getActivity();
    }

    @Provides
    @PerFragment
    public Activity provideActivity() {
        return fragment.getActivity();
    }

    @Provides
    @PerFragment
    public Fragment provideFragment() {
        return fragment;
    }

}
