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

public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.ViewHolder> {
    private Context context;
    private List<UserBean.DataBean> list;
    public LinearAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }
    //传递数据的方法
    public void setList(List<UserBean.DataBean> data) {
        list.clear();
        if (data!=null){
            list.addAll(data);
        }
        notifyDataSetChanged();
    }
    //自定义ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final ImageView icon;
        public final ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            icon=itemView.findViewById(R.id.icon);
            constraintLayout=itemView.findViewById(R.id.constraintlayout);
        }
    }
    @NonNull
    @Override
    public LinearAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //获取数据展示子条目
        View view = LayoutInflater.from(context).inflate(R.layout.list_item,viewGroup,false);
        //创建ViewHolder实例并传入子条目布局
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinearAdapter.ViewHolder viewHolder, final int i) {
        //获取list集合对应下标的数据dataBean
        UserBean.DataBean dataBean = list.get(i);
        //给子条目控件赋值
        viewHolder.name.setText(dataBean.getName());
        Glide.with(context).load(dataBean.getIcon()).into(viewHolder.icon);
        //设置点击事件
        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click!=null){
                    click.onClick(i);
                }
            }
        });
        //设置长按监听事件
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
    //添加数据
    private void addData(int position, UserBean.DataBean dataBean){
        list.add(position,dataBean);
        notifyItemInserted(position);
    }
    //删除条目数据
    public void removeData(int position) {
        list.remove(position);
        //必须使用notifyItemRemoved 才能加载移除动画
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,list.size());
    }
    //声明接口
    Click click;
    public void setClickListener(Click click){
        this.click=click;
    }
    //自定义接口
    public interface Click{
        void onClick(int position);
        void onLongClick(int position);
    }

}
