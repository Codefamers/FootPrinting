package com.qhn.bhne.footprinting.mvp.presenter.impl;

import com.qhn.bhne.footprinting.mvp.entries.Construction;
import com.qhn.bhne.footprinting.mvp.entries.Project;
import com.qhn.bhne.footprinting.mvp.entries.Spot;
import com.qhn.bhne.footprinting.mvp.interactor.impl.ConstructionInteImpl;
import com.qhn.bhne.footprinting.mvp.interactor.impl.FileIntelImpl;
import com.qhn.bhne.footprinting.mvp.interactor.impl.ProjectInteImpl;
import com.qhn.bhne.footprinting.mvp.interactor.impl.SpotIntelImpl;
import com.qhn.bhne.footprinting.mvp.presenter.CreateProjectPresenter;
import com.qhn.bhne.footprinting.mvp.presenter.base.BasePresenterImpl;
import com.qhn.bhne.footprinting.mvp.view.CreateProjectView;

import javax.inject.Inject;

/**
 * Created by qhn
 * on 2016/11/28 0028.
 */

public class CreateProjectPI extends BasePresenterImpl<CreateProjectView,Construction> implements CreateProjectPresenter{
    private ProjectInteImpl projectInteractor;
    private ConstructionInteImpl constructionInte;
    private SpotIntelImpl spotIntel;

    @Inject
    public CreateProjectPI(SpotIntelImpl spotIntel, ProjectInteImpl projectInteractor, ConstructionInteImpl constructionInte) {
        this.spotIntel = spotIntel;
        this.projectInteractor = projectInteractor;
        this.constructionInte = constructionInte;
    }


    @Override
    public Long insertProject(Project project) {
        return  projectInteractor.insert(project);
    }

    @Override
    public Long insertConstruction(Construction construction) {
        return constructionInte.insert(construction);
    }

    @Override
    public Long insertSpot(Spot spot) {
        return spotIntel.insert(spot);
    }





    @Override
    public void updateProject(Project project) {
        projectInteractor.update(project);
        mView.returnResult();
    }

    @Override
    public void updateConst(Construction construction) {
        constructionInte.update(construction);
        mView.returnResult();
    }

    @Override
    public void lookProjectInfo(long itemID) {
        mView.showProjectDetails(projectInteractor.queryUnique(itemID));
    }

    @Override
    public void lookConstructionInfo(long parentID, long itemID) {
        mView.showConstDetails(constructionInte.queryUnique(parentID,itemID));
    }

    @Override
    public void createProject(Project project) {
        Long id =insertProject(project);
        project.setChildId(id * project.getProjectMax());
        updateProject(project);
        mView.returnResult();
    }

    @Override
    public void createConstruction(Construction construction) {
        Long id =insertConstruction(construction);
        construction.setChildId(id * construction.getConstructionMax());
        updateConst(construction);
        mView.returnResult();
    }

    @Override
    public void createSpot(Spot spot) {
        spotIntel.save(spot);
        mView.returnResult();
    }

}
