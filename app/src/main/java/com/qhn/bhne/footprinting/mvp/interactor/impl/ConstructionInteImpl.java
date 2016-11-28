package com.qhn.bhne.footprinting.mvp.interactor.impl;

import android.database.sqlite.SQLiteConstraintException;

import com.qhn.bhne.footprinting.db.ConstructionDao;
import com.qhn.bhne.footprinting.mvp.entries.Construction;
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
    public void delete(long parentId,long itemId) {
        constructionDao.delete(queryUnique(parentId,itemId));

    }

    @Override
    public boolean add(Construction construction) {
        try {
            constructionDao.insert(construction);
            return true;
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Long insert(Construction construction) {
        return constructionDao.insert(construction);
    }

    @Override
    public void update(Construction construction) {

        constructionDao.update(construction);
    }

    @Override
    public List<Construction> queryList() {
        Query<Construction> query=constructionDao
                .queryBuilder()
                .build();
        return query.list();
    }

    @Override
    public Construction queryUnique(Long childID, long id) {
        Query<Construction> query=constructionDao
                .queryBuilder()
                .where(ConstructionDao.Properties.Id.eq(id))
                .build();
        return query.unique();
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
