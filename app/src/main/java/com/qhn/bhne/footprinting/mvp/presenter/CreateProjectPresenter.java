package com.qhn.bhne.footprinting.mvp.presenter;

import com.qhn.bhne.footprinting.mvp.entries.Construction;
import com.qhn.bhne.footprinting.mvp.entries.Project;
import com.qhn.bhne.footprinting.mvp.entries.Spot;

/**
 * Created by qhn
 * on 2016/11/28 0028.
 */

public interface CreateProjectPresenter {
    Long insertProject(Project project);
    Long insertConstruction(Construction construction);
    Long insertSpot(Spot spot);




    void updateProject(Project project);

    void updateConst(Construction construction);

    void lookProjectInfo(long itemID);

    void lookConstructionInfo(long parentID, long itemID);

    void createProject(Project project);

    void createConstruction(Construction construction);
}
