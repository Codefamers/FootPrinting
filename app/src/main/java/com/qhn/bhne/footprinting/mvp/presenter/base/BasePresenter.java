package com.qhn.bhne.footprinting.mvp.presenter.base;

import android.support.annotation.NonNull;
import android.view.View;

import com.qhn.bhne.footprinting.mvp.view.BaseView;

/**
 * Created by qhn
 * on 2016/11/25 0025.
 */

public interface  BasePresenter {
    void create();
    void attachView(@NonNull BaseView view);
    void onDestroy();
}
