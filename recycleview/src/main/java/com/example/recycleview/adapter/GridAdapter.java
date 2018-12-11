package com.example.recycleview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recycleview.R;
import com.example.recycleview.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private Context context;
    private List<UserBean.DataBean> list;
    public GridAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<UserBean.DataBean> data) {
        list.clear();
        if (data!=null){
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icon;
        private final TextView name;
        private final ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.icon);
            name=itemView.findViewById(R.id.name);
            constraintLayout=itemView.findViewById(R.id.constraintlayout);
        }
    }
    @NonNull
    @Override
    public GridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.ViewHolder viewHolder, final int i) {
        UserBean.DataBean dataBean = list.get(i);
        viewHolder.name.setText(dataBean.getName());
        Glide.with(context).load(dataBean.getIcon()).into(viewHolder.icon);
        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click!=null){
                    click.onClick(i);
                }
            }
        });
        viewHolder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (click!=null){
                    click.onLongClick(i);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    //删除
    public void removeData(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position,list.size());
    }
    Click click;
    public void setonClickListener(Click click){
        this.click=click;
    }
    //接口
    public interface Click{
        void onClick(int position);
        void onLongClick(int position);
    }

}
