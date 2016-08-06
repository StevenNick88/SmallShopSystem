package com.gxu.smallshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxu.smallshop.db.domain.BorrowedBook;
import com.gxu.smallshop.db.domain.BuyedGoods;
import com.gxu.smallshop.pulltorefresh.task.CustomSoundPullEventListener;
import com.gxu.smallshop.pulltorefresh.task.CustomLastItemVisibleListener;
import com.gxu.smallshop.pulltorefresh.task.CustomRefreshListener;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.ImgUtils;
import com.gxu.smallshop.utils.JsonService;
import com.gxu.smallshop.utils.JsonTools;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QBuyedGoodsAdminActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private PullToRefreshListView admin_buyed_goods;
    private ListBorrowedBookBaseAdapter adapter;
    private List<BuyedGoods> listData;
    private ProgressDialog dialog;
    private EditText buyed_edit;
    private Button q_buyer_goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qgoods_admin_buyed);
        adapter = new ListBorrowedBookBaseAdapter(QBuyedGoodsAdminActivity.this);
        buyed_edit = (EditText) this.findViewById(R.id.buyed_edit);
        q_buyer_goods = (Button) this.findViewById(R.id.q_buyer_goods);
        q_buyer_goods.setOnClickListener(this);
        admin_buyed_goods = (PullToRefreshListView) this.findViewById(R.id.admin_buyed_goods);

        // Set a listener to be invoked when the list should be refreshed.
        admin_buyed_goods.setOnRefreshListener(new CustomRefreshListener(
                QBuyedGoodsAdminActivity.this, listData, adapter, admin_buyed_goods));
        // Add an end-of-list listener
        admin_buyed_goods.setOnLastItemVisibleListener(new CustomLastItemVisibleListener(
                QBuyedGoodsAdminActivity.this));
        // Add Sound Event Listener
        admin_buyed_goods.setOnPullEventListener(new CustomSoundPullEventListener(
                QBuyedGoodsAdminActivity.this));

        //执行异步任务获取网络数据
        new QBookTask().execute(CommonUrl.BORROW_BOOKS_URL);
        admin_buyed_goods.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        queryBuyedGoods();
    }

    private void queryBuyedGoods() {
        // 判断输入框是否为空
        if (buyed_edit.getText().toString().equals("")) {
            msg(this, "提示", "购买者编号不能为空！");
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("user_num", buyed_edit.getText().toString());
            //从服务端获取json的数据并封装为javabean
            String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.BUY_GOODS_WITH_USER_NUM_URL,
                    JsonService.createJsonString(map));
            List<BuyedGoods> list = JsonTools.getBuyedGoods("buygoods_with_buyer_num", jsonString);
            if (list != null && !list.isEmpty()) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("buygoods_with_buyer_num", (Serializable) list);
                launch(QGoodsAdminBuyedListActivity.class, bundle);
            } else {
                msg(this, "提示", "占无购买记录！");
            }
        }
    }


    public class QBookTask extends AsyncTask<String, Void, List<BuyedGoods>> {

        //执行耗时操作之前的准备
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(QBuyedGoodsAdminActivity.this, "正在加载...", "系统正在处理您的请求");
        }

        //执行耗时操作
        @Override
        protected List<BuyedGoods> doInBackground(String[] params) {
            String jsonString = HttpUtils.getJsonContent(params[0]);
            List<BuyedGoods> list = JsonTools.getBuyedGoods("borrowbooks", jsonString);

            return list;
        }

        //更新UI
        @Override
        protected void onPostExecute(List<BuyedGoods> buyedGoodsList) {
            super.onPostExecute(buyedGoodsList);
            listData = buyedGoodsList;
            adapter.setData(buyedGoodsList);
            admin_buyed_goods.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BuyedGoods buyedGoods = (BuyedGoods) admin_buyed_goods.getRefreshableView().
                getItemAtPosition(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("buyed_goods", buyedGoods);
        launch(BuyedGoodsDetailActivity.class, bundle);
    }


    private class ListBorrowedBookBaseAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<BuyedGoods> list = null;

        public ListBorrowedBookBaseAdapter(Context context) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
        }

        public void setData(List<BuyedGoods> list) {
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
                view = layoutInflater.inflate(R.layout.borrowed_book_admin_item, null);
            } else {
                view = convertView;
            }
            TextView g_name_value = (TextView) view.findViewById(R.id.g_name_value);
            TextView g_price_value = (TextView) view.findViewById(R.id.g_price_value);
            TextView g_rebate_value = (TextView) view.findViewById(R.id.g_rebate_value);
            final ImageView list_image = (ImageView) view.findViewById(R.id.list_image);
            g_name_value.setText(list.get(position).getGoods_name());
            g_price_value.setText(list.get(position).getBuy_price());
            g_rebate_value.setText(list.get(position).getGoods_rebate());

            //开启另外一个线程去加载图书图片
            String img_url = CommonUrl.LOAD_IMG + list.get(position).getGoods_img();
            ImgUtils.loadImage(new ImgUtils.ImageCallBack() {
                @Override
                public void getBitmap(Bitmap bitmap) {
                    list_image.setImageBitmap(bitmap);
                }
            }, img_url);

            return view;
        }
    }
}
