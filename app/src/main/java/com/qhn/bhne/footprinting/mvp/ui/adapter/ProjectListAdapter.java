package com.qhn.bhne.footprinting.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qhn.bhne.footprinting.R;
import com.qhn.bhne.footprinting.mvp.entries.Construction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;

/**
 * Created by qhn
 * on 2016/11/8 0008.
 */
public class ProjectListAdapter extends RecyclerView.Adapter {
    private static final int TYPE_ITEM = 0;//item类型
    private static final int TYPE_TITLE = 1;//title类型


    private HashMap<Integer, String> titleDataMap;//标题数据
    private HashMap<String, List<Construction>> contentData;//内容数据
    private Map<Integer, Construction> posItemMap;//记录item与position对应关系
    private Context context;//上下文环境
    private int titleNum = 0;
    private int itemPos = 0;

    public ProjectListAdapter(Context context, HashMap<String, List<Construction>> dataMap) {
        this.context = context;

        contentData = dataMap;
        posItemMap = new HashMap<>();
        titleDataMap = new HashMap<Integer, String>();
        if (dataMap != null) {
            initTitleData();//将标题中的数据位置与recyclerView中的位置对齐

        }
    }

    private void initTitleData() {
        Set<String> titleSet = contentData.keySet();
        Object[] titleArray = titleSet.toArray();
        for (int i = 0; i < titleSet.size(); i++) {
            titleDataMap.put(itemPos + titleNum, (String) titleArray[i]);
            ++titleNum;

            for (int a = 0; a < contentData.get(titleArray[i]).size(); a++) {
                posItemMap.put(titleNum + itemPos, contentData.get(titleArray[i]).get(a));
                itemPos++;
            }
        }


    }

    public void addMore(HashMap<String, List<Construction>> dataMap) {
        int startPosition = contentData == null ? 0 : contentData.size();
        if (dataMap != null) {
            contentData.putAll(dataMap);
        }
        notifyItemRangeInserted(startPosition, contentData.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new ContentViewHolder(LayoutInflater.from(
                    context).inflate(R.layout.item_project_menu_detail, parent,
                    false));
        }
        if (viewType == TYPE_TITLE) {
            return new TitleViewHolder(LayoutInflater.from(
                    context).inflate(R.layout.item_project_header, parent,
                    false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentViewHolder) {


        }
        if (holder instanceof TitleViewHolder) {

        }
    }

   /* @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return isFooter(position) ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    private boolean isFooter(int position) {
        int type = getItemViewType(position);
        if (type == TYPE_TITLE) {
            return true;//只占一行中的一列，
        }
        return false;
    }*/

    @Override
    public int getItemViewType(int position) {
        if (titleDataMap.containsKey(position))
            return TYPE_TITLE;
        else
            return TYPE_ITEM;
    }


    @Override
    public int getItemCount() {
        if (contentData != null) {
            return posItemMap.size() + titleDataMap.size();
        }
        return 0;
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {


        public ContentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {

        public TitleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }
    }
}
