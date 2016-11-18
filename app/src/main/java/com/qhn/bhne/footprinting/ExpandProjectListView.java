package com.qhn.bhne.footprinting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qhn.bhne.footprinting.db.ConstructionDao;
import com.qhn.bhne.footprinting.db.DaoSession;
import com.qhn.bhne.footprinting.entries.Construction;
import com.qhn.bhne.footprinting.entries.Project;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qhn
 * on 2016/11/18 0018.
 */

public class ExpandProjectListView extends BaseExpandableListAdapter {

    private List<Project> projectList;
    private Activity activity;
    private DaoSession daoSession;
    private List<Construction> constructionList;

    public ExpandProjectListView(Activity activity, List<Project> projectList, DaoSession daoSession) {
        this.activity = activity;
        this.projectList = projectList;
        this.daoSession = daoSession;
    }


    @Override
    public int getGroupCount() {
        return projectList.size();
    }

    @Override
    public int getChildrenCount(int position) {
        Long projectId = projectList.get(position).getProjectId();
        Query<Construction> constructionQuery = daoSession.
                getConstructionDao()
                .queryBuilder()
                .where(ConstructionDao.Properties.ProjectId.eq(projectId))
                .build();
        constructionList = constructionQuery.list();
        return constructionQuery.list() == null ? 0 : constructionList.size();
    }

    @Override
    public Object getGroup(int i) {
        return projectList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return constructionList.get(i1);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // 获取对应一级条目的View  和ListView 的getView相似
        ProjectViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_project_header, null);
            holder = new ProjectViewHolder();
            holder.txtProjectName = (TextView) convertView.findViewById(R.id.txt_project_name);
            convertView.setTag(holder);
        } else {
            holder = (ProjectViewHolder) convertView.getTag();
        }
        holder.txtProjectName.setText(projectList.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // 获取对应二级条目的View  和ListView 的getView相似
        final ConsViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_project_menu_detail, null);
            holder = new ConsViewHolder();
            holder.txtConsName = (TextView) convertView.findViewById(R.id.txt_cons_name);
            holder.lvFile= (ListView) convertView.findViewById(R.id.lv_file_name);
            convertView.setTag(holder);
        } else {
            holder = (ConsViewHolder) convertView.getTag();
        }
        if (constructionList.size()!=0) {
            holder.txtConsName.setText(constructionList.get(childPosition).getName());
            ArrayAdapter adapter=new ArrayAdapter(activity,android.R.layout.simple_list_item_1,activity.getResources().getStringArray(R.array.project_category));
            holder.lvFile.setAdapter(adapter);
            setListViewHeightBasedOnChildren(holder.lvFile);
        }
        holder.txtConsName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (holder.lvFile.getVisibility()== View.GONE) {
                    holder.lvFile.setVisibility(View.VISIBLE);
                }else
                    holder.lvFile.setVisibility(View.GONE);

            }
        });
        holder.lvFile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                activity.startActivity(new Intent(activity,MapActivity.class));
            }
        });
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }


    private TextView getGenericView(String string) {

        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView textView = new TextView(activity);
        textView.setLayoutParams(layoutParams);

        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

        textView.setPadding(40, 20, 0, 20);
        textView.setText(string);
        textView.setTextColor(Color.RED);
        return textView;
    }

    static class ProjectViewHolder {
        private TextView txtProjectName;
    }

    static class ConsViewHolder {
        private TextView txtConsName;
        private ListView  lvFile;
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
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
