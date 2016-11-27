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
import com.qhn.bhne.footprinting.interfaces.PopClickItemCallBack;
import com.qhn.bhne.footprinting.mvp.entries.Construction;
import com.qhn.bhne.footprinting.mvp.entries.FileContent;
import com.qhn.bhne.footprinting.mvp.entries.Project;
import com.qhn.bhne.footprinting.mvp.presenter.impl.ShowProjectPI;
import com.qhn.bhne.footprinting.mvp.ui.activities.base.BaseActivity;
import com.qhn.bhne.footprinting.mvp.ui.adapter.ExpandProjectListView;
import com.qhn.bhne.footprinting.mvp.view.ShowProjectView;
import com.qhn.bhne.footprinting.utils.DateFormat;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ShowProjectActivity extends BaseActivity
        implements ShowProjectView, NavigationView.OnNavigationItemSelectedListener, PopClickItemCallBack {
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


    @Inject
    ShowProjectPI showProjectPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_project;
    }

    @Override
    protected void initInjector() {

        getActivityComponent().inject(this);

    }

    @Override
    protected void initViews() {
        showProjectPresenter.attachView(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(CREATE_PROJECT, 0);
            }
        });
        initDrawerLayout();
        initExpandableListView();

    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initExpandableListView() {

        projectList = showProjectPresenter.queryProjectList();
        expandableListAdapter = new ExpandProjectListView(projectList, daoSession, this);
        exlProject.setAdapter(expandableListAdapter);
    }


    public void createDialog(int createCategory, long parentId) {
        initCustomDialog();
        initAlertDialog(createCategory, parentId);
    }

    private void initAlertDialog(final int createCategory, final long parentId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setTitle("新建项目/工程");
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strProjectName = editText.getText().toString();
                Boolean showWhichIcon = false;
                if (TextUtils.isEmpty(strProjectName)) {
                    Toast.makeText(ShowProjectActivity.this, "请输入工程名", Toast.LENGTH_SHORT).show();
                    showHintIcon(showWhichIcon);
                    return;
                }
                showWhichIcon = showProjectPresenter.verifyRepeatName(createCategory, strProjectName, parentId);
                if (showWhichIcon){
                    showHintIcon(showWhichIcon);
                    Toast.makeText(ShowProjectActivity.this, "该名称已被注册", Toast.LENGTH_SHORT).show();
                    return;
                }


                switch (createCategory) {
                    case CREATE_PROJECT:
                    case CREATE_CONSTRUCTION:

                        Intent intent = new Intent(ShowProjectActivity.this, CreateProjectActivity.class);
                        intent.putExtra("PROJECT_NAME", strProjectName);
                        intent.putExtra("EVENT_CATEGORY", createCategory);
                        intent.putExtra("PROJECT_ID", parentID);
                        startActivityForResult(intent, 100);
                        break;
                    case CREATE_FILE:
                        FileContent fileContent = new FileContent(null, parentID, strProjectName, currentUser.getName(), DateFormat.dateFormat(new Date()));
                        if (!showProjectPresenter.addFile(fileContent)) {
                            return;
                        }
                        showProjectPresenter.refreshData();
                        break;
                }
                dialog.dismiss();
               /*  = verifyIsRepeat(strProjectName, createCategory);
                if (showWhichIcon || TextUtils.isEmpty(strProjectName)) {
                    Toast.makeText(ShowProjectActivity.this, "工程名已存在或者空值", Toast.LENGTH_SHORT).show();

                    return;
                }
                showHintIcon(showWhichIcon);


*/

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
    public void refreshView(List<Project> list) {
        projectList.clear();
        projectList.addAll(list);
        expandableListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            switch (resultCode) {
                case RESULT_OK:
                    showProjectPresenter.refreshData();
                    break;
                case CreateProjectActivity.RESULT_CREATE_CONST:
                    daoSession.clear();
                    showProjectPresenter.refreshData();

                    break;
            }
        }
    }


    @Override
    public void clickItem(MenuItem menuItem, int createProject, long childID, long itemID) {
        parentID = childID;
        switch (menuItem.getItemId()) {
            case R.id.add_construction:
                createDialog(createProject, childID);

                break;
            case R.id.look_info:
                Intent intent = new Intent(ShowProjectActivity.this, CreateProjectActivity.class);
                intent.putExtra("PROJECT_ID", childID);
                intent.putExtra("ITEM_ID", itemID);
                intent.putExtra("EVENT_CATEGORY", createProject - 1);
                startActivityForResult(intent, 100);
                break;
            case R.id.delete_project:
                Toast.makeText(this, "删除项目", Toast.LENGTH_SHORT).show();
                showProjectPresenter.deleteItem(createProject, itemID);

                showProjectPresenter.refreshData();
                break;
        }
    }

    private void deleteItem(int itemCategory, long itemID) {

    }

    private Construction queryConstUnique(long itemID) {
        /*Query<ConstructionInteractor> constructionQuery = constDao
                .queryBuilder()
                .where(ConstructionDao.Properties.Id.eq(itemID))
                .build();
        return constructionQuery.unique();*/
        return null;

    }


    private Project queryProjectUnique(String projectName) {
       /* Query<Project> projectQuery = projectDao
                .queryBuilder()
                .where(ProjectDao.Properties.UserName.eq(currentUser.getName()), ProjectDao.Properties.Name.eq(projectName))
                .build();

        return projectQuery.unique();*/
        return null;
    }

    private Project queryProjectUnique(Long id) {
       /* Query<Project> projectQuery = projectDao
                .queryBuilder()
                .where(ProjectDao.Properties.UserName.eq(currentUser.getName()), ProjectDao.Properties.Id.eq(id))
                .build();

        return projectQuery.unique();*/
        return null;
    }


}
