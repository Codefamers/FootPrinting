package com.qhn.bhne.footprinting.mvp.interactor;

import com.qhn.bhne.footprinting.mvp.entries.FileContent;
import com.qhn.bhne.footprinting.mvp.entries.Project;

import java.util.List;

/**
 * Created by qhn
 * on 2016/11/27 0027.
 */

public interface FileInteractor {
    boolean delete (long itemId);
    boolean add(FileContent fileContent);


    void update(FileContent fileContent);

    List<FileContent> queryList();
    FileContent queryUnique(long id);
    FileContent queryUnique(String name,long parentID);
    Long insert(FileContent fileContent);
}
