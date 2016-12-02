package com.qhn.bhne.footprinting.mvp.interactor.impl;

import com.qhn.bhne.footprinting.db.SpotDao;
import com.qhn.bhne.footprinting.mvp.entries.Spot;
import com.qhn.bhne.footprinting.mvp.interactor.SpotInteractor;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by qhn
 * on 2016/11/29 0029.
 */

public class SpotIntelImpl implements SpotInteractor {

    private SpotDao spotDao;

    @Inject
    public SpotIntelImpl(SpotDao spotDao) {
        this.spotDao = spotDao;
    }

    @Override
    public void delete(long itemId) {

    }

    @Override
    public boolean add(Spot spot) {
        return false;
    }

    @Override
    public Long insert(Spot spot) {
        try {
            return spotDao.insert(spot);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return -1L;
        }

    }

    @Override
    public void update(Spot spot) {

    }

    @Override
    public List<Spot> queryList() {
        return null;
    }

    @Override
    public Spot queryUnique(long id) {
        return null;
    }

    @Override
    public Spot queryUnique(String name) {
        return null;
    }

    @Override
    public void save(Spot spot) {
        spotDao.save(spot);
    }
}
