package com.gxu.smallshop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxu.smallshop.db.domain.BorrowedBook;
import com.gxu.smallshop.db.domain.BuyedGoods;
import com.gxu.smallshop.pulltorefresh.task.CustomLastItemVisibleListener;
import com.gxu.smallshop.pulltorefresh.task.CustomRefreshListener;
import com.gxu.smallshop.pulltorefresh.task.CustomSoundPullEventListener;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.ImgUtils;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;


public class QGoodsAdminBuyedListActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    private List<BuyedGoods> buyedGoodsList;
    private PullToRefreshListView admin_buyed_list_goods;
    private ListBuyedGoodsBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbuyed_goods_admin_list);
        adapter=new ListBuyedGoodsBaseAdapter(QGoodsAdminBuyedListActivity.this);
        buyedGoodsList =(List<BuyedGoods>)getIntent().getSerializableExtra("buygoods_with_buyer_num");

        admin_buyed_list_goods =(PullToRefreshListView)this.findViewById(R.id.admin_buyed_list_goods);
        // Set a listener to be invoked when the list should be refreshed.
        admin_buyed_list_goods.setOnRefreshListener(new CustomRefreshListener(
                QGoodsAdminBuyedListActivity.this, buyedGoodsList, adapter, admin_buyed_list_goods));
        // Add an end-of-list listener
        admin_buyed_list_goods.setOnLastItemVisibleListener(new CustomLastItemVisibleListener(
                QGoodsAdminBuyedListActivity.this));
        // Add Sound Event Listener
        admin_buyed_list_goods.setOnPullEventListener(new CustomSoundPullEventListener(
                QGoodsAdminBuyedListActivity.this));

        adapter.setData(buyedGoodsList);
        admin_buyed_list_goods.setAdapter(adapter);
        admin_buyed_list_goods.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BuyedGoods buyedGoods=(BuyedGoods) admin_buyed_list_goods.getRefreshableView().
                getItemAtPosition(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("buyed_goods", buyedGoods);
        launch(BuyedGoodsDetailActivity.class, bundle);

    }

    private class ListBuyedGoodsBaseAdapter extends BaseAdapter {
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
                view = layoutInflater.inflate(R.layout.borrowed_book_admin_list_item, null);
            } else {
                view = convertView;
            }
            TextView g_name_value = (TextView) view.findViewById(R.id.g_name_value);
            TextView g_price_value = (TextView) view.findViewById(R.id.g_price_value);
            TextView g_rebate_value = (TextView) view.findViewById(R.id.g_rebate_value);
            final ImageView list_image=(ImageView)view.findViewById(R.id.list_image);
            g_name_value.setText(list.get(position).getGoods_name());
            g_price_value.setText(list.get(position).getBuy_price());
            g_rebate_value.setText(list.get(position).getGoods_rebate());

            //开启另外一个线程去加载图片
            String img_url=CommonUrl.LOAD_IMG+list.get(position).getGoods_img();
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
