package com.qhn.bhne.footprinting.interfaces;

import android.view.MenuItem;

/**
 * Created by qhn
 * on 2016/11/21 0021.
 */

public interface PopClickItemCallBack {
    void clickItem(MenuItem menuItem,int createCategory,long parentID,long itemID);
    //void clickListItem(MenuItem menuItem,int createCategory,int position);
}
