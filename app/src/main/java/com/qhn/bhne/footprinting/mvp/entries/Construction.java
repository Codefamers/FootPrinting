package com.qhn.bhne.footprinting.mvp.entries;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by qhn
 * on 2016/11/15 0015.
 */
@Entity
public class Construction {
    @Id(autoincrement = true)
    private Long constructionId;//工程id

    @NotNull
    private Long parentID;//父节点id

    @Index(unique = true)
    @NotNull
    private String name;//工程名称

    @NotNull
    private String category;//类别

    @NotNull
    private String userName;//用户名称
    private String profession;//专业
    private String voltageClass;//电压等级
    private String remark;//备注
    private String date;
    public Construction() {
    }

    public Construction(Long id) {
        this.constructionId = id;
    }
    @Generated(hash = 2033462558)
    public Construction(Long constructionId, @NotNull Long parentID, @NotNull String name, @NotNull String category,
            @NotNull String userName, String profession, String voltageClass, String remark, String date) {
        this.constructionId = constructionId;
        this.parentID = parentID;
        this.name = name;
        this.category = category;
        this.userName = userName;
        this.profession = profession;
        this.voltageClass = voltageClass;
        this.remark = remark;
        this.date = date;
    }

    public Long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(Long constructionId) {
        this.constructionId = constructionId;
    }

    public Long getParentID() {
        return parentID;
    }

    public void setParentID(Long parentID) {
        this.parentID = parentID;
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
}
