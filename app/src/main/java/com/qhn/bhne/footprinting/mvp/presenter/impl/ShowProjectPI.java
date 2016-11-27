package com.qhn.bhne.footprinting.mvp.presenter.impl;

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

    }


    @Override
    public void onDestroy() {

    }

    @Override
    public List<Project> queryProjectList() {
        //mView.refreshView(projectInteractor.queryList());
        return projectInteractor.queryList();
    }

    private List<Construction> queryConstList(Project project) {
       /* Long projectId = project.getId();
        Query<ConstructionInteractor> constructionQuery = constructionDao
                .queryBuilder()
                .where(ConstructionDao.Properties.ParentID.eq(projectId * Constants.PROJECT_MAX))
                .build();
        return constructionQuery.list();*/
        return null;
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
    public void deleteItem(int itemCategory, long itemID) {
        switch (itemCategory) {
            case ShowProjectActivity.CREATE_CONSTRUCTION:
                projectInteractor.delete(itemID);

                break;
            case ShowProjectActivity.CREATE_FILE:
                constructionInte.delete(itemID);
                //constDao.delete(queryConstUnique(itemID));
                //refreshExpandProjectList();

                break;
        }
    }

    @Override
    public void refreshData() {
        mView.refreshView(queryProjectList());
    }
    /*private boolean insertFileContent(FileContent fileContent) {
       *//* try {

            fileContentDao.save(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "创建失败该名称已被注册", Toast.LENGTH_SHORT).show();
            return false;
        }*//*
        return true;
    }
    //验证工程名是否重复
    private boolean verifyIsRepeat(String projectName, int createCategory) {
       *//* if (createCategory == CREATE_PROJECT) {
            Query<Project> projectQuery = projectDao
                    .queryBuilder()
                    .where(ProjectDao.Properties.Name.eq(projectName), ProjectDao.Properties.UserName.eq("123456"))
                    .build();
            if (projectQuery.unique() != null) {
                return true;
            } else
                return false;
        } else {
            Query<ConstructionInteractor> constructionQuery = constDao
                    .queryBuilder()
                    .where(ConstructionDao.Properties.Name.eq(projectName), ConstructionDao.Properties.UserName.eq("123456"))
                    .build();
            if (constructionQuery.unique() != null) {
                return true;
            } else
                return false;
        }*//*
        return false;
    }*/

    @Override
    public boolean verifyRepeatName(int itemCategory, String itemName, long parentID) {
        switch (itemCategory) {
            case ShowProjectActivity.CREATE_PROJECT:

                return !(projectInteractor.queryUnique(itemName) == null);
            case ShowProjectActivity.CREATE_CONSTRUCTION:
                return !(constructionInte.queryUnique(itemName, parentID) == null);
            case ShowProjectActivity.CREATE_FILE:
                return !(fileIntel.queryUnique(itemName, parentID) == null);


        }
        return false;
    }


}
