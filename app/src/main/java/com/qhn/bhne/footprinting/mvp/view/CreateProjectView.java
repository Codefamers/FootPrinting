package com.qhn.bhne.footprinting.mvp.view;

import com.qhn.bhne.footprinting.mvp.entries.Construction;
import com.qhn.bhne.footprinting.mvp.entries.Project;

/**
 * Created by qhn
 * on 2016/11/28 0028.
 */

public interface CreateProjectView extends BaseView {
    void getViewString();
    void showProjectDetails(Project project);
    void showConstDetails(Construction construction);
    void showSpotDetails();


    void returnResult();
}
