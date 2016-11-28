package com.qhn.bhne.footprinting.mvp.presenter.impl;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.qhn.bhne.footprinting.mvp.entries.Construction;
import com.qhn.bhne.footprinting.mvp.entries.FileContent;
import com.qhn.bhne.footprinting.mvp.entries.Project;
import com.qhn.bhne.footprinting.mvp.interactor.impl.ConstructionInteImpl;
import com.qhn.bhne.footprinting.mvp.interactor.impl.FileIntelImpl;
import com.qhn.bhne.footprinting.mvp.interactor.impl.ProjectInteImpl;
import com.qhn.bhne.footprinting.mvp.presenter.ShowProjectPresenter;
import com.qhn.bhne.footprinting.mvp.presenter.base.BasePresenterImpl;
import com.qhn.bhne.footprinting.mvp.ui.activities.ShowProjectActivity;
import com.qhn.bhne.footprinting.mvp.view.ShowProjectView;
import com.qhn.bhne.footprinting.utils.MyUtils;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;


/**
 * Created by qhn
 * on 2016/11/26 0026.
 */

public class ShowProjectPI extends BasePresenterImpl<ShowProjectView, Project> implements ShowProjectPresenter {


    private ProjectInteImpl projectInteractor;
    private ConstructionInteImpl constructionInte;
    private FileIntelImpl fileIntel;

    @Inject
    public ShowProjectPI(ProjectInteImpl projectInteractor, ConstructionInteImpl constructionInte, FileIntelImpl fileIntel) {
        this.projectInteractor = projectInteractor;
        this.constructionInte = constructionInte;
        this.fileIntel = fileIntel;
    }

    @Override
    public void create() {
        for (Construction construction : constructionInte.queryList()) {
            KLog.d("construction"+construction.getChildId());
        }
        for (FileContent fileContent : fileIntel.queryList()) {
            KLog.d("fileContent"+fileContent.getParentID());
        }
    }


    @Override
    public void onDestroy() {

    }

    @Override
    public List<Project> queryProjectList() {
        //mView.refreshView(projectInteractor.queryList());
        return projectInteractor.queryList();
    }



    @Override
    public void addProject() {

    }

    @Override
    public void addConst() {

    }

    @Override
    public boolean addFile(FileContent fileContent) {

        return fileIntel.add(fileContent);
    }

    @Override
    public void deleteItem(int itemCategory, long parentID,long itemID) {
        switch (itemCategory) {
            case ShowProjectActivity.CREATE_CONSTRUCTION:
                projectInteractor.delete(itemID);

                break;
            case ShowProjectActivity.CREATE_FILE:
                constructionInte.delete(parentID,itemID);

                break;
        }
    }

    @Override
    public void refreshData() {

        mView.refreshView(queryProjectList());
    }


    @Override
    public boolean verifyRepeatName(int itemCategory, String itemName, long parentID) {
        boolean isPass=false;
        if (TextUtils.isEmpty(itemName)) {
            mView.showErrorMessage("请输入名称");
            mView.showRepeatIcon(false);
            return false;
        }
        switch (itemCategory) {
            case ShowProjectActivity.CREATE_PROJECT:

                if (projectInteractor.queryUnique(itemName) != null) {
                    mView.showErrorMessage("该项目名已存在");
                    mView.showRepeatIcon(false);
                    isPass=false;
                }else{
                    mView.showRepeatIcon(true);
                    isPass=true;

                }
                break;
            case ShowProjectActivity.CREATE_CONSTRUCTION:
                if ((constructionInte.queryUnique(itemName, parentID) != null)) {
                    mView.showErrorMessage("该工程名已存在");
                    mView.showRepeatIcon(false);
                    isPass=false;
                }else {
                    mView.showRepeatIcon(true);
                    isPass=true;
                }

                break;
            case ShowProjectActivity.CREATE_FILE:
                if ((fileIntel.queryUnique(itemName, parentID) != null)) {
                    mView.showErrorMessage("该文件已存在");
                    mView.showRepeatIcon(false);
                    isPass=false;
                }else{
                    mView.showRepeatIcon(true);
                    mView.createdFile(itemName);
                    refreshData();
                    isPass=false;
                }

                break;


        }
        return isPass;
    }

    @Override
    public void createFile(FileContent fileContent) {
        Long id =fileIntel.insert(fileContent);
        fileContent.setChildId(id * fileContent.getFileMax());
        fileIntel.update(fileContent);
        mView.cancelDialog();
        refreshData();
    }


}
