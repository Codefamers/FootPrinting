package com.qhn.bhne.footprinting.mvp.presenter;

import com.qhn.bhne.footprinting.mvp.entries.FileContent;
import com.qhn.bhne.footprinting.mvp.entries.Project;

import java.util.List;

/**
 * Created by qhn
 * on 2016/11/26 0026.
 */

public interface ShowProjectPresenter {
    List<Project> queryProjectList();
    void addProject();
    void addConst();
    boolean addFile(FileContent fileContent);
    void deleteItem(int itemCategory, long itemID);
    void refreshData();
    boolean verifyRepeatName(int itemCategory,String itemName,long itemID);
}
