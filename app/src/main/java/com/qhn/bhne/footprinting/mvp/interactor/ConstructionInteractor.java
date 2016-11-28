package com.qhn.bhne.footprinting.mvp.interactor;

import com.qhn.bhne.footprinting.mvp.entries.Construction;
import com.qhn.bhne.footprinting.mvp.entries.Project;

import java.util.List;

/**
 * Created by qhn
 * on 2016/11/27 0027.
 */

public interface ConstructionInteractor {
    void delete(long parentId, long itemId);

    boolean add(Construction construction);

    Long insert(Construction construction);

    void update(Construction construction);

    List<Construction> queryList();




    Construction queryUnique(Long parentID, long id);

    Construction queryUnique(String name, long parentID);
}
