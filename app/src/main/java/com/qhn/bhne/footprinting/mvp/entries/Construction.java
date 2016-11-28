package com.qhn.bhne.footprinting.mvp.entries;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Unique;

import com.qhn.bhne.footprinting.db.DaoSession;
import com.qhn.bhne.footprinting.db.FileContentDao;
import com.qhn.bhne.footprinting.db.ConstructionDao;
import com.qhn.bhne.footprinting.interfaces.Constants;

/**
 * Created by qhn
 * on 2016/11/15 0015.
 */
@Entity
public class Construction {
    @Id(autoincrement = true)
    private Long id;//工程id

    @NotNull
    private Long projectId;//父节点id

    @Unique
    private Long childId;//子类节点



    @Index(unique = true)
    @NotNull
    private String name;//工程名称

    @NotNull
    private String category;//类别

    @NotNull
    private String userName;//用户名称
    @NotNull
    private String profession;//专业
    @NotNull
    private String voltageClass;//电压等级
    @ToMany(joinProperties = {@JoinProperty(name = "childId", referencedName = "parentID")})
    @OrderBy("date ASC")
    private List<FileContent> fileContentList;

    private int constructionMax= Constants.PROJECT_MAX;

    public int getConstructionMax() {
        return constructionMax;
    }

    public void setConstructionMax(int constructionMax) {
        this.constructionMax = constructionMax;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }
    private String remark;//备注
    private String date;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1539718505)
    private transient ConstructionDao myDao;

    public Construction() {
    }

    public Construction(Long id) {
        this.id = id;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 28055427)
    public List<FileContent> getFileContentList() {
        if (fileContentList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FileContentDao targetDao = daoSession.getFileContentDao();
            List<FileContent> fileContentListNew = targetDao._queryConstruction_FileContentList(childId);
            synchronized (this) {
                if (fileContentList == null) {
                    fileContentList = fileContentListNew;
                }
            }
        }
        return fileContentList;
    }

    public Construction(Long id, Long projectId, Long childId, String name, String category, String userName, String profession, String voltageClass, String date) {
        this.id = id;
        this.projectId = projectId;
        this.childId = childId;
        this.name = name;
        this.category = category;
        this.userName = userName;
        this.profession = profession;
        this.voltageClass = voltageClass;
        this.date = date;
    }

    @Generated(hash = 1100589033)
    public Construction(Long id, @NotNull Long projectId, Long childId, @NotNull String name, @NotNull String category, @NotNull String userName,
            @NotNull String profession, @NotNull String voltageClass, int constructionMax, String remark, String date) {
        this.id = id;
        this.projectId = projectId;
        this.childId = childId;
        this.name = name;
        this.category = category;
        this.userName = userName;
        this.profession = profession;
        this.voltageClass = voltageClass;
        this.constructionMax = constructionMax;
        this.remark = remark;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getVoltageClass() {
        return voltageClass;
    }

    public void setVoltageClass(String voltageClass) {
        this.voltageClass = voltageClass;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1191791643)
    public synchronized void resetFileContentList() {
        fileContentList = null;
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
    @Generated(hash = 996948775)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getConstructionDao() : null;
    }
}
