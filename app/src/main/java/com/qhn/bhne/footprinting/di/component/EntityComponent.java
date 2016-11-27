package com.qhn.bhne.footprinting.di.component;

import com.qhn.bhne.footprinting.di.module.EntityModule;
import com.qhn.bhne.footprinting.di.scope.PerActivity;
import com.qhn.bhne.footprinting.mvp.presenter.impl.ShowProjectPI;

import javax.inject.Scope;

import dagger.Component;

/**
 * Created by qhn
 * on 2016/11/26 0026.
 */

@Component(modules = EntityModule.class)
public interface EntityComponent {
    void inject(ShowProjectPI showProjectPI);
}
