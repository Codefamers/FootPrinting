package com.qhn.bhne.footprinting.di.module;

import com.qhn.bhne.footprinting.db.ConstructionDao;
import com.qhn.bhne.footprinting.db.DaoSession;
import com.qhn.bhne.footprinting.db.FileContentDao;
import com.qhn.bhne.footprinting.db.ProjectDao;
import com.qhn.bhne.footprinting.db.UserDao;
import com.qhn.bhne.footprinting.mvp.entries.User;

import org.greenrobot.greendao.query.QueryBuilder;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qhn
 * on 2016/11/26 0026.
 */
@Module
public class EntityModule {
    private DaoSession daoSession;
    private String userName;
    private String password;

    public EntityModule(DaoSession daoSession, String userName, String password) {
        this.daoSession = daoSession;
        this.userName=userName;
        this.password=password;
    }


    @Provides
    public ProjectDao ProvideProjectDao() {
        return daoSession.getProjectDao();
    }


    @Provides
    public ConstructionDao ProvideConstDao() {
        return daoSession.getConstructionDao();
    }


    @Provides
    public UserDao ProvidesUserDao() {
        return daoSession.getUserDao();
    }


    @Provides
    public FileContentDao ProvideFileContentDao() {
        return daoSession.getFileContentDao();
    }


    @Provides
    public User provideUser(UserDao userDao) {
        QueryBuilder<User> queryBuilder = userDao
                .queryBuilder()
                .where(UserDao.Properties.Name.eq(userName), UserDao.Properties.Password.eq(password));
        return queryBuilder.unique();
    }
}
