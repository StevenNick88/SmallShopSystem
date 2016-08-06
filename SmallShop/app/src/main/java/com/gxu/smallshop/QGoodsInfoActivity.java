package com.gxu.smallshop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gxu.smallshop.db.domain.BuyedGoods;
import com.gxu.smallshop.db.domain.Goods;
import com.gxu.smallshop.pulltorefresh.task.CustomLastItemVisibleListener;
import com.gxu.smallshop.pulltorefresh.task.CustomRefreshListener;
import com.gxu.smallshop.pulltorefresh.task.CustomSoundPullEventListener;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.ImgUtils;
import com.gxu.smallshop.utils.JsonService;
import com.gxu.smallshop.utils.JsonTools;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QGoodsInfoActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private EditText g_name_value, g_type_value;

    private String[] qGoodsTitle = {"简单查询", "高级查询"};
    private List<View> qGoodsView;
    private List<String> sq_data;
    private TabPageIndicator qbi_indicator;
    private ViewPager qbi_pager;
    private Spinner sq_spinner;
    private EditText sq_edit;
    private Button sq_button, sq_complex_button;
    private String sq_query;


    private List<Goods> goodsList;
    private PullToRefreshListView goods_list;
    private ListGoodsBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qgoods_info);
        qbi_indicator = (TabPageIndicator) this.findViewById(R.id.qbi_indicator);
        qGoodsView = getView();
        qbi_pager = (ViewPager) this.findViewById(R.id.qbi_pager);
        qbi_pager.setAdapter(new QGoodsPagerAdapter());
        qbi_indicator.setViewPager(qbi_pager);
    }

    public List<Goods> getGoodsList() {
        List<Goods> goodsList=null;
        try {
            String jsonString = HttpUtils.getJsonContent(CommonUrl.GOODS_LIST_URL);
            goodsList = JsonTools.getGoodsList("goods_list", jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("detail_goods", (Goods)goods_list.getRefreshableView().getItemAtPosition(position));
        launch(QGoodsInfoListDetailActivity.class, bundle);

    }

    private class ListGoodsBaseAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Goods> list = null;

        public ListGoodsBaseAdapter(Context context) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
        }

        public void setData(List<Goods> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.goods_list_item, null);
            } else {
                view = convertView;
            }
            TextView g_name_value = (TextView) view.findViewById(R.id.g_name_value);
            TextView g_price_value = (TextView) view.findViewById(R.id.g_price_value);
            TextView g_num_value = (TextView) view.findViewById(R.id.g_num_value);
            final ImageView list_image=(ImageView)view.findViewById(R.id.list_image);
            g_name_value.setText(list.get(position).getG_name());
            g_price_value.setText(list.get(position).getG_price());
            g_num_value.setText(list.get(position).getG_num());

            //开启另外一个线程去加载图片
            String img_url=CommonUrl.LOAD_IMG+list.get(position).getG_img();
            ImgUtils.loadImage(new ImgUtils.ImageCallBack() {
                @Override
                public void getBitmap(Bitmap bitmap) {
                    list_image.setImageBitmap(bitmap);
                }
            }, img_url);

            return view;
        }
    }

    public List<View> getView() {
        List<View> list = new ArrayList<>();
        //简单查询View
        View v1 = LayoutInflater.from(QGoodsInfoActivity.this).inflate(R.layout.simple_q_goods, null);
        sq_spinner = (Spinner) v1.findViewById(R.id.sq_spinner);
        sq_edit = (EditText) v1.findViewById(R.id.sq_edit);
        sq_button = (Button) v1.findViewById(R.id.sq_button);
        sq_button.setOnClickListener(this);
        sq_data = getData();
        sq_spinner.setAdapter(new ArrayAdapter(QGoodsInfoActivity.this,
                android.R.layout.simple_spinner_item, sq_data));

        adapter=new ListGoodsBaseAdapter(QGoodsInfoActivity.this);
        goodsList =getGoodsList();
        goods_list =(PullToRefreshListView)v1.findViewById(R.id.goods_list);
        // Set a listener to be invoked when the list should be refreshed.
        goods_list.setOnRefreshListener(new CustomRefreshListener(
                QGoodsInfoActivity.this, goodsList, adapter, goods_list));
        // Add an end-of-list listener
        goods_list.setOnLastItemVisibleListener(new CustomLastItemVisibleListener(
                QGoodsInfoActivity.this));
        // Add Sound Event Listener
        goods_list.setOnPullEventListener(new CustomSoundPullEventListener(
                QGoodsInfoActivity.this));

        adapter.setData(goodsList);
        goods_list.setAdapter(adapter);
        goods_list.setOnItemClickListener(this);



        //高级查询View
        View v2 = LayoutInflater.from(QGoodsInfoActivity.this).inflate(R.layout.complex_q_goods, null);
        g_name_value = (EditText) v2.findViewById(R.id.g_name_value);
        g_type_value = (EditText) v2.findViewById(R.id.g_type_value);
        sq_complex_button = (Button) v2.findViewById(R.id.sq_complex_button);
        sq_complex_button.setOnClickListener(this);

        list.add(v1);
        list.add(v2);
        return list;
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        list.add("商品编号");
        list.add("商品名称");
        list.add("商品类型");
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //简单查询
            case R.id.sq_button:
                //根据不同的查询方式查询
                switch (sq_spinner.getSelectedItem().toString()) {

                    case "商品编号":
                        queryGoodsWithG_num();
                        break;
                    case "商品名称":
                        queryGoodsWithG_name();
                        break;
                    case "商品类型":
                        queryGoodsWithG_type();
                        break;
                }
                break;
            //高级查询
            case R.id.sq_complex_button:
                queryGoodsWithG_name_type();
                break;

        }
    }

    private void queryGoodsWithG_name_type() {
        Map<String, String> map = new HashMap<>();
        map.put("g_name", g_name_value.getText().toString());
        map.put("g_type", g_type_value.getText().toString());

        //从服务端获取json的数据并封装为javabean
        String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.GOODS_URL_WITH_G_NAME_TYPE,
                JsonService.createJsonString(map));
        List<Goods> list = JsonTools.getGoodsList("q_goods_with_g_name_type", jsonString);
        if (list != null) {
            launch(QGoodsInfoListActivity.class, new Intent(), "q_goods_with_g_name_type", list);
        } else {
            msg(this, "失败信息", "对不起，没有这件商品！");
        }

    }


    private void queryGoodsWithG_num() {
        Map<String, String> map = new HashMap<>();
        map.put("g_num", sq_edit.getText().toString());
        //从服务端获取json的数据并封装为javabean
        String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.GOODS_URL,
                JsonService.createJsonString(map));
        Goods goods = JsonTools.getGoods("goods", jsonString);
        List<Goods> list = new ArrayList<Goods>();
        list.add(goods);
        if (list != null) {
            launch(QGoodsInfoListActivity.class, new Intent(), "list_goods_withG_num", list);
        } else {
            msg(this, "失败信息", "对不起，没有这件商品！");
        }
    }

    private void queryGoodsWithG_name() {
        Map<String, String> map = new HashMap<>();
        map.put("g_name", sq_edit.getText().toString());
        //从服务端获取json的数据并封装为javabean
        String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.GOODS_URL_WITH_G_NAME,
                JsonService.createJsonString(map));
        List<Goods> list = JsonTools.getGoodsList("list_goods_withG_name", jsonString);
        if (list != null) {
            launch(QGoodsInfoListActivity.class, new Intent(), "list_goods_withG_name", list);
        } else {
            msg(this, "失败信息", "对不起，没有这本书！");
        }
    }


    private void queryGoodsWithG_type() {
        Map<String, String> map = new HashMap<>();
        map.put("g_type", sq_edit.getText().toString());
        //从服务端获取json的数据并封装为javabean
        String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.GOODS_URL_WITH_G_TYPE,
                JsonService.createJsonString(map));
        List<Goods> list = JsonTools.getGoodsList("list_goods_withG_type", jsonString);
        if (list != null) {
            launch(QGoodsInfoListActivity.class, new Intent(), "list_goods_withG_type", list);
        } else {
            msg(this, "失败信息", "对不起，没有这本书！");
        }

    }


    private class QGoodsPagerAdapter extends PagerAdapter {
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public int getCount() {
            return qGoodsTitle.length;
        }

        public CharSequence getPageTitle(int position) {
            return qGoodsTitle[position];
        }

        //显示界面方法
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            (container).removeView(qGoodsView.get(position));
        }

        //销毁界面方法
        public Object instantiateItem(ViewGroup container, int position) {
            (container).addView(qGoodsView.get(position));
            return qGoodsView.get(position);
        }
    }


}
