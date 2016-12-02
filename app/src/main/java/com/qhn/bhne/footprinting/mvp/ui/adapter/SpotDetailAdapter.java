package com.qhn.bhne.footprinting.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.qhn.bhne.footprinting.R;
import com.qhn.bhne.footprinting.mvp.entries.Spot;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qhn
 * on 2016/11/30 0030.
 */
public class SpotDetailAdapter extends RecyclerView.Adapter<SpotDetailAdapter.ViewHolder> {



    private List<Spot> spotList;
    private Boolean cbIsShow;
    private RecyclerView mRec;
    private int checkPosition = -1;

    public RecyclerView getmRec() {
        return mRec;
    }

    public void setmRec(RecyclerView mRec) {
        this.mRec = mRec;
    }

    public Boolean getCbIsShow() {
        return cbIsShow;
    }

    public void setCbIsShow(Boolean cbIsShow) {
        this.cbIsShow = cbIsShow;
    }

    public SpotDetailAdapter(List<Spot> spotList) {
        this.spotList = spotList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spot_detail, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Spot spot = spotList.get(position);

        if (cbIsShow) {
            holder.cbIsCheck.setVisibility(View.VISIBLE);

            holder.cbIsCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (holder.getOldPosition() != position) {
                            ViewHolder viewHolder = (ViewHolder) mRec.findViewHolderForLayoutPosition(checkPosition);
                            if (viewHolder != null) {
                                if (viewHolder.cbIsCheck.isChecked()) {
                                    viewHolder.cbIsCheck.setChecked(false);
                                }
                            }

                        }
                        checkPosition = position;
                    }
                }
            });


        }


        holder.txtSpotName.setText(spot.getName());
        holder.txtSpotAddress.setText(new StringBuilder().append("位置").append(spot.getLocation()).toString());
        holder.txtServerGrade.setText(String.valueOf(spot.getDate()).substring(0, 10));
        holder.txtSpotNum.setText(new StringBuilder().append("编号").append(spot.getNumber()).toString());

        holder.txtSpotType.setText(new StringBuilder().append("点类型 : ").append(spot.getType()).toString());
        if (TextUtils.isEmpty(spot.getRemark())) {
            holder.txtSpotDetailContent.setText("暂无描述");
        }else
            holder.txtSpotDetailContent.setText(spot.getRemark());
    }

    @Override
    public int getItemCount() {
        return spotList == null ? 0 : spotList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_spot_name)
        TextView txtSpotName;
        @BindView(R.id.txt_spot_num)
        TextView txtSpotNum;
        @BindView(R.id.txt_server_grade)
        TextView txtServerGrade;
        @BindView(R.id.img_cut_line_one)
        ImageView imgCutLineOne;
        @BindView(R.id.txt_spot_detail_content)
        TextView txtSpotDetailContent;
        @BindView(R.id.txt_spot_type)
        TextView txtSpotType;
        @BindView(R.id.txt_spot_address)
        TextView txtSpotAddress;
        @BindView(R.id.cb_is_selected)
        CheckBox cbIsCheck;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
