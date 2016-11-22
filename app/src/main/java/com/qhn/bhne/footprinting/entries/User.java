package com.qhn.bhne.footprinting.entries;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by qhn
 * on 2016/11/16 0016.
 */
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;

    @Unique
    @NotNull
    private String name;//账户

    @NotNull
    private String password;//密码


    private String headImage;//头像


    private String createTime;//创建时间

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }
    @Generated(hash = 37316966)
    public User(Long id, @NotNull String name, @NotNull String password,
            String headImage, String createTime) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.headImage = headImage;
        this.createTime = createTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadImage() {
        return this.headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
