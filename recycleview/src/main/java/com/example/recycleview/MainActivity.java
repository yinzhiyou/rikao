package com.example.recycleview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.recycleview.fragment.FlowFragment;
import com.example.recycleview.fragment.GridFragment;
import com.example.recycleview.fragment.ListViewFragment;
import com.example.recycleview.presenter.IPresenterImpl;
import com.example.recycleview.view.IView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private ViewPager viewPager;
    private List<Fragment> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        //获取资源id
        radioGroup=findViewById(R.id.radio);
        viewPager=findViewById(R.id.viewpager);
        //创建Fragment
        list=new ArrayList<>();
        list.add(new ListViewFragment());
        list.add(new GridFragment());
        list.add(new FlowFragment());

        //创建适配器
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        //点击事件
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.list:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.grid:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.flow:
                        viewPager.setCurrentItem(2);
                        break;
                        default:
                            break;
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        radioGroup.check(R.id.list);
                        break;
                    case 1:
                        radioGroup.check(R.id.grid);
                        break;
                    case 2:
                        radioGroup.check(R.id.flow);
                        break;
                        default:
                            break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


}
