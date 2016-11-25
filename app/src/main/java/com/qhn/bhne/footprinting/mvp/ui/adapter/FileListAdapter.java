package com.qhn.bhne.footprinting.mvp.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qhn.bhne.footprinting.R;
import com.qhn.bhne.footprinting.mvp.entries.FileContent;

import java.util.List;

/**
 * Created by qhn
 * on 2016/11/21 0021.
 */

public class FileListAdapter extends BaseAdapter {
    private List<FileContent> list;
    private Activity activity;

    public FileListAdapter(List<FileContent> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(activity).inflate(R.layout.item_file_header, null);
        TextView textView = (TextView) view.findViewById(R.id.txt_file_name);
        textView.setText(list.get(i).getFileName());
        return view;
    }
    public void addItem(FileContent fileContent){
        list.add(fileContent);
        notifyDataSetChanged();
    }
}
