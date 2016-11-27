package com.qhn.bhne.footprinting.mvp.entries;

import com.qhn.bhne.footprinting.interfaces.Constants;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Unique;

import com.qhn.bhne.footprinting.db.DaoSession;
import com.qhn.bhne.footprinting.db.ConstructionDao;
import com.qhn.bhne.footprinting.db.ProjectDao;

/**
 * Created by qhn
 * on 2016/11/15 0015.
 */
@Entity(active = true)
public class Project {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String name;//项目名称

    @NotNull
    private String userName;//用户名称


    @ToMany(joinProperties =
            {@JoinProperty(name="childId",referencedName = "projectId")})
    @OrderBy("date ASC")
    private List<Construction> constructionList;
    @Unique
    private Long childId;



    private String category;//项目类别
    private int definition;//定义号
    private int batch;//批次
    private String remark;//备注
    private String date;//创建日期
    private String describe;

    private int projectMax=Constants.PROJECT_MAX;
    
   /* private Long parentID;//工程id

    public Long getId() {
        return parentID;
    }

    public void setId(Long parentID) {
        this.parentID = parentID;
    }*/

    public int getProjectMax() {
        return projectMax;
    }

    public void setProjectMax(int projectMax) {
        this.projectMax = projectMax;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1378029107)
    private transient ProjectDao myDao;

    public Project(Long id, String name, String userName,Long childId) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.childId=childId;
        
    }

    public String getDescribe() {
        return describe;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Generated(hash = 1767516619)
    public Project() {
    }

    @Generated(hash = 1970870098)
    public Project(Long id, @NotNull String name, @NotNull String userName, Long childId,
            String category, int definition, int batch, String remark, String date, String describe,
            int projectMax) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.childId = childId;
        this.category = category;
        this.definition = definition;
        this.batch = batch;
        this.remark = remark;
        this.date = date;
        this.describe = describe;
        this.projectMax = projectMax;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getDefinition() {
        return definition;
    }

    public void setDefinition(int definition) {
        this.definition = definition;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
    @Generated(hash = 2081800561)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProjectDao() : null;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1285069623)
    public synchronized void resetConstructionList() {
        constructionList = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 12512351)
    public List<Construction> getConstructionList() {
        if (constructionList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ConstructionDao targetDao = daoSession.getConstructionDao();
            List<Construction> constructionListNew = targetDao._queryProject_ConstructionList(childId);
            synchronized (this) {
                if (constructionList == null) {
                    constructionList = constructionListNew;
                }
            }
        }
        return constructionList;
    }


}
