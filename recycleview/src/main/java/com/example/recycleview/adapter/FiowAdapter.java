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

public class FiowAdapter extends RecyclerView.Adapter <FiowAdapter.ViewHolder>{
    private Context context;
    private List<UserBean.DataBean> list;
    public FiowAdapter(Context context) {
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
        private final TextView name;
        private final ImageView icon;
        private final ConstraintLayout constraintlayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            icon=itemView.findViewById(R.id.icon);
            constraintlayout=itemView.findViewById(R.id.constraintlayout);
        }
    }
    @NonNull
    @Override
    public FiowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.flow_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FiowAdapter.ViewHolder viewHolder, final int i) {
        UserBean.DataBean dataBean = list.get(i);
        viewHolder.name.setText(dataBean.getName());
        Glide.with(context).load(dataBean.getIcon()).into(viewHolder.icon);
        viewHolder.constraintlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click!=null){
                    click.onClick(i);
                }
            }
        });
        viewHolder.constraintlayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (click!=null){
                    click.onLongClick(i);
                }
                return true;
            }
        });
    }
    //删除
    public void removeData(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position,list.size());
       // notifyItemRangeChanged(position,list.size());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    //声明
    Click click;
    public void setOnClickListener(Click click){
        this.click=click;
    }
    //接口
    public interface Click{
        void onClick(int position);
        void onLongClick(int position);
    }
}
