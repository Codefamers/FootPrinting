package com.qhn.bhne.footprinting.mvp.view;

import com.qhn.bhne.footprinting.mvp.entries.Project;

import java.util.List;

/**
 * Created by qhn
 * on 2016/11/26 0026.
 */

public interface ShowProjectView extends BaseView {
    //void fromOthersBack();
    void refreshView(List<Project> list);
    void showRepeatIcon(Boolean isShowIcon);
    void createdFile(String fileName);

    void cancelDialog();
}
