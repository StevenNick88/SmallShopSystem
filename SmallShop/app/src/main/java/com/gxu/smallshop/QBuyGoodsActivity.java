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

import com.gxu.smallshop.db.domain.Agent;
import com.gxu.smallshop.db.domain.BorrowedBook;
import com.gxu.smallshop.db.domain.BuyedGoods;
import com.gxu.smallshop.db.domain.User;
import com.gxu.smallshop.pulltorefresh.task.CustomSoundPullEventListener;
import com.gxu.smallshop.pulltorefresh.task.CustomLastItemVisibleListener;
import com.gxu.smallshop.pulltorefresh.task.CustomRefreshListener;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.ImgUtils;
import com.gxu.smallshop.utils.JsonService;
import com.gxu.smallshop.utils.JsonTools;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QBuyGoodsActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private PullToRefreshListView buyed_goods;
    private ListBuyedGoodsBaseAdapter adapter;
    private List<BuyedGoods> listData;
//    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qgoods_borrowed);
        adapter = new ListBuyedGoodsBaseAdapter(QBuyGoodsActivity.this);
        buyed_goods = (PullToRefreshListView) this.findViewById(R.id.buyed_goods);

        // Set a listener to be invoked when the list should be refreshed.
        buyed_goods.setOnRefreshListener(new CustomRefreshListener(
                QBuyGoodsActivity.this, listData, adapter, buyed_goods));
        // Add an end-of-list listener
        buyed_goods.setOnLastItemVisibleListener(new CustomLastItemVisibleListener(
                QBuyGoodsActivity.this));
        // Add Sound Event Listener
        buyed_goods.setOnPullEventListener(new CustomSoundPullEventListener(
                QBuyGoodsActivity.this));

        //执行异步任务获取网络数据
        new QBookTask().execute(CommonUrl.BUY_GOODS_WITH_USER_NUM_URL);
        buyed_goods.setOnItemClickListener(this);
    }


    public class QBookTask extends AsyncTask<String, Void, List<BuyedGoods>> {

        //执行耗时操作之前的准备
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog = ProgressDialog.show(QBookBorrowedActivity.this, "正在加载...", "系统正在处理您的请求");
        }

        //执行耗时操作
        @Override
        protected List<BuyedGoods> doInBackground(String[] params) {
            List<BuyedGoods>  list=null;
            //首先判断当前用户是学生还是管理员，以便发送数据的服务端
            Map<String, Object> userMap = LoginActivity.getNowUserMap();
            for (String key : userMap.keySet()) {
                if (key.equals("买家")) {
                    User now_user = (User) userMap.get(key);
                    //从服务端获取json的数据并封装为javabean
                    Map<String, String> map = new HashMap<>();
                    map.put("user_num", now_user.getU_num());
                    //从服务端获取json的数据并封装为javabean
                    String jsonString = HttpUtils.sendInfoToServerGetJsonData(params[0],
                            JsonService.createJsonString(map));
                    list = JsonTools.getBuyedGoods("buygoods_with_buyer_num", jsonString);
                }
                else if (key.equals("代理")) {
                    Agent now_user = (Agent) userMap.get(key);
                    //从服务端获取json的数据并封装为javabean
                    Map<String, String> map = new HashMap<>();
                    map.put("user_num", now_user.getA_num());
                    //从服务端获取json的数据并封装为javabean
                    String jsonString = HttpUtils.sendInfoToServerGetJsonData(params[0],
                            JsonService.createJsonString(map));
                    list = JsonTools.getBuyedGoods("buygoods_with_buyer_num", jsonString);
                }
            }
            return list;
        }

        //更新UI
        @Override
        protected void onPostExecute(List<BuyedGoods> buyedGoodsList) {
            super.onPostExecute(buyedGoodsList);
            listData = buyedGoodsList;
            adapter.setData(buyedGoodsList);
            buyed_goods.setAdapter(adapter);
            adapter.notifyDataSetChanged();
//            dialog.dismiss();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BuyedGoods buyedGoods = (BuyedGoods) buyed_goods.getRefreshableView().
                getItemAtPosition(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("buyed_goods", buyedGoods);
        launch(BuyedGoodsDetailActivity.class, bundle);
    }


    public class ListBuyedGoodsBaseAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<BuyedGoods> list = null;

        public ListBuyedGoodsBaseAdapter(Context context) {
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
                view = layoutInflater.inflate(R.layout.buyed_goods_item, null);
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
//            if (!(state_value.getText().toString().equals("借阅中") ||
//                    list.get(position).getState().equals("续借中"))) {
//                view.setBackgroundColor(Color.GRAY);
//            }

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
