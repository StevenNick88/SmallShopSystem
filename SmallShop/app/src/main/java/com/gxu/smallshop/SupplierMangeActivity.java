package com.gxu.smallshop;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gxu.smallshop.db.domain.Agent;
import com.gxu.smallshop.db.domain.Supplier;
import com.gxu.smallshop.utils.Base64Utils;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.DESUtils;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.JsonService;
import com.gxu.smallshop.utils.JsonTools;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class SupplierMangeActivity extends BaseActivity implements View.OnClickListener {

    private EditText am_num_value, s_name_value, s_pwd_value,s_introduce_value;

    private String adminMangeTitle[] = {"供应商查询", "添加供应商"};
    private List<View> adminMangeViews;
    private List<View> mqPagerViews;

    private TabPageIndicator a_indicator;
    private ViewPager a_pager, admin_query_pager;
    private EditText s_num_value;
    private Button supplier_query, add_supplier;
    private uk.co.senab.photoview.PhotoViewAttacher mAttacher;
    private final int BQ_PAGER = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_mange);
        init();
    }

    private void init() {
        adminMangeViews = getList();
        a_indicator = (TabPageIndicator) this.findViewById(R.id.a_indicator);
        a_pager = (ViewPager) this.findViewById(R.id.a_pager);
        a_pager.setAdapter(new BKMangePagerAdapter());
        a_indicator.setViewPager(a_pager);
    }


    public List<View> getList() {
        List<View> list = new ArrayList<View>();
        View v1 = getLayoutInflater().inflate(R.layout.query_supplier, null);
        mqPagerViews = getViews();
        s_num_value = (EditText) v1.findViewById(R.id.s_num_value);
        supplier_query = (Button) v1.findViewById(R.id.supplier_query);
        supplier_query.setOnClickListener(this);
        admin_query_pager = (ViewPager) v1.findViewById(R.id.admin_query_pager);
        admin_query_pager.setAdapter(new BQPagerAdapter());

        View v2 = getLayoutInflater().inflate(R.layout.add_supplier, null);
        add_supplier = (Button) v2.findViewById(R.id.add_supplier);
        add_supplier.setOnClickListener(this);

        am_num_value = (EditText) v2.findViewById(R.id.am_num_value);
        s_name_value = (EditText) v2.findViewById(R.id.s_name_value);
        s_pwd_value = (EditText) v2.findViewById(R.id.s_pwd_value);
        s_introduce_value = (EditText) v2.findViewById(R.id.s_introduce_value);

        list.add(v1);
        list.add(v2);
        return list;
    }

    public List<View> getViews() {
        List<View> views = new ArrayList();
        Resources res = this.getResources();
        for (int i = 0; i < BQ_PAGER; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(res.getIdentifier("bg_img" + i, "drawable", this.getPackageName()));
            mAttacher = new uk.co.senab.photoview.PhotoViewAttacher(iv);
            LinearLayout ll = new LinearLayout(this);
            ll.setGravity(Gravity.CENTER);
            ll.addView(iv);
            views.add(ll);
            iv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
//                    Toast.makeText(SupplierMangeActivity.this, "点击了图片", Toast.LENGTH_SHORT).show();
                }
            });

        }
        return views;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.supplier_query:
                querySupplier();
                break;
            case R.id.add_supplier:
                addSupplier();
                break;
        }
    }

    private void querySupplier() {
        // 判断输入框是否为空
        if (s_num_value.getText().toString().equals("")) {
            msg(this, "提示", "代理编号不能为空！");
        }
        else  {
            msgWithEventDesDecryption(SupplierMangeActivity.this, "提示", "确定", "取消");
        }
    }

    @Override
    public void onPositiveEventDesDecryption(String desValuesStr) {
        super.onPositiveEventDesDecryption(desValuesStr);
        Map<String, String> map = new HashMap<>();
        map.put("s_num", s_num_value.getText().toString());

        //从服务端获取json的数据并封装为javabean
        String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.SUPPLIER_URL,
                JsonService.createJsonString(map));
        Supplier supplier = JsonTools.getSupplier("supplier", jsonString,desValuesStr);
        if (supplier != null && supplier.getS_num()!=null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("supplier_query", supplier);
            launch(SupplierInfoActivity.class, bundle);
        }else{
            msg(this, "失败信息", "对不起，没有这个供应商！");
        }
    }

    private void addSupplier() {
        // 判断输入框是否为空
        if (am_num_value.getText().toString().equals("")) {
            msg(this, "提示", "供应商编号不能为空！");
        }
        else if ( s_name_value.getText().toString().equals("")) {
            msg(this, "提示", "供应商姓名不能为空！");
        }
        else if ( s_pwd_value.getText().toString().equals("")) {
            msg(this, "提示", "供应商密码不能为空！");
        }
        else if ( s_introduce_value.getText().toString().equals("")) {
            msg(this, "提示", "供应商描述不能为空！");
        }else{
            msgWithEventDesEncryption(SupplierMangeActivity.this, "提示", "确定", "取消");
        }
    }


    @Override
    public void onPositiveEventDesEncryption(String desValuesStr) {
        super.onPositiveEventDesEncryption(desValuesStr);
        Map<String, String> map = new HashMap<>();
        Boolean flag = false;
        try {
            map.put("s_num", am_num_value.getText().toString());
            map.put("s_name",  Base64Utils.encode(DESUtils.desCrypto(s_name_value.getText().toString().getBytes(), desValuesStr)));
            map.put("s_pwd", Base64Utils.encode(DESUtils.desCrypto(s_pwd_value.getText().toString().getBytes(), desValuesStr)));
            map.put("s_introduce", Base64Utils.encode(DESUtils.desCrypto(s_introduce_value.getText().toString().getBytes(), desValuesStr)));
            flag = HttpUtils.sendJavaBeanToServer(CommonUrl.ADD_SUPPLIER_URL,
                    JsonService.createJsonString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag == true) {
            msgWithPositiveButton(this, "提示", "确定", "添加供应商成功！");
        } else {
            msg(this, "失败信息", "添加供应商失败！");
        }
    }

    @Override
    public void onPositiveEvent() {
        super.onPositiveEvent();
        launch(SupplierMangeActivity.class);
    }

    private class BQPagerAdapter extends PagerAdapter {

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public int getCount() {
            return mqPagerViews.size();
        }

        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            (container).removeView(mqPagerViews.get(position));
        }

        public Object instantiateItem(ViewGroup container, int position) {
            (container).addView(mqPagerViews.get(position));
            return mqPagerViews.get(position);
        }
    }


    private class BKMangePagerAdapter extends PagerAdapter {

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public int getCount() {
            return adminMangeTitle.length;
        }

        public CharSequence getPageTitle(int position) {
            return adminMangeTitle[position];
        }

        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            (container).removeView(adminMangeViews.get(position));
        }

        public Object instantiateItem(ViewGroup container, int position) {
            (container).addView(adminMangeViews.get(position));
            return adminMangeViews.get(position);
        }

    }

}
