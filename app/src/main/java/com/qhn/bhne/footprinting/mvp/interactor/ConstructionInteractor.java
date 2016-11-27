package com.qhn.bhne.footprinting.mvp.interactor;

import com.qhn.bhne.footprinting.mvp.entries.Construction;
import com.qhn.bhne.footprinting.mvp.entries.Project;

import java.util.List;

/**
 * Created by qhn
 * on 2016/11/27 0027.
 */

public interface ConstructionInteractor {
    boolean delete (long itemId);
    boolean add();
    boolean update();
    List<Construction> queryList();
    Construction queryUnique(long id);
    Construction queryUnique(String name,long parentID);
}
