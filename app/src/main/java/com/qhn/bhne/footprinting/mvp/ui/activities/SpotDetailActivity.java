package com.qhn.bhne.footprinting.mvp.ui.activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.qhn.bhne.footprinting.R;
import com.qhn.bhne.footprinting.db.ConstructionDao;
import com.qhn.bhne.footprinting.db.SpotDao;
import com.qhn.bhne.footprinting.mvp.entries.Construction;
import com.qhn.bhne.footprinting.mvp.ui.activities.base.BaseActivity;
import com.qhn.bhne.footprinting.mvp.ui.adapter.SpotDetailAdapter;

import org.greenrobot.greendao.query.LazyList;
import org.greenrobot.greendao.query.QueryBuilder;

import butterknife.BindView;


public class SpotDetailActivity extends BaseActivity {
    @BindView(R.id.rec_spot)
    RecyclerView recSpot;

    @BindView(R.id.root)
    CoordinatorLayout rootLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    private long parentID;
    private LazyList spotList;
    private boolean isChooseSpot;

    @Override
    protected void initViews() {
        parentID = getIntent().getLongExtra("PARENT_ID", -1);
        isChooseSpot = getIntent().getBooleanExtra("CHOOSE_SPOT", false);
        QueryBuilder queryBuilder = daoSession.getSpotDao().queryBuilder().where(SpotDao.Properties.ParentID.eq(parentID));
        QueryBuilder<Construction> queryBuilder1 = daoSession.getConstructionDao().queryBuilder().where(ConstructionDao.Properties.ChildId.eq(parentID));
        Construction construction = queryBuilder1.unique();

        //QueryBuilder<Project> queryBuilder2=daoSession.getProjectDao().queryBuilder().where(ProjectDao.Properties.ChildId.eq(construction.getProjectId()));
        if (!TextUtils.isEmpty(construction.getName())) {
            collapsingToolbar.setTitle(construction.getName());
        }


        spotList = queryBuilder.listLazy();
        if (spotList.size() != 0) {
            initRec();
        } else {
            LayoutInflater.from(this).inflate(R.layout.item_loading_empty, rootLayout, true);
        }

    }

    private void initRec() {

        recSpot.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recSpot.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        SpotDetailAdapter mAdapter = new SpotDetailAdapter(spotList);
        mAdapter.setmRec(recSpot);
        mAdapter.setCbIsShow(isChooseSpot);
        recSpot.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_spot_detail;
    }

    @Override
    protected void initInjector() {

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }


}
