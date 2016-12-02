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



    private String lastPosition;//上一个点的位置
    private int  number;//挡位编号
    @NotNull
    private String name;//点名称
    @NotNull
    private String type;//点类型

    private boolean isChecked;//是否被选择，默认为false
    private Double longitude;//经度
    private Double latitude;//纬度
    private String location;//踩点位置
    private float  distance;//距离
    private String remark;//备注
    @NotNull
    private String date;

    public Spot(Long id, Long parentID, int number, String type, String name) {
        this.id = id;
        this.parentID = parentID;
        this.number = number;
        this.type = type;
        this.name = name;
    }

    @Generated(hash = 817251004)
    public Spot() {
    }

    @Generated(hash = 1588316507)
    public Spot(Long id, @NotNull Long parentID, String lastPosition, int number,
            @NotNull String name, @NotNull String type, boolean isChecked,
            Double longitude, Double latitude, String location, float distance,
            String remark, @NotNull String date) {
        this.id = id;
        this.parentID = parentID;
        this.lastPosition = lastPosition;
        this.number = number;
        this.name = name;
        this.type = type;
        this.isChecked = isChecked;
        this.longitude = longitude;
        this.latitude = latitude;
        this.location = location;
        this.distance = distance;
        this.remark = remark;
        this.date = date;
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

    public String getLastPosition() {
        return this.lastPosition;
    }

    public void setLastPosition(String lastPosition) {
        this.lastPosition = lastPosition;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean getIsChecked() {
        return this.isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
