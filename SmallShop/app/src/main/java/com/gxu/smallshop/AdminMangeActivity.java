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

import com.gxu.smallshop.db.domain.Admin;
import com.gxu.smallshop.utils.Base64Utils;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.DESUtils;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.JsonService;
import com.gxu.smallshop.utils.JsonTools;
import com.gxu.smallshop.utils.PBECoder;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminMangeActivity extends BaseActivity implements View.OnClickListener {

    private EditText m_num_value, m_name_value, m_pwd_value, m_remark_value;

    private String adminMangeTitle[] = {"管理员信息查询", "添加管理员"};
    private List<View> adminMangeViews;
    private List<View> mqPagerViews;

    private TabPageIndicator a_indicator;
    private ViewPager a_pager, admin_query_pager;
    private EditText mq_num_value;
    private Button admin_query, add_admin;
    private uk.co.senab.photoview.PhotoViewAttacher mAttacher;//图片放大缩小的包装器
    private final int BQ_PAGER = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mange);
        init();
    }

    private void init() {
        adminMangeViews = getList();
        a_indicator = (TabPageIndicator) this.findViewById(R.id.a_indicator);
        a_pager = (ViewPager) this.findViewById(R.id.a_pager);
        //bk_pager适配器
        a_pager.setAdapter(new BKMangePagerAdapter());
        a_indicator.setViewPager(a_pager);
    }


    public List<View> getList() {
        List<View> list = new ArrayList<View>();
        //管理员信息查询View
        View v1 = getLayoutInflater().inflate(R.layout.query_admin, null);
        mqPagerViews = getViews();
        mq_num_value = (EditText) v1.findViewById(R.id.mq_num_value);
        admin_query = (Button) v1.findViewById(R.id.admin_query);
        admin_query.setOnClickListener(this);
        admin_query_pager = (ViewPager) v1.findViewById(R.id.admin_query_pager);
        admin_query_pager.setAdapter(new BQPagerAdapter());

        //添加管理员View
        View v2 = getLayoutInflater().inflate(R.layout.add_admin, null);
        add_admin = (Button) v2.findViewById(R.id.add_admin);
        add_admin.setOnClickListener(this);

        m_num_value = (EditText) v2.findViewById(R.id.m_num_value);
        m_name_value = (EditText) v2.findViewById(R.id.m_name_value);
        m_pwd_value = (EditText) v2.findViewById(R.id.m_pwd_value);
        m_remark_value = (EditText) v2.findViewById(R.id.m_remark_value);

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
            //实现图片放大缩小
            mAttacher = new uk.co.senab.photoview.PhotoViewAttacher(iv);
            LinearLayout ll = new LinearLayout(this);
            ll.setGravity(Gravity.CENTER);
            ll.addView(iv);
            views.add(ll);
            //点击图片事件
            iv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(AdminMangeActivity.this, "点击了图片", Toast.LENGTH_SHORT).show();
                }
            });

        }
        return views;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击查找管理员信息
            case R.id.admin_query:
                queryAdmin();
                break;
            //点击添加管理员
            case R.id.add_admin:
                addAdmin();
                break;
        }
    }

    private void queryAdmin() {
        // 判断输入框是否为空
        if (mq_num_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员编号不能为空！");
        } else {
            msgWithEventDesDecryption(AdminMangeActivity.this, "提示", "确定", "取消");
        }
    }

    @Override
    public void onPositiveEventDesDecryption(String desValuesStr) {
        super.onPositiveEventDesDecryption(desValuesStr);
        Map<String, String> map = new HashMap<>();
        map.put("m_num", mq_num_value.getText().toString());

        //从服务端获取json的数据并封装为javabean
        String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.ADMIN_URL,
                JsonService.createJsonString(map));
        Admin admin = JsonTools.getAdmin("admin", jsonString, desValuesStr);
        if (admin != null && admin.getM_num()!=null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("admin_query", admin);
            launch(AdminInfoActivity.class, bundle);
        } else {
            msg(this, "失败信息", "对不起，没有这个管理员！");
        }
    }

    private void addAdmin() {
        // 判断输入框是否为空
        if (m_num_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员编号不能为空！");
        } else if (m_name_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员姓名不能为空！");
        } else if (m_pwd_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员密码不能为空！");
        } else if (m_remark_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员备注不能为空！");
        } else {
            msgWithEventDesEncryption(AdminMangeActivity.this, "提示", "确定", "取消");
        }
    }

    //点击对话框的确定触发的方法
    @Override
    public void onPositiveEventDesEncryption(String desValuesStr) {
        super.onPositiveEventDesEncryption(desValuesStr);
        Map<String, String> map = new HashMap<>();
        //加密数据 编号不能加密
        Boolean flag = false;
        try {
            map.put("m_num", m_num_value.getText().toString());
            //加密 将DES加密后的密文再进行整体的base64加密
            map.put("m_name", Base64Utils.encode(DESUtils.desCrypto(m_name_value.getText().toString().getBytes(), desValuesStr)));
            map.put("m_pwd", Base64Utils.encode(DESUtils.desCrypto(m_pwd_value.getText().toString().getBytes(), desValuesStr)));
            map.put("m_remark", Base64Utils.encode(DESUtils.desCrypto(m_remark_value.getText().toString().getBytes(), desValuesStr)));
            flag = HttpUtils.sendJavaBeanToServer(CommonUrl.ADD_ADMIN_URL,
                    JsonService.createJsonString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag == true) {
            msg(this, "成功信息", "添加管理员成功！");
            launch(AdminMangeActivity.class);
        } else {
            msg(this, "失败信息", "添加管理员失败！");
        }
    }

    private class BQPagerAdapter extends PagerAdapter {

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public int getCount() {
            return mqPagerViews.size();
        }

        //显示界面方法
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            (container).removeView(mqPagerViews.get(position));
        }

        //销毁界面方法
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

        //显示界面方法
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            (container).removeView(adminMangeViews.get(position));
        }

        //销毁界面方法
        public Object instantiateItem(ViewGroup container, int position) {
            (container).addView(adminMangeViews.get(position));
            return adminMangeViews.get(position);
        }

    }

}
