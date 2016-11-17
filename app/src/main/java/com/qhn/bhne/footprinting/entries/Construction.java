package com.qhn.bhne.footprinting.entries;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * Created by qhn
 * on 2016/11/15 0015.
 */

public class Construction {
    @Id(autoincrement = true)
    private Long constructionId;//工程id

    @NotNull
    private Long projectId;//项目id

    @NotNull
    private String name;//工程名称

    @NotNull
    private String category;//类别

    private String profession;//专业
    private String voltageClass;//批次
    private String remark;//备注
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
