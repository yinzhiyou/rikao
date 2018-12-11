package com.example.recycleview.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recycleview.Apis;
import com.example.recycleview.R;
import com.example.recycleview.adapter.FiowAdapter;
import com.example.recycleview.bean.UserBean;
import com.example.recycleview.presenter.IPresenterImpl;
import com.example.recycleview.view.DividerGridItemDecoration;
import com.example.recycleview.view.IView;

import java.util.HashMap;

public class FlowFragment extends Fragment implements IView {
    private RecyclerView recyclerView;
    private IPresenterImpl iPresenter;
    private final int mSpanCount=3;
    private FiowAdapter fiowAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.flow_fragment_item,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iPresenter=new IPresenterImpl(this);
        //获取资源id
        recyclerView=view.findViewById(R.id.recycler);
        //创建管理器
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(mSpanCount, StaggeredGridLayoutManager.VERTICAL);
        //设置布局管理器
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        //创建适配器
        fiowAdapter = new FiowAdapter(getActivity());
        recyclerView.setAdapter(fiowAdapter);
        DividerGridItemDecoration dividerGridItemDecoration = new DividerGridItemDecoration(getActivity());
        recyclerView.addItemDecoration(dividerGridItemDecoration);
        iPresenter.getRequeryData(Apis.URL_HOME,new HashMap<String, String>(),UserBean.class);
        fiowAdapter.setOnClickListener(new FiowAdapter.Click() {
            @Override
            public void onClick(int position) {
                fiowAdapter.removeData(position);
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
            fiowAdapter.setList(userBean.getData());
        }
    }
}
