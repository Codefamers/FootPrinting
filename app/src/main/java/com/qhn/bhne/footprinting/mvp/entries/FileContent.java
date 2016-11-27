package com.qhn.bhne.footprinting.mvp.entries;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.DaoException;
import com.qhn.bhne.footprinting.db.DaoSession;
import com.qhn.bhne.footprinting.db.FileContentDao;

/**
 * Created by qhn
 * on 2016/11/21 0021.
 */
@Entity(active = true)
public class FileContent {
    @Id(autoincrement = true)
    private Long fileID;//主键id


    @NotNull
    private Long parentID;//工程id

    @Index(unique = true)
    @NotNull
    private String fileName;//文件名称

    @NotNull
    private String userName;//用户id
    @NotNull
    private String date;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;


    /** Used for active entity operations. */
    @Generated(hash = 352143221)
    private transient FileContentDao myDao;

    public FileContent() {
    }

    public FileContent(Long id) {
        this.fileID = id;
    }

    @Generated(hash = 464939423)
    public FileContent(Long fileID, @NotNull Long parentID, @NotNull String fileName,
            @NotNull String userName, @NotNull String date) {
        this.fileID = fileID;
        this.parentID = parentID;
        this.fileName = fileName;
        this.userName = userName;
        this.date = date;
    }

    

    public Long getFileID() {
        return this.fileID;
    }

    public void setFileID(Long fileID) {
        this.fileID = fileID;
    }

    public Long getParentID() {
        return this.parentID;
    }

    public void setParentID(Long parentID) {
        this.parentID = parentID;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 89691949)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFileContentDao() : null;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
