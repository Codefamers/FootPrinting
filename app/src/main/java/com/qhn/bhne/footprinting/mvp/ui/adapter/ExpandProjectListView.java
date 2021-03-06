package com.qhn.bhne.footprinting.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.qhn.bhne.footprinting.R;
import com.qhn.bhne.footprinting.db.DaoSession;
import com.qhn.bhne.footprinting.db.FileContentDao;
import com.qhn.bhne.footprinting.mvp.ui.activities.MapActivity;
import com.qhn.bhne.footprinting.mvp.ui.activities.ShowProjectActivity;
import com.qhn.bhne.footprinting.mvp.entries.Construction;
import com.qhn.bhne.footprinting.mvp.entries.FileContent;
import com.qhn.bhne.footprinting.mvp.entries.Project;
import com.qhn.bhne.footprinting.interfaces.Constants;
import com.qhn.bhne.footprinting.interfaces.CreateFileCallBack;
import com.qhn.bhne.footprinting.interfaces.PopClickItemCallBack;
import com.socks.library.KLog;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Created by qhn
 * on 2016/11/18 0018.
 */

public class ExpandProjectListView extends BaseExpandableListAdapter implements CreateFileCallBack {

    private List<Project> projectList;

    private DaoSession daoSession;
    private PopClickItemCallBack callBack;
    private FileListAdapter adapter;

    public ExpandProjectListView( List<Project> projectList, DaoSession daoSession, PopClickItemCallBack itemCallBack) {

        this.projectList = projectList;
        this.daoSession = daoSession;
        callBack = itemCallBack;
    }


    @Override
    public int getGroupCount() {
        return projectList.size();
    }

    @Override
    public int getChildrenCount(int position) {
        List<Construction> constructionList = projectList.get(position).getConstructionList();
        return constructionList == null ? 0 : constructionList.size();
    }

    @Override
    public Object getGroup(int i) {
        return projectList.get(i);
    }

    @Override
    public Object getChild(int position, int i1) {
        return projectList.get(position).getConstructionList().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // 获取对应一级条目的View  和ListView 的getView相似

        final ProjectViewHolder holder;
        final Context context=parent.getContext();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_project_header, parent, false);
            holder = new ProjectViewHolder();
            holder.txtProjectName = (TextView) convertView.findViewById(R.id.txt_project_name);
            holder.imgMoreOperation = (ImageView) convertView.findViewById(R.id.img_more_operation);
            convertView.setTag(holder);
        } else {
            holder = (ProjectViewHolder) convertView.getTag();
        }
        final Project project = projectList.get(groupPosition);
        holder.imgMoreOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PopupMenu popMenu = new PopupMenu(context, holder.imgMoreOperation);
                popMenu.getMenuInflater().inflate(R.menu.menu_project_operation, popMenu.getMenu());
                KLog.d("childid"+project.getChildId());
                popMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        callBack.clickItem(menuItem, ShowProjectActivity.CREATE_CONSTRUCTION, project.getChildId(), project.getId());
                        return true;
                    }
                });
                popMenu.show();
            }
        });
        holder.txtProjectName.setText(projectList.get(groupPosition).getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // 获取对应二级条目的View  和ListView 的getView相似
        final ConsViewHolder holder;
        final Context context=parent.getContext();

        final List<Construction> constructionList = projectList.get(groupPosition).getConstructionList();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_project_menu_detail, parent, false);
            holder = new ConsViewHolder();
            holder.txtConsName = (TextView) convertView.findViewById(R.id.txt_cons_name);
            holder.lvFile = (ListView) convertView.findViewById(R.id.lv_file_name);
            holder.imgMoreOperation = (ImageView) convertView.findViewById(R.id.img_more_operation);
            convertView.setTag(holder);
        } else {
            holder = (ConsViewHolder) convertView.getTag();
        }
        if (constructionList.size() != 0) {
            final Construction construction = constructionList.get(childPosition);
            holder.txtConsName.setText(constructionList.get(childPosition).getName());
            final List<FileContent> fileContentList = construction.getFileContentList();
            adapter = new FileListAdapter(fileContentList);
            holder.lvFile.setAdapter(adapter);
            setListViewHeightBasedOnChildren(holder.lvFile);
            holder.imgMoreOperation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popMenu = new PopupMenu(context, holder.imgMoreOperation);
                    popMenu.getMenuInflater().inflate(R.menu.menu_project_operation, popMenu.getMenu());
                    popMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            callBack.clickItem(menuItem, ShowProjectActivity.CREATE_FILE, construction.getChildId(), construction.getId());
                            return true;
                        }
                    });
                    popMenu.show();
                }
            });
            holder.txtConsName.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (holder.lvFile.getVisibility() == View.GONE) {
                        holder.lvFile.setVisibility(View.VISIBLE);
                    } else
                        holder.lvFile.setVisibility(View.GONE);

                }
            });
            holder.lvFile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent=new Intent(context, MapActivity.class);
                    intent.putExtra("CHILD_ID",fileContentList.get(position).getChildId());
                    intent.putExtra("ITEM_ID",fileContentList.get(position).getFileID());
                    context.startActivity(intent);
                }
            });
        }


        return convertView;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    @Override
    public void onSuccess(FileContent fileContent) {
        adapter.addItem(fileContent);
        adapter.notifyDataSetChanged();
    }


    static class ProjectViewHolder {
        private TextView txtProjectName;
        private ImageView imgMoreOperation;
    }

    static class ConsViewHolder {
        private TextView txtConsName;
        private ListView lvFile;
        private ImageView imgMoreOperation;
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
