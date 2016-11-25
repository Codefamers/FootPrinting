package com.qhn.bhne.footprinting.mvp.ui.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qhn.bhne.footprinting.R;
import com.qhn.bhne.footprinting.mvp.ui.activities.base.BaseActivity;
import com.qhn.bhne.footprinting.mvp.ui.adapter.ExpandProjectListView;
import com.qhn.bhne.footprinting.db.ConstructionDao;
import com.qhn.bhne.footprinting.db.FileContentDao;
import com.qhn.bhne.footprinting.db.ProjectDao;
import com.qhn.bhne.footprinting.mvp.entries.Construction;
import com.qhn.bhne.footprinting.mvp.entries.FileContent;
import com.qhn.bhne.footprinting.mvp.entries.Project;
import com.qhn.bhne.footprinting.interfaces.Constants;
import com.qhn.bhne.footprinting.interfaces.PopClickItemCallBack;
import com.qhn.bhne.footprinting.utils.StatusBarCompat;
import com.socks.library.KLog;

import org.greenrobot.greendao.query.Query;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

public class ShowProjectActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, PopClickItemCallBack {

    public static final int CREATE_PROJECT = 1;
    public static final int CREATE_CONSTRUCTION = 2;
    public static final int CREATE_FILE = 3;
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

    @BindView(R.id.exl_project)
    ExpandableListView exlProject;
    private EditText editText;
    private ImageView imageHint;
    private Button btnCancel;
    private Button btnConfirm;
    private View dialogView;
    private ExpandProjectListView expandableListAdapter;
    private List<Project> projectList;
    private long parentID;
    private ProjectDao projectDao;
    private ConstructionDao constDao;
    private FileContentDao fileContentDao;

    @Override
    protected void initViews() {
        projectDao = daoSession.getProjectDao();
        constDao = daoSession.getConstructionDao();
        fileContentDao = daoSession.getFileContentDao();
        initToolBar();
        initDrawerLayout();
        initExpandableListView();

    }

