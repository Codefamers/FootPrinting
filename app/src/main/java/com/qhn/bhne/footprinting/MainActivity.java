package com.qhn.bhne.footprinting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qhn.bhne.footprinting.db.DaoSession;
import com.qhn.bhne.footprinting.db.NoteDao;
import com.qhn.bhne.footprinting.entries.Construction;

import com.qhn.bhne.footprinting.entries.Note;

import com.qhn.bhne.footprinting.entries.NoteType;
import com.qhn.bhne.footprinting.entries.Project;
import com.socks.library.KLog;

import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content_main)
    RelativeLayout contentMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rec_project)
    RecyclerView recProject;
    ProjectListAdapter projectListAdapter;
    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        initFab();
        initRecyclerView();
        initDataBase();

    }

    private void initDataBase() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        noteDao=daoSession.getNoteDao();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setOnMenuItemClickListener(this);
    }

    private void initFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                //addNote();
                startActivity(new Intent(MainActivity.this,CreateProject.class));
            }
        });
    }

    private void addNote() {
        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());
        Note note = new Note(null,"第一次使用greenDao", comment, new Date(), NoteType.TEXT);
        noteDao.insert(note);

        KLog.d("note id"+note.getId());

    }

    private void initRecyclerView() {
        Map<String, List<Construction>> dataMap=new HashMap<>();
        projectListAdapter=new ProjectListAdapter(this, (HashMap<String, List<Construction>>) dataMap);
        setGridRecyclerStyle(this,recProject,1,projectListAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_download:
                Toast.makeText(this, "下载", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
    //从Url中加载图片
    public static void loadImageFormNet(String url, ImageView imageView, Activity activity) {
        Glide.with(activity).load(url).placeholder(R.drawable.ic_placeholder).into(imageView);
        //Glide.with(activity).load(url).bitmapTransform(new RoundedCornersTransformation(activity, 200, 0, RoundedCornersTransformation.CornerType.TOP)).crossFade().into(imageView);
    }

    public static void setGridRecyclerStyle(Activity activity, RecyclerView recyclerView, int span, RecyclerView.Adapter adapter) {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
    }

}
