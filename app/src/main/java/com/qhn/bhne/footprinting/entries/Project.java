package com.qhn.bhne.footprinting.entries;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by qhn
 * on 2016/11/15 0015.
 */
@Entity(nameInDb = "PROJECT")
public class Project {
    @Id(autoincrement = true)
    private Long projectId;

    @NotNull
    private String name;//项目名称

    @Unique
    @NotNull
    private String userName;//用户名称

    private String category;//项目类别
    private int definition;//定义号
    private int batch;//批次
    private String remark;//备注
    private String date;//创建日期
    private String describe;



    @Keep
    public Project(Long projectId, @NotNull String name,@NotNull String userName ,String category,
            int definition, int batch, String remark, String date,String describe) {
        this.projectId = projectId;
        this.name = name;
        this.category = category;
        this.definition = definition;
        this.batch = batch;
        this.remark = remark;
        this.date = date;
        this.describe=describe;
        this.userName=userName;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
