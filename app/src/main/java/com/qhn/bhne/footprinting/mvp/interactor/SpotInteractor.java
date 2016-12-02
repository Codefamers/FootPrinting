package com.qhn.bhne.footprinting.mvp.interactor;

import com.qhn.bhne.footprinting.mvp.entries.Spot;

import java.util.List;

/**
 * Created by qhn
 * on 2016/11/29 0029.
 */

public interface SpotInteractor {
    void delete (long itemId);


    boolean add(Spot spot);

    Long insert(Spot spot);
    void update(Spot spot);

    List<Spot> queryList();
    Spot queryUnique(long id);
    Spot queryUnique(String name);

    void save(Spot spot);
}
