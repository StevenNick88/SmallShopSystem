package com.gxu.smallshop;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gxu.smallshop.db.domain.Log;
import com.gxu.smallshop.db.domain.Message;
import com.gxu.smallshop.pulltorefresh.task.CustomSoundPullEventListener;
import com.gxu.smallshop.pulltorefresh.task.CustomLastItemVisibleListener;
import com.gxu.smallshop.pulltorefresh.task.CustomRefreshListener;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.JsonTools;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;


public class LogMangeActivity  extends BaseActivity implements AdapterView.OnItemClickListener {

    private PullToRefreshListView log_list;
    private ListLogBaseAdapter adapter;
    private List<Log> listData;
//    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_mange);
        adapter = new ListLogBaseAdapter(LogMangeActivity.this);
        log_list = (PullToRefreshListView) this.findViewById(R.id.log_list);

        // Set a listener to be invoked when the list should be refreshed.
        log_list.setOnRefreshListener(new CustomRefreshListener(
                LogMangeActivity.this, listData, adapter, log_list));
        // Add an end-of-list listener
        log_list.setOnLastItemVisibleListener(new CustomLastItemVisibleListener(
                LogMangeActivity.this));
        // Add Sound Event Listener
        log_list.setOnPullEventListener(new CustomSoundPullEventListener(
                LogMangeActivity.this));

        //执行异步任务获取网络数据
        new QLogsTask().execute(CommonUrl.LOGS_URL);
        log_list.setOnItemClickListener(this);
    }


    public class QLogsTask extends AsyncTask<String, Void, List<Log>> {

        //执行耗时操作之前的准备
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog = ProgressDialog.show(QBookBorrowedActivity.this, "正在加载...", "系统正在处理您的请求");
        }

        //执行耗时操作
        @Override
        protected List<Log> doInBackground(String[] params) {
            String jsonString = HttpUtils.getJsonContent(params[0]);
            List<Log> list = JsonTools.getLogList("logs", jsonString);

            return list;
        }

        //更新UI
        @Override
        protected void onPostExecute(List<Log> logList) {
            super.onPostExecute(logList);
            listData = logList;
            adapter.setData(logList);
            log_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
//            dialog.dismiss();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log log = (Log) log_list.getRefreshableView().
                getItemAtPosition(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("log", log);
        launch(LogDetailActivity.class, bundle);
    }


    public class ListLogBaseAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Log> list = null;

        public ListLogBaseAdapter(Context context) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
        }

        public void setData(List<Log> list) {
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
                view = layoutInflater.inflate(R.layout.log_item, null);
            } else {
                view = convertView;
            }
            TextView tv_log = (TextView) view.findViewById(R.id.tv_log);
            tv_log.setText(list.get(position).getLog_num());

            return view;
        }
    }
}
