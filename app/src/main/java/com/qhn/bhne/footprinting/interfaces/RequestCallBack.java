package com.qhn.bhne.footprinting.interfaces;

/**
 * Created by qhn
 * on 2016/11/26 0026.
 */

public interface RequestCallBack<T> {
    void onBefore();
    void onSuccess(T data);
    void onError(String errorMessage);
}
