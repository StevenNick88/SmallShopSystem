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


public class AgentMangeActivity extends BaseActivity implements View.OnClickListener {

    private EditText am_num_value, a_name_value, a_pwd_value, a_introduce_value,
            a_level_value,a_rebate_value;

    private String adminMangeTitle[] = {"代理查询", "添加代理"};
    private List<View> adminMangeViews;
    private List<View> mqPagerViews;

    private TabPageIndicator a_indicator;
    private ViewPager a_pager, admin_query_pager;
    private EditText a_num_value;
    private Button agent_query, add_agent;
    private uk.co.senab.photoview.PhotoViewAttacher mAttacher;
    private final int BQ_PAGER = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_mange);
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
        View v1 = getLayoutInflater().inflate(R.layout.query_agent, null);
        mqPagerViews = getViews();
        a_num_value = (EditText) v1.findViewById(R.id.a_num_value);
        agent_query = (Button) v1.findViewById(R.id.agent_query);
        agent_query.setOnClickListener(this);
        admin_query_pager = (ViewPager) v1.findViewById(R.id.admin_query_pager);
        admin_query_pager.setAdapter(new BQPagerAdapter());

        View v2 = getLayoutInflater().inflate(R.layout.add_agent, null);
        add_agent = (Button) v2.findViewById(R.id.add_agent);
        add_agent.setOnClickListener(this);

        am_num_value = (EditText) v2.findViewById(R.id.am_num_value);
        a_name_value = (EditText) v2.findViewById(R.id.a_name_value);
        a_pwd_value = (EditText) v2.findViewById(R.id.a_pwd_value);
        a_introduce_value = (EditText) v2.findViewById(R.id.a_introduce_value);
        a_level_value = (EditText) v2.findViewById(R.id.a_level_value);
        a_rebate_value = (EditText) v2.findViewById(R.id.a_rebate_value);

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
                    Toast.makeText(AgentMangeActivity.this, "点击了图片", Toast.LENGTH_SHORT).show();
                }
            });

        }
        return views;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.agent_query:
                queryAgent();
                break;
            case R.id.add_agent:
                addAgent();
                break;
        }
    }

    private void queryAgent() {
        // 判断输入框是否为空
        if (a_num_value.getText().toString().equals("")) {
            msg(this, "提示", "代理编号不能为空！");
        } else {
            msgWithEventDesDecryption(AgentMangeActivity.this, "提示", "确定", "取消");
        }
    }

    @Override
    public void onPositiveEventDesDecryption(String desValuesStr) {
        super.onPositiveEventDesDecryption(desValuesStr);
        Map<String, String> map = new HashMap<>();
        map.put("a_num", a_num_value.getText().toString());

        //从服务端获取json的数据并封装为javabean
        String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.AGENT_URL,
                JsonService.createJsonString(map));
        Agent agent = JsonTools.getAgent("agent", jsonString, desValuesStr);
        if (agent != null && agent.getA_num()!=null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("agent_query", agent);
            launch(AgentInfoActivity.class, bundle);
        } else {
            msg(this, "失败信息", "对不起，没有这个代理！");
        }
    }

    private void addAgent() {
        // 判断输入框是否为空
        if (am_num_value.getText().toString().equals("")) {
            msg(this, "提示", "代理编号不能为空！");
        } else if (a_name_value.getText().toString().equals("")) {
            msg(this, "提示", "代理姓名不能为空！");
        } else if (a_pwd_value.getText().toString().equals("")) {
            msg(this, "提示", "代理密码不能为空！");
        } else if (a_level_value.getText().toString().equals("")) {
            msg(this, "提示", "代理等级不能为空！");
        } else if (a_rebate_value.getText().toString().equals("")) {
            msg(this, "提示", "代理折扣率不能为空！");
        } else if (a_introduce_value.getText().toString().equals("")) {
            msg(this, "提示", "代理描述不能为空！");
        }
        else {
            msgWithEventDesEncryption(AgentMangeActivity.this, "提示", "确定", "取消");

        }
    }

    @Override
    public void onPositiveEventDesEncryption(String desValuesStr) {
        super.onPositiveEventDesEncryption(desValuesStr);
        Map<String, String> map = new HashMap<>();
        Boolean flag = false;
        try {
            map.put("a_num", am_num_value.getText().toString());
            map.put("a_name", Base64Utils.encode(DESUtils.desCrypto(a_name_value.getText().toString().getBytes(), desValuesStr)));
            map.put("a_pwd", Base64Utils.encode(DESUtils.desCrypto(a_pwd_value.getText().toString().getBytes(), desValuesStr)));
            map.put("a_level", Base64Utils.encode(DESUtils.desCrypto(a_level_value.getText().toString().getBytes(), desValuesStr)));
            map.put("a_rebate", Base64Utils.encode(DESUtils.desCrypto(a_rebate_value.getText().toString().getBytes(), desValuesStr)));
            map.put("a_introduce", Base64Utils.encode(DESUtils.desCrypto(a_introduce_value.getText().toString().getBytes(), desValuesStr)));
            flag = HttpUtils.sendJavaBeanToServer(CommonUrl.ADD_AGENT_URL,
                    JsonService.createJsonString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag == true) {
            msg(this, "成功信息", "添加代理成功！");
            launch(AgentMangeActivity.class);
        } else {
            msg(this, "失败信息", "添加代理失败！");
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
