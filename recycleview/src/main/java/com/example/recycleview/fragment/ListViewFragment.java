package com.example.recycleview.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recycleview.Apis;
import com.example.recycleview.R;
import com.example.recycleview.adapter.LinearAdapter;
import com.example.recycleview.bean.UserBean;
import com.example.recycleview.presenter.IPresenterImpl;
import com.example.recycleview.view.IView;

import java.util.HashMap;

public class ListViewFragment extends Fragment implements IView {
    private RecyclerView recyclerView;
    private IPresenterImpl iPresenter;
    private LinearAdapter linearAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment_item,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //创建p层实例
        iPresenter=new IPresenterImpl(this);
        //获取资源id
        recyclerView=view.findViewById(R.id.recycler);
        //创建线性管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置线性方向（VERTICAL垂直方向，HORIZONTAL水平方向）
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);
        //创建适配器
        linearAdapter = new LinearAdapter(getActivity());
        //设置适配器
        recyclerView.setAdapter(linearAdapter);
        //设置分割线与线性布局的方向要保持一致
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), OrientationHelper.VERTICAL);
        //自定义分割线
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.recycler_divider_horizontal));
        recyclerView.addItemDecoration(dividerItemDecoration);
        //加载数据
        iPresenter.getRequeryData(Apis.URL_HOME,new HashMap<String, String>(),UserBean.class);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //调用适配器里点击事件的监听
        linearAdapter.setClickListener(new LinearAdapter.Click() {
            @Override
            public void onClick(int position) {
                linearAdapter.removeData(position);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof UserBean){
            UserBean userBean = (UserBean) o;
            linearAdapter.setList(userBean.getData());
        }
    }
}
