package com.qhn.bhne.footprinting;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qhn.bhne.footprinting.db.DaoSession;
import com.qhn.bhne.footprinting.db.ProjectDao;
import com.qhn.bhne.footprinting.entries.Construction;
import com.qhn.bhne.footprinting.entries.Project;
import com.socks.library.KLog;

import org.greenrobot.greendao.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ShowProjectActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content_show_project)
    RelativeLayout contentShowProject;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.rec_project)
    RecyclerView recProject;
    @BindView(R.id.exl_project)
    ExpandableListView exlProject;
    private ProjectListAdapter projectListAdapter;
    private EditText editText;
    private ImageView imageHint;
    private Button btnCancel;
    private Button btnConfirm;
    private View dialogView;
    private ExpandProjectListView expandableListAdapter;
    private List<Project> projectList;

    @Override
    protected void initViews() {
        initToolBar();
        initDrawerLayout();
        initRecyclerView();
        initExpandableListView();
        initDataBase();
    }

    private void initExpandableListView() {
        Construction construction=new Construction(null,1l,"123456","天津","高压",null,null,null,null);
        daoSession.insert(construction);
        projectList=queryProjectList();
        expandableListAdapter=new ExpandProjectListView(this,projectList,daoSession);
        exlProject.setAdapter(expandableListAdapter);
    }

    private List<Project> queryProjectList() {
        Query<Project> projectQuery=daoSession.getProjectDao()
                .queryBuilder()
                .where(ProjectDao.Properties.UserName.eq("123456"))
                .build();
        return projectQuery.list();
    }
    private Project queryProjectUniqe(String projectName) {
        Query<Project> projectQuery=daoSession.getProjectDao()
                .queryBuilder()
                .where(ProjectDao.Properties.UserName.eq("123456"),ProjectDao.Properties.Name.eq(projectName))
                .build();
        return projectQuery.unique();
    }
    private void initToolBar() {
        setSupportActionBar(toolbar);
        StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimaryDark));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                showDialog();
            }
        });
    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void showDialog() {
        initCustomDialog();
        initAlertDialog();
    }

    private void initAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setTitle("新建项目/工程");
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strProjectName=editText.getText().toString();
                Boolean showWhichIcon = verifyIsRepeat(strProjectName);
                if (showWhichIcon||TextUtils.isEmpty(strProjectName)){
                    Toast.makeText(ShowProjectActivity.this, "工程名已存在或者空值", Toast.LENGTH_SHORT).show();
                    showHintIcon(true);
                    return;
                }
                showHintIcon(showWhichIcon);
                Intent intent=new Intent(ShowProjectActivity.this,CreateProject.class);
                intent.putExtra("PROJECT_NAME",strProjectName);
                startActivityForResult(intent,100);
                dialog.dismiss();

            }


        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setView(dialogView);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void showHintIcon(Boolean showWhichIcon) {
        if (showWhichIcon) {
            imageHint.setImageResource(R.mipmap.ic_info_outline_white_18dp);
            imageTint(imageHint, 0xFF, 0xC5, 0x11, 0x62);

        } else {
            imageHint.setImageResource(R.mipmap.ic_done_white_18dp);
            imageTint(imageHint, 0xFF, 0x40, 0xC4, 0xFF);

        }
    }

    private void imageTint(ImageView imageView, int alpha, int red, int green, int blue) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setImageTintList(ColorStateList.valueOf(Color.argb(alpha, red, green, blue)));
        } else
            imageView.setColorFilter(Color.argb(alpha, red, green, blue));
    }

    private void initCustomDialog() {
        LayoutInflater inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.item_new_project, (ViewGroup) findViewById(R.id.dialog));
        editText = (EditText) dialogView.findViewById(R.id.ed_project);
        imageHint = (ImageView) dialogView.findViewById(R.id.img_hint);
        btnConfirm = (Button) dialogView.findViewById(R.id.btn_confirm);
        btnCancel = (Button) dialogView.findViewById(R.id.btn_cancel);
    }



    //验证工程名是否重复
    private boolean verifyIsRepeat(String projectName) {
        Query<Project> projectQuery = daoSession.getProjectDao()
                .queryBuilder()
                .where(ProjectDao.Properties.Name.eq(projectName), ProjectDao.Properties.UserName.eq("123456"))
                .build();
        if (projectQuery.unique() != null) {
            return true;
        } else
            return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_project;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_camera:
                break;
            case R.id.nav_gallery:
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_send:
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_view:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    private void initDataBase() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        ProjectDao projectDao = daoSession.getProjectDao();
    }

    private void initRecyclerView() {
        Map<String, List<Construction>> dataMap = new HashMap<>();
        projectListAdapter = new ProjectListAdapter(this, (HashMap<String, List<Construction>>) dataMap);
        MyUtils.setGridRecyclerStyle(this, recProject, 1, projectListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            switch (resultCode) {
                case RESULT_OK:
                    refreshRecyclerView();
                    KLog.d("增加一条数据");
                    projectList.add(queryProjectUniqe(data.getStringExtra("PROJECT_NAME")));
                    expandableListAdapter.notifyDataSetChanged();

                    break;
            }
        }
    }

    private void refreshRecyclerView() {

    }
}
