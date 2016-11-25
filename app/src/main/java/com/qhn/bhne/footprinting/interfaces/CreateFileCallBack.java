package com.qhn.bhne.footprinting.interfaces;

import com.qhn.bhne.footprinting.mvp.entries.FileContent;

/**
 * Created by qhn
 * on 2016/11/21 0021.
 */

public interface CreateFileCallBack {
    void onSuccess(FileContent fileContent);
}
