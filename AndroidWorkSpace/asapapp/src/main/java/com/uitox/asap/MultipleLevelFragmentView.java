package com.uitox.asap;

import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.uitox.adapter.MyAdapter;
import com.uitox.adapter.ViewHolder;
import com.uitox.asapapp.FirstClassItem;
import com.uitox.asapapp.SecondClassItem;
import com.uitox.lib.ScreenUtils;
import com.uitox.view.BaseFragmentView;

import java.util.ArrayList;
import java.util.List;

public class MultipleLevelFragmentView extends BaseFragmentView {

    private TextView mainTab1TV;
    /**
     * 左侧一级分类的数据
     */
    private List<FirstClassItem> firstList;

    /**
     * 右侧二级分类的数据
     */
    private List<SecondClassItem> secondList;

    /**
     * 使用PopupWindow显示一级分类和二级分类
     */
    private PopupWindow popupWindow;

    /**
     * 左侧和右侧两个ListView
     */
    private ListView leftLV, rightLV;

    //弹出PopupWindow时背景变暗
    private View darkView, view;

    //弹出PopupWindow时，背景变暗的动画
    private Animation animIn, animOut;

    private int selectedPosition = 0;

    public MultipleLevelFragmentView() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.multiple_level_layout, container, false);

        mainTab1TV = (TextView) view.findViewById(R.id.main_tab1);
        darkView = view.findViewById(R.id.main_darkview);

        animIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_anim);
        animOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out_anim);

        initData();
        initPopup();

        //OnClickListenerImpl l = new OnClickListenerImpl();
        //mainTab1TV.setOnClickListener(l);

        return view;
    }

    @Override
    public void initData() {
        firstList = new ArrayList<FirstClassItem>();
        //1
        ArrayList<SecondClassItem> secondList1 = new ArrayList<SecondClassItem>();
        secondList1.add(new SecondClassItem(101, "自助餐"));
        secondList1.add(new SecondClassItem(102, "西餐"));
        secondList1.add(new SecondClassItem(103, "川菜"));
        firstList.add(new FirstClassItem(1, "美食", secondList1));
        //2
        ArrayList<SecondClassItem> secondList2 = new ArrayList<SecondClassItem>();
        secondList2.add(new SecondClassItem(201, "天津"));
        secondList2.add(new SecondClassItem(202, "北京"));
        secondList2.add(new SecondClassItem(203, "秦皇岛"));
        secondList2.add(new SecondClassItem(204, "沈阳"));
        secondList2.add(new SecondClassItem(205, "大连"));
        secondList2.add(new SecondClassItem(206, "哈尔滨"));
        secondList2.add(new SecondClassItem(207, "锦州"));
        secondList2.add(new SecondClassItem(208, "上海"));
        secondList2.add(new SecondClassItem(209, "杭州"));
        secondList2.add(new SecondClassItem(210, "南京"));
        secondList2.add(new SecondClassItem(211, "嘉兴"));
        secondList2.add(new SecondClassItem(212, "苏州"));
        firstList.add(new FirstClassItem(2, "旅游", secondList2));
        //3
        ArrayList<SecondClassItem> secondList3 = new ArrayList<SecondClassItem>();
        secondList3.add(new SecondClassItem(301, "南开区"));
        secondList3.add(new SecondClassItem(302, "和平区"));
        secondList3.add(new SecondClassItem(303, "河西区"));
        secondList3.add(new SecondClassItem(304, "河东区"));
        secondList3.add(new SecondClassItem(305, "滨海新区"));
        firstList.add(new FirstClassItem(3, "电影", secondList3));
        //4
        firstList.add(new FirstClassItem(4, "手机", new ArrayList<SecondClassItem>()));
        //5
        firstList.add(new FirstClassItem(5, "娱乐", null));

        //copy
        firstList.addAll(firstList);
    }

    private void initPopup() {
        popupWindow = new PopupWindow(getActivity());
        view = LayoutInflater.from(getActivity()).inflate(R.layout.multiple_popup_layout, null);
        leftLV = (ListView) view.findViewById(R.id.pop_listview_left);
        rightLV = (ListView) view.findViewById(R.id.pop_listview_right);

        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.setFocusable(true);

        popupWindow.setHeight(ScreenUtils.getScreenH(getActivity()) * 2 / 3);
        popupWindow.setWidth(ScreenUtils.getScreenW(getActivity()));

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkView.startAnimation(animOut);
                darkView.setVisibility(View.GONE);

                leftLV.setSelection(0);
                rightLV.setSelection(0);
            }
        });


        //为了方便扩展，这里的两个ListView均使用BaseAdapter.如果分类名称只显示一个字符串，建议改为ArrayAdapter.
        //加载一级分类
        final MyAdapter<FirstClassItem> firstAdapter = new MyAdapter<FirstClassItem>(getActivity(), firstList, R.layout.multiple_left_listview_item) {
            @Override
            public void convert(ViewHolder holder, FirstClassItem FirstClassItem, View convertView, int position) {

                TextView nameTV = holder.setTextReturnView(R.id.left_item_name, FirstClassItem.getName());

                if (FirstClassItem.getSecondList() != null && FirstClassItem.getSecondList().size() > 0) {
                    nameTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
                } else {
                    nameTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }
        };
        leftLV.setAdapter(firstAdapter);

        //加载左侧第一行对应右侧二级分类
        secondList = new ArrayList<SecondClassItem>();
        secondList.addAll(firstList.get(0).getSecondList());

        final MyAdapter<SecondClassItem> secondAdapter = new MyAdapter<SecondClassItem>(getActivity(), secondList, R.layout.multiple_right_listview_item) {
            @Override
            public void convert(ViewHolder holder, SecondClassItem SecondClassItem, View convertView, int position) {
                holder.setText(R.id.right_item_name, SecondClassItem.getName());
            }
        };
        rightLV.setAdapter(secondAdapter);

        //左侧ListView点击事件
        leftLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //二级数据
                List<SecondClassItem> list2 = firstList.get(position).getSecondList();
                //如果没有二级类，则直接跳转
                if (list2 == null || list2.size() == 0) {
                    popupWindow.dismiss();

                    int firstId = firstList.get(position).getId();
                    String selectedName = firstList.get(position).getName();
                    handleResult(firstId, -1, selectedName);
                    return;
                }

                //显示右侧二级分类
                updateSecondListView(list2, secondAdapter);
            }
        });

        //右侧ListView点击事件
        rightLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //关闭popupWindow，显示用户选择的分类
                popupWindow.dismiss();

                /*int firstPosition = firstAdapter.getSelectedPosition();
                int firstId = firstList.get(firstPosition).getId();
                int secondId = firstList.get(firstPosition).getSecondList().get(position).getId();
                String selectedName = firstList.get(firstPosition).getSecondList().get(position).getName();
                handleResult(firstId, secondId, selectedName);*/
            }
        });
    }

    //刷新右侧ListView
    private void updateSecondListView(List<SecondClassItem> list2, MyAdapter<SecondClassItem> secondAdapter) {
        secondList.clear();
        secondList.addAll(list2);
        secondAdapter.notifyDataSetChanged();
    }

    //处理点击结果
    private void handleResult(int firstId, int secondId, String selectedName) {
        String text = "first id:" + firstId + ",second id:" + secondId;
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
        mainTab1TV.setText(selectedName);
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    @Override
    public void initBundle(Bundle bundle) {

    }

    @Override
    public void updateUI() {

    }
}
