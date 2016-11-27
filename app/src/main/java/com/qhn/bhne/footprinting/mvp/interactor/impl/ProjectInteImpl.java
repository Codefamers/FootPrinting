package com.qhn.bhne.footprinting.mvp.interactor.impl;

import com.qhn.bhne.footprinting.db.ProjectDao;
import com.qhn.bhne.footprinting.mvp.entries.Project;
import com.qhn.bhne.footprinting.mvp.interactor.ProjectInteractor;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by qhn
 * on 2016/11/26 0026.
 */

public class ProjectInteImpl implements ProjectInteractor {
    public ProjectDao projectDao;

    @Inject
    public ProjectInteImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public boolean delete(long id) {

        projectDao.delete(queryUnique(id));
        return false;
    }

    @Override
    public boolean add() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public List<Project> queryList() {
        QueryBuilder<Project> projectQueryBuilder = projectDao
                .queryBuilder()
                .where(ProjectDao.Properties.UserName.eq("123456"));
        Query<Project> query = projectQueryBuilder.build();
        return query.list();
    }

    @Override
    public Project queryUnique(long id) {
        Query<Project> query=projectDao.queryBuilder().where(ProjectDao.Properties.Id.eq(id)).build();
        return query.unique();
    }

    @Override
    public Project queryUnique(String name) {
        Query<Project> query=projectDao.queryBuilder().where(ProjectDao.Properties.Name.eq(name)).build();
        return query.unique();
    }

}
