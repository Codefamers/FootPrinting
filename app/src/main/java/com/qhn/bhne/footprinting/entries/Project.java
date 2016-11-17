package com.qhn.bhne.footprinting.entries;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qhn
 * on 2016/11/15 0015.
 */
@Entity(nameInDb = "PROJECT")
public class Project {
    @Id(autoincrement = true)
    private Long projectId;

    @NotNull
    private String name;

    @NotNull
    private String category;

    private int definition;//定义号
    private int batch;//批次
    private String remark;//备注
    private Date date;

    @Generated(hash = 2023325387)
    public Project(Long projectId, @NotNull String name, @NotNull String category,
            int definition, int batch, String remark, Date date) {
        this.projectId = projectId;
        this.name = name;
        this.category = category;
        this.definition = definition;
        this.batch = batch;
        this.remark = remark;
        this.date = date;
    }

    @Generated(hash = 1767516619)
    public Project() {
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
