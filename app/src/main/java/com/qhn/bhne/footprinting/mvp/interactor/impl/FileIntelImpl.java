package com.qhn.bhne.footprinting.mvp.interactor.impl;

import android.database.sqlite.SQLiteConstraintException;

import com.qhn.bhne.footprinting.db.FileContentDao;
import com.qhn.bhne.footprinting.mvp.entries.FileContent;
import com.qhn.bhne.footprinting.mvp.interactor.FileInteractor;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by qhn
 * on 2016/11/27 0027.
 */

public class FileIntelImpl implements FileInteractor {

    private FileContentDao fileContentDao;

    @Inject
    public FileIntelImpl(FileContentDao fileContentDao) {
        this.fileContentDao = fileContentDao;
    }

    @Override
    public boolean delete(long itemId) {
        return false;
    }

    @Override
    public boolean add(FileContent fileContent) {
        try {
            fileContentDao.insert(fileContent);
            return true;
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public List<FileContent> queryList() {
        return null;
    }

    @Override
    public FileContent queryUnique(long id) {
        return null;
    }

    @Override
    public FileContent queryUnique(String name, long parentID) {
        Query<FileContent> query = fileContentDao
                .queryBuilder()
                .where(FileContentDao.Properties.FileName.eq(name)
                        , FileContentDao.Properties.ParentID.eq(parentID))
                .build();
        return query.unique();
    }


}
