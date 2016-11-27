package com.qhn.bhne.footprinting.mvp.presenter.base;

import android.support.annotation.NonNull;
import android.view.View;

import com.qhn.bhne.footprinting.interfaces.RequestCallBack;
import com.qhn.bhne.footprinting.mvp.view.BaseView;

/**
 * Created by qhn
 * on 2016/11/25 0025.
 */

public class BasePresenterImpl<T extends BaseView,E> implements BasePresenter,RequestCallBack<T> {
    protected T mView;

    public T getmView() {
        return mView;
    }


    @Override
    public void create() {

    }

    @Override
    public void attachView(@NonNull BaseView view) {
        mView= (T) view;
    }



    @Override
    public void onDestroy() {

    }

    @Override
    public void onBefore() {

    }

    @Override
    public void onSuccess(T data) {

    }

    @Override
    public void onError(String errorMessage) {
        mView.showErrorMessage(errorMessage);
    }
}
