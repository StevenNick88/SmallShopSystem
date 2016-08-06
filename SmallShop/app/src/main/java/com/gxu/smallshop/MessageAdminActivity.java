package com.gxu.smallshop;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxu.smallshop.db.domain.BorrowedBook;
import com.gxu.smallshop.db.domain.Message;
import com.gxu.smallshop.pulltorefresh.task.CustomSoundPullEventListener;
import com.gxu.smallshop.pulltorefresh.task.CustomLastItemVisibleListener;
import com.gxu.smallshop.pulltorefresh.task.CustomRefreshListener;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.ImgUtils;
import com.gxu.smallshop.utils.JsonTools;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;


public class MessageAdminActivity  extends BaseActivity implements AdapterView.OnItemClickListener {

    private PullToRefreshListView message_list;
    private ListMessageBaseAdapter adapter;
    private List<Message> listData;
//    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_admin);
        adapter = new ListMessageBaseAdapter(MessageAdminActivity.this);
        message_list = (PullToRefreshListView) this.findViewById(R.id.message_list);

        // Set a listener to be invoked when the list should be refreshed.
        message_list.setOnRefreshListener(new CustomRefreshListener(
                MessageAdminActivity.this, listData, adapter, message_list));
        // Add an end-of-list listener
        message_list.setOnLastItemVisibleListener(new CustomLastItemVisibleListener(
                MessageAdminActivity.this));
        // Add Sound Event Listener
        message_list.setOnPullEventListener(new CustomSoundPullEventListener(
                MessageAdminActivity.this));

        //执行异步任务获取网络数据
        new QMessageTask().execute(CommonUrl.MESSAGES_URL);
        message_list.setOnItemClickListener(this);
    }


    public class QMessageTask extends AsyncTask<String, Void, List<Message>> {

        //执行耗时操作之前的准备
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog = ProgressDialog.show(QBookBorrowedActivity.this, "正在加载...", "系统正在处理您的请求");
        }

        //执行耗时操作
        @Override
        protected List<Message> doInBackground(String[] params) {
            String jsonString = HttpUtils.getJsonContent(params[0]);
            List<Message> list = JsonTools.getMessageList("messages", jsonString);

            return list;
        }

        //更新UI
        @Override
        protected void onPostExecute(List<Message> messageList) {
            super.onPostExecute(messageList);
            listData = messageList;
            adapter.setData(messageList);
            message_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
//            dialog.dismiss();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Message message = (Message) message_list.getRefreshableView().
                getItemAtPosition(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("message", message);
        launch(MessageAdminDetailActivity.class, bundle);
    }


    public class ListMessageBaseAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Message> list = null;

        public ListMessageBaseAdapter(Context context) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
        }

        public void setData(List<Message> list) {
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
                view = layoutInflater.inflate(R.layout.message_item, null);
            } else {
                view = convertView;
            }
            TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
            tv_message.setText(list.get(position).getMs_num());

            return view;
        }
    }
}
