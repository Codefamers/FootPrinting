package com.qhn.bhne.footprinting.mvp.entries;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import com.qhn.bhne.footprinting.db.DaoSession;
import com.qhn.bhne.footprinting.db.FileContentDao;

import java.util.List;
import com.qhn.bhne.footprinting.db.SpotDao;

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

    @Unique
    private Long childId;

    @Index(unique = true)
    @NotNull
    private String fileName;//文件名称

    @NotNull
    private String userName;//用户id
    @NotNull
    private String date;
    private int FileMax;

    public int getFileMax() {
        return FileMax;
    }

    public void setFileMax(int fileMax) {
        FileMax = fileMax;
    }

    @ToMany(joinProperties = {@JoinProperty(name = "childId", referencedName = "parentID")})
    @OrderBy("date ASC")
    private List<Spot> spotList;

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 936139614)
    public List<Spot> getSpotList() {
        if (spotList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SpotDao targetDao = daoSession.getSpotDao();
            List<Spot> spotListNew = targetDao._queryFileContent_SpotList(childId);
            synchronized (this) {
                if (spotList == null) {
                    spotList = spotListNew;
                }
            }
        }
        return spotList;
    }

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

    public FileContent(Long fileID, Long parentID, Long childId, String fileName, String userName, String date) {
        this.fileID = fileID;
        this.parentID = parentID;
        this.childId = childId;
        this.fileName = fileName;
        this.userName = userName;
        this.date = date;
    }

    @Generated(hash = 213846452)
    public FileContent(Long fileID, @NotNull Long parentID, Long childId, @NotNull String fileName,
            @NotNull String userName, @NotNull String date, int FileMax) {
        this.fileID = fileID;
        this.parentID = parentID;
        this.childId = childId;
        this.fileName = fileName;
        this.userName = userName;
        this.date = date;
        this.FileMax = FileMax;
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

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1607954516)
    public synchronized void resetSpotList() {
        spotList = null;
    }
}
