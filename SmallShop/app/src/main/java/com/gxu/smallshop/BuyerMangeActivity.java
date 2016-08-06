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

import com.gxu.smallshop.db.domain.User;
import com.gxu.smallshop.utils.AESUtils;
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


public class BuyerMangeActivity extends BaseActivity implements View.OnClickListener {

    private EditText mu_num_value, u_name_value, u_pwd_value, u_email_value, u_phone_value,u_rebate_value;

    private String adminMangeTitle[] = {"买家查询", "添加买家"};
    private List<View> adminMangeViews;
    private List<View> mqPagerViews;

    private TabPageIndicator a_indicator;
    private ViewPager a_pager, admin_query_pager;
    private EditText u_num_value;
    private Button user_query, add_user;
    private uk.co.senab.photoview.PhotoViewAttacher mAttacher;//图片放大缩小的包装器
    private final int BQ_PAGER = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_mange);
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
        //买家查询View
        View v1 = getLayoutInflater().inflate(R.layout.query_buyer, null);
        mqPagerViews = getViews();
        u_num_value = (EditText) v1.findViewById(R.id.u_num_value);
        user_query = (Button) v1.findViewById(R.id.user_query);
        user_query.setOnClickListener(this);
        admin_query_pager = (ViewPager) v1.findViewById(R.id.admin_query_pager);
        admin_query_pager.setAdapter(new BQPagerAdapter());

        //买家入库View
        View v2 = getLayoutInflater().inflate(R.layout.add_buyer, null);
        add_user = (Button) v2.findViewById(R.id.add_user);
        add_user.setOnClickListener(this);

        mu_num_value = (EditText) v2.findViewById(R.id.mu_num_value);
        u_name_value = (EditText) v2.findViewById(R.id.u_name_value);
        u_pwd_value = (EditText) v2.findViewById(R.id.u_pwd_value);
        u_email_value = (EditText) v2.findViewById(R.id.u_email_value);
        u_phone_value = (EditText) v2.findViewById(R.id.u_phone_value);
        u_rebate_value = (EditText) v2.findViewById(R.id.u_rebate_value);

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
                    Toast.makeText(BuyerMangeActivity.this, "点击了图片", Toast.LENGTH_SHORT).show();
                }
            });

        }
        return views;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击查找买家
            case R.id.user_query:
                queryUser();
                break;
            case R.id.add_user:
                addUser();
                break;
        }
    }

    private void queryUser() {
        // 判断输入框是否为空
        if (u_num_value.getText().toString().equals("")) {
            msg(this, "提示", "买家编号不能为空！");
        } else {
            msgWithEventDesDecryption(BuyerMangeActivity.this, "提示", "确定", "取消");
        }
    }


    @Override
    public void onPositiveEventDesDecryption(String desValuesStr) {
        super.onPositiveEventDesDecryption(desValuesStr);
        Map<String, String> map = new HashMap<>();
        map.put("u_num", u_num_value.getText().toString());

        //从服务端获取json的数据并封装为javabean
        String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.USER_URL,
                JsonService.createJsonString(map));
        User user = JsonTools.getUser("user", jsonString,desValuesStr);
        if (user != null && user.getU_num()!=null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("user_query", user);
            launch(BuyerInfoActivity.class, bundle);
        } else {
            msg(this, "失败信息", "对不起，没有这个买家！");
        }
    }

    /**
     *
     */
    private void addUser() {
        // 判断输入框是否为空
        if (mu_num_value.getText().toString().equals("")) {
            msg(this, "提示", "买家编号不能为空！");
        } else if (u_name_value.getText().toString().equals("")) {
            msg(this, "提示", "买家姓名不能为空！");
        } else if (u_pwd_value.getText().toString().equals("")) {
            msg(this, "提示", "买家密码不能为空！");
        } else if (u_email_value.getText().toString().equals("")) {
            msg(this, "提示", "电子邮箱不能为空！");
        }
        else if (u_phone_value.getText().toString().equals("")) {
            msg(this, "提示", "手机号码不能为空！");
        } else if (u_rebate_value.getText().toString().equals("")) {
            msg(this, "提示", "买家折扣率不能为空！");
        }
        else {
            msgWithEventDesEncryption(BuyerMangeActivity.this, "提示", "确定", "取消");
        }
    }

    @Override
    public void onPositiveEventDesEncryption(String desValuesStr) {
        super.onPositiveEventDesEncryption(desValuesStr);
        Map<String, String> map = new HashMap<>();
        Boolean flag = false;
        try {
//            map.put("u_pwd", Base64Utils.encode(DESUtils.desCrypto(u_pwd_value.getText().toString().getBytes(), desValuesStr)));
//            map.put("u_name", Base64Utils.encode(AESUtils.encrypt(u_name_value.getText().toString(), desValuesStr)));
            map.put("u_num", mu_num_value.getText().toString());
            map.put("u_name", Base64Utils.encode(DESUtils.desCrypto(u_name_value.getText().toString().getBytes(), desValuesStr)));
            map.put("u_pwd", Base64Utils.encode(DESUtils.desCrypto(u_pwd_value.getText().toString().getBytes(), desValuesStr)));
            map.put("u_email", Base64Utils.encode(DESUtils.desCrypto(u_email_value.getText().toString().getBytes(), desValuesStr)));
            map.put("u_phone", Base64Utils.encode(DESUtils.desCrypto(u_phone_value.getText().toString().getBytes(), desValuesStr)));
            map.put("u_rebate", Base64Utils.encode(DESUtils.desCrypto(u_rebate_value.getText().toString().getBytes(), desValuesStr)));
            flag = HttpUtils.sendJavaBeanToServer(CommonUrl.ADD_USER_URL,
                    JsonService.createJsonString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag == true) {
            msg(this, "成功信息", "添加买家成功！");
            launch(BuyerMangeActivity.class);
        } else {
            msg(this, "失败信息", "添加买家失败！");
        }
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
