package com.qhn.bhne.footprinting.mvp.interactor.impl;

import com.qhn.bhne.footprinting.db.ConstructionDao;
import com.qhn.bhne.footprinting.mvp.entries.Construction;
import com.qhn.bhne.footprinting.mvp.entries.Project;
import com.qhn.bhne.footprinting.mvp.interactor.ConstructionInteractor;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by qhn
 * on 2016/11/27 0027.
 */

public class ConstructionInteImpl implements ConstructionInteractor {
    private ConstructionDao constructionDao;
    @Inject
    public ConstructionInteImpl(ConstructionDao constructionDao) {
        this.constructionDao = constructionDao;
    }

    @Override
    public boolean delete(long itemId) {
        constructionDao.delete(queryUnique(itemId));
        return true;
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
    public List<Construction> queryList() {
        return null;
    }

    @Override
    public Construction queryUnique(long id) {
        return null;
    }

    @Override
    public Construction queryUnique(String name, long parentID) {
        Query<Construction> query=constructionDao
                .queryBuilder()
                .where(ConstructionDao.Properties.Name.eq(name)
                        ,ConstructionDao.Properties.ProjectId.eq(parentID))
                .build();
        return query.unique();
    }


}
