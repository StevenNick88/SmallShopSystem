package com.gxu.smallshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gxu.smallshop.db.domain.Admin;
import com.gxu.smallshop.db.domain.Agent;
import com.gxu.smallshop.db.domain.BuyedGoods;
import com.gxu.smallshop.db.domain.Goods;
import com.gxu.smallshop.db.domain.Log;
import com.gxu.smallshop.db.domain.Message;
import com.gxu.smallshop.db.domain.Supplier;
import com.gxu.smallshop.db.domain.User;
import com.gxu.smallshop.pulltorefresh.task.CustomLastItemVisibleListener;
import com.gxu.smallshop.pulltorefresh.task.CustomRefreshListener;
import com.gxu.smallshop.pulltorefresh.task.CustomSoundPullEventListener;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.CommonUtils;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.JsonTools;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SystemMangeActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private PullToRefreshListView system_list;
    private SystemBaseAdapter adapter;
    private String[] systemItemNames;
    private String[] systemItemUrls;
    List<Map<String, Object>> systemItemData;
    private ProgressDialog dialog,dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_mange);
        systemItemNames = this.getResources().getStringArray(R.array.system_mange_item);
//        systemItemUrls =this.getResources().getStringArray(R.array.online_books_item_url);
        adapter = new SystemBaseAdapter(this);
        systemItemData = getSystemData();
        system_list = (PullToRefreshListView) this.findViewById(R.id.system_list);

        // Set a listener to be invoked when the list should be refreshed.
        system_list.setOnRefreshListener(new CustomRefreshListener(
                SystemMangeActivity.this, systemItemData, adapter, system_list));
        // Add an end-of-list listener
        system_list.setOnLastItemVisibleListener(new CustomLastItemVisibleListener(
                SystemMangeActivity.this));
        // Add Sound Event Listener
        system_list.setOnPullEventListener(new CustomSoundPullEventListener(
                SystemMangeActivity.this));

        adapter.setData(systemItemData);
        system_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        system_list.setOnItemClickListener(this);

    }

    private List<Map<String, Object>> getSystemData() {
        Resources res = this.getResources();
        //每个数据项中的数据由Map来存储
        List<Map<String, Object>> data = new ArrayList();
        for (int i = 0; i < systemItemNames.length; i++) {
            Map<String, Object> map = new HashMap();
            map.put("img", res.getIdentifier("system_item", "drawable", this.getPackageName()));
            map.put("name", systemItemNames[i]);

            data.add(map);
        }
        return data;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1:
                launch(ChangePasswordActivity.class);
                break;
            case 2:
                launch(LogMangeActivity.class);
                break;
            case 3:
                dataBackup();
                break;
            case 4:
                launch(OutdatedDeleteActivity.class);
                break;
            case 5:
                msgWithEvent(SystemMangeActivity.this, "提示", "确定", "取消", "这将清空系统的所有数据，确定要这么做吗？");
                break;
            default:
                break;
        }

    }

    @Override
    public void onPositiveEvent() {
        super.onPositiveEvent();
        new SystemInitializationTask().execute(CommonUrl.SYSTEM_INITIALIZATION_URL);
    }
    //系统初始化
    public class SystemInitializationTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog2 = ProgressDialog.show(SystemMangeActivity.this, "提示", "系统正在处理您的请求...请稍后！");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean flag = HttpUtils.sendJavaBeanToServer(params[0], "");
            return flag;
        }

        @Override
        protected void onPostExecute(Boolean flag) {
            super.onPostExecute(flag);
            dialog2.dismiss();
            if (flag == true) {
                msg(SystemMangeActivity.this, "成功信息", "系统初始化成功！");
            } else {
                msg(SystemMangeActivity.this, "失败信息", "系统初始化失败！");
            }
        }

    }

    private void dataBackup() {
        //执行异步任务获取网络数据
        new QDataTask().execute(CommonUrl.backup_urls);
    }

    public class QDataTask extends AsyncTask<String, Void, List<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(SystemMangeActivity.this, "正在加载...", "系统正在处理您的请求...");
        }

        @Override
        protected List<String> doInBackground(String... params) {
            List<String> list = new ArrayList<>();

            String jsonString0 = HttpUtils.getJsonContent(params[0]);
//            List<Admin> adminList = JsonTools.getAdmins("admins", jsonString1);
            String jsonString1 = HttpUtils.getJsonContent(params[1]);
//            List<Agent> agentList = JsonTools.getAgents("agents", jsonString2);
            String jsonString2 = HttpUtils.getJsonContent(params[2]);
//            List<BuyedGoods> buyedGoodsList = JsonTools.getBuyedGoods("buyedgoods", jsonString3);
            String jsonString3 = HttpUtils.getJsonContent(params[3]);
//            List<Goods> goodsList = JsonTools.getGoodsList("goods_list", jsonString4);
            String jsonString4 = HttpUtils.getJsonContent(params[4]);
//            List<Log> logList = JsonTools.getLogList("logs", jsonString5);
            String jsonString5 = HttpUtils.getJsonContent(params[5]);
//            List<Message> messageList = JsonTools.getMessageList("messages", jsonString6);
            String jsonString6 = HttpUtils.getJsonContent(params[6]);
//            List<Supplier> supplierList = JsonTools.getListSupplier("suppliers", jsonString7);
            String jsonString7 = HttpUtils.getJsonContent(params[7]);
//            List<User> userList = JsonTools.getListUser("users", jsonString8);

            list.add(jsonString0);
            list.add(jsonString1);
            list.add(jsonString2);
            list.add(jsonString3);
            list.add(jsonString4);
            list.add(jsonString5);
            list.add(jsonString6);
            list.add(jsonString7);

            return list;
        }

        @Override
        protected void onPostExecute(List<String> lists) {
            super.onPostExecute(lists);
            dialog.dismiss();
            byte[] bytes= CommonUtils.ChangeListToByte(lists);
            String path=CommonUtils.saveFileToSdcard("smallshop.txt",bytes);
            if (!path.equals("")){
                msg(SystemMangeActivity.this, "提示", "备份成功,文件存储于"+path+"目录下！");
            }
        }

    }

    private class SystemBaseAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Map<String, Object>> list = null;

        public SystemBaseAdapter(Context context) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
        }

        public void setData(List<Map<String, Object>> list) {
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
                view = layoutInflater.inflate(R.layout.system_list_item, null);
            } else {
                view = convertView;
            }
            ImageView system_item_img = (ImageView) view.findViewById(R.id.system_item_img);
            TextView system_list_item_name = (TextView) view.findViewById(R.id.system_list_item_name);
            system_item_img.setImageResource((Integer)list.get(position).get("img"));
            system_list_item_name.setText((String) list.get(position).get("name"));
            return view;
        }
    }


}
