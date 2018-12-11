package com.example.recycleview.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recycleview.Apis;
import com.example.recycleview.R;
import com.example.recycleview.adapter.GridAdapter;
import com.example.recycleview.bean.UserBean;
import com.example.recycleview.presenter.IPresenterImpl;
import com.example.recycleview.view.DividerGridItemDecoration;
import com.example.recycleview.view.IView;

import java.util.HashMap;

public class GridFragment extends Fragment implements IView {
    private RecyclerView recyclerView;
    private final int mSpanCount=3;
    private GridAdapter gridAdapter;
    private IPresenterImpl iPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grid_fragment_item,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iPresenter=new IPresenterImpl(this);
        //获取资源id
        recyclerView=view.findViewById(R.id.recycler);
        //创建管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), mSpanCount);
        //设置方向
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置管理器
        recyclerView.setLayoutManager(gridLayoutManager);
        //创建适配器
        gridAdapter = new GridAdapter(getActivity());
        recyclerView.setAdapter(gridAdapter);
        //设置分割线
        DividerGridItemDecoration dividerGridItemDecoration = new DividerGridItemDecoration(getActivity());
        recyclerView.addItemDecoration(dividerGridItemDecoration);
        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //获取数据
        iPresenter.getRequeryData(Apis.URL_HOME,new HashMap<String, String>(),UserBean.class);
        //监听
        gridAdapter.setonClickListener(new GridAdapter.Click() {
            @Override
            public void onClick(int position) {
                gridAdapter.removeData(position);
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
            gridAdapter.setList(userBean.getData());
        }
    }
}
