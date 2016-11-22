package com.qhn.bhne.footprinting.entries;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by qhn
 * on 2016/11/21 0021.
 */
@Entity
public class FileContent {
    @Id(autoincrement = true)
    private Long fileID;//主键id


    @NotNull
    private Long parentID;//工程id

    @Index(unique = true)
    @NotNull
    private String fileName;//文件名称

    @NotNull
    private String userName;//用户id

    public FileContent() {
    }

    public FileContent(Long id) {
        this.fileID = id;
    }

    @Generated(hash = 431757488)
    public FileContent(Long fileID, @NotNull Long parentID, @NotNull String fileName, @NotNull String userName) {
        this.fileID = fileID;
        this.parentID = parentID;
        this.fileName = fileName;
        this.userName = userName;
    }

    public Long getFileID() {
        return this.fileID;
    }

    public void setFileID(Long fileID) {
        this.fileID = fileID;
    }

    public Long getParentID() {
        return this.parentID;
    }

    public void setParentID(Long parentID) {
        this.parentID = parentID;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
