package com.qhn.bhne.footprinting.mvp.entries;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by qhn
 * on 2016/11/28 0028.
 */
@Entity
public class Spot {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private Long parentID;//工程id


    @NotNull
    private String date;


    @Generated(hash = 1255746944)
    public Spot(Long id, @NotNull Long parentID, @NotNull String date) {
        this.id = id;
        this.parentID = parentID;
        this.date = date;
    }

    @Generated(hash = 817251004)
    public Spot() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentID() {
        return parentID;
    }

    public void setParentID(Long parentID) {
        this.parentID = parentID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