    //初始化工具栏
    private void initToolBar() {
        setSupportActionBar(toolbar);
        StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimaryDark));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(CREATE_PROJECT);
            }
        });
    }

    private void initExpandableListView() {
        projectList = queryProjectList();
        expandableListAdapter = new ExpandProjectListView(projectList, daoSession, this);
        exlProject.setAdapter(expandableListAdapter);
    }

    //查询项目列表
    private List<Project> queryProjectList() {
        Query<Project> projectQuery = projectDao
                .queryBuilder()
                .where(ProjectDao.Properties.UserName.eq(currentUser.getName()))
                .build();
        Iterator<Project> iterator = projectQuery.listIterator();

        while (iterator.hasNext()) {
            Project project = iterator.next();
            List<Construction> constList = queryConstList(project);
            project.setConstructionList(constList);
        }

        return projectQuery.list();
    }




    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    public void createDialog(int createCategory) {
        initCustomDialog();
        initAlertDialog(createCategory);
    }

    private void initAlertDialog(final int createCategory) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setTitle("新建项目/工程");
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strProjectName = editText.getText().toString();
                Boolean showWhichIcon = verifyIsRepeat(strProjectName, createCategory);
                if (showWhichIcon || TextUtils.isEmpty(strProjectName)) {
                    Toast.makeText(ShowProjectActivity.this, "工程名已存在或者空值", Toast.LENGTH_SHORT).show();
                    showHintIcon(true);
                    return;
                }
                showHintIcon(showWhichIcon);

                switch (createCategory) {
                    case CREATE_PROJECT:
                    case CREATE_CONSTRUCTION:
                        dialog.dismiss();
                        Intent intent = new Intent(ShowProjectActivity.this, CreateProjectActivity.class);
                        intent.putExtra("PROJECT_NAME", strProjectName);
                        intent.putExtra("EVENT_CATEGORY", createCategory);
                        intent.putExtra("PROJECT_ID", parentID);
                        startActivityForResult(intent, 100);
                        break;
                    case CREATE_FILE:
                        FileContent fileContent = new FileContent(null, parentID, strProjectName, currentUser.getName());
                        if (!insertFileContent(fileContent)) {//创建失败
                            showHintIcon(false);
                        } else {//创建成功
                            refreshExpandProjectList();
                            dialog.dismiss();
                        }

                        break;
                }


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

    private boolean insertFileContent(FileContent fileContent) {
        try {

            fileContentDao.save(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "创建失败该名称已被注册", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
    private boolean verifyIsRepeat(String projectName, int createCategory) {
        if (createCategory == CREATE_PROJECT) {
            Query<Project> projectQuery = projectDao
                    .queryBuilder()
                    .where(ProjectDao.Properties.Name.eq(projectName), ProjectDao.Properties.UserName.eq("123456"))
                    .build();
            if (projectQuery.unique() != null) {
                return true;
            } else
                return false;
        } else {
            Query<Construction> constructionQuery = constDao
                    .queryBuilder()
                    .where(ConstructionDao.Properties.Name.eq(projectName), ConstructionDao.Properties.UserName.eq("123456"))
                    .build();
            if (constructionQuery.unique() != null) {
                return true;
            } else
                return false;
        }

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       /* //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            switch (resultCode) {
                case RESULT_OK:
                    projectList.add(queryProjectUnique(data.getStringExtra("PROJECT_NAME")));
                    expandableListAdapter.notifyDataSetChanged();
                    break;
                case CreateProjectActivity.RESULT_CREATE_CONST:
                    refreshExpandProjectList();

                    break;
            }
        }
    }

    private void refreshExpandProjectList() {
        projectList.clear();
        projectList.addAll(queryProjectList());
        expandableListAdapter.notifyDataSetChanged();
    }


    @Override
    public void clickItem(MenuItem menuItem, int createProject, long parentID,long itemID) {
        this.parentID = parentID;
        switch (menuItem.getItemId()) {
            case R.id.add_construction:
                createDialog(createProject);

                break;
            case R.id.look_info:
                Intent intent = new Intent(ShowProjectActivity.this, CreateProjectActivity.class);
                intent.putExtra("PROJECT_ID", parentID);
                intent.putExtra("ITEM_ID",itemID);
                intent.putExtra("EVENT_CATEGORY", createProject-1);
                startActivityForResult(intent, 100);
                break;
            case R.id.delete_project:
                Toast.makeText(this, "删除项目", Toast.LENGTH_SHORT).show();
                deleteItem(createProject,itemID);
                break;
        }
    }

    private void deleteItem(int createProject,long itemID) {
        daoSession.clear();
        switch (createProject) {
            case CREATE_CONSTRUCTION:

                projectDao.delete(queryProjectUnique(itemID));
                refreshExpandProjectList();
                break;
            case CREATE_FILE:

                constDao.delete(queryConstUnique(itemID));
                refreshExpandProjectList();
                break;
        }
    }

    private Construction queryConstUnique(long itemID) {
        Query<Construction> constructionQuery = constDao
                .queryBuilder()
                .where(ConstructionDao.Properties.ConstructionId.eq(itemID))
                .build();
        return constructionQuery.unique();

    }
    private List<Construction> queryConstList(Project project) {
        Long projectId = project.getProjectId();
        Query<Construction> constructionQuery = constDao
                .queryBuilder()
                .where(ConstructionDao.Properties.ParentID.eq(projectId * Constants.PROJECT_MAX))
                .build();
        return constructionQuery.list();
    }

    private Project queryProjectUnique(String projectName) {
        Query<Project> projectQuery = projectDao
                .queryBuilder()
                .where(ProjectDao.Properties.UserName.eq(currentUser.getName()), ProjectDao.Properties.Name.eq(projectName))
                .build();

        return projectQuery.unique();
    }
    private Project queryProjectUnique(Long id) {
        Query<Project> projectQuery = projectDao
                .queryBuilder()
                .where(ProjectDao.Properties.UserName.eq(currentUser.getName()), ProjectDao.Properties.ProjectId.eq(id))
                .build();

        return projectQuery.unique();
    }
}
