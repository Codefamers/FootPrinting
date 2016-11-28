package com.qhn.bhne.footprinting.mvp.interactor;

import com.qhn.bhne.footprinting.mvp.entries.Project;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by qhn
 * on 2016/11/26 0026.
 */

public interface ProjectInteractor {
     void delete (long itemId);


     boolean add(Project project);

     Long insert(Project project);
     void update(Project project);

     List<Project> queryList();
     Project queryUnique(long id);
     Project queryUnique(String name);
}
