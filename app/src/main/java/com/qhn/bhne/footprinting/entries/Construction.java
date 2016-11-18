package com.qhn.bhne.footprinting.entries;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qhn
 * on 2016/11/15 0015.
 */
@Entity
public class Construction {
    @Id(autoincrement = true)
    private Long constructionId;//工程id

    @NotNull
    private Long projectId;//项目id

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
    @Keep
    public Construction(Long constructionId, Long projectId, @NotNull String userName,String name, String category,
                        String profession, String voltageClass, String remark, String date) {
        this.constructionId = constructionId;
        this.projectId = projectId;
        this.name = name;
        this.category = category;
        this.profession = profession;
        this.voltageClass = voltageClass;
        this.remark = remark;
        this.date = date;
        this.userName=userName;
    }

    public Long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(Long constructionId) {
        this.constructionId = constructionId;
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
}
