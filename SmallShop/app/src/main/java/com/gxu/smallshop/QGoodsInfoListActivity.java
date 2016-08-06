package com.gxu.smallshop;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxu.smallshop.db.domain.Book;
import com.gxu.smallshop.db.domain.Goods;
import com.gxu.smallshop.pulltorefresh.task.CustomLastItemVisibleListener;
import com.gxu.smallshop.pulltorefresh.task.CustomRefreshListener;
import com.gxu.smallshop.pulltorefresh.task.CustomSoundPullEventListener;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.ImgUtils;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;
import java.util.Set;


public class QGoodsInfoListActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private PullToRefreshListView list_goods;
    private List<Goods> intentData;
    private ListGoodsBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qgoods_info_list);
        intentData=getIntentData();
        Log.i("QGoodsInfoListActivity",intentData.toString());
        adapter=new ListGoodsBaseAdapter(QGoodsInfoListActivity.this);
        list_goods=(PullToRefreshListView)this.findViewById(R.id.list_goods);

        // Set a listener to be invoked when the list should be refreshed.
        list_goods.setOnRefreshListener(new CustomRefreshListener(
                QGoodsInfoListActivity.this, intentData, adapter, list_goods));
        // Add an end-of-list listener
        list_goods.setOnLastItemVisibleListener(new CustomLastItemVisibleListener(
                QGoodsInfoListActivity.this));
        // Add Sound Event Listener
        list_goods.setOnPullEventListener(new CustomSoundPullEventListener(
                QGoodsInfoListActivity.this));


        adapter.setData(intentData);
        list_goods.setAdapter(adapter);
        list_goods.setOnItemClickListener(this);
    }

    private List<Goods> getIntentData() {
        Set<String> keySet=getIntent().getExtras().keySet();
        for (String key:keySet){
            if (key.equals("list_goods_withG_num")){
                List<Goods> list=(List<Goods>)getIntent().getSerializableExtra(key);
                return list;
            }
            if (key.equals("list_goods_withG_name")){
                List<Goods> list=(List<Goods>)getIntent().getSerializableExtra(key);
                return list;
            }
            if (key.equals("list_goods_withG_type")){
                List<Goods> list=(List<Goods>)getIntent().getSerializableExtra(key);
                return list;
            }
            if (key.equals("q_goods_with_g_name_type")){
                List<Goods> list=(List<Goods>)getIntent().getSerializableExtra(key);
                return list;
            }

        }
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("detail_goods", (Goods)list_goods.getRefreshableView().getItemAtPosition(position));
        launch(QGoodsInfoListDetailActivity.class, bundle);
    }


    private class ListGoodsBaseAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Goods> list=null;

        public ListGoodsBaseAdapter(Context context){
            this.context=context;
            layoutInflater=LayoutInflater.from(context);
        }
        public void setData(List<Goods> list){
            this.list=list;
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
            View view=null;
            if (convertView == null) {
                view= layoutInflater.inflate(R.layout.q_goods_info_list_item, null);
            }else {
                view=convertView;
            }
            TextView list_g_name_value=(TextView)view.findViewById(R.id.list_g_name_value);
            TextView list_g_type_value=(TextView)view.findViewById(R.id.list_g_type_value);
            TextView list_g_price_value=(TextView)view.findViewById(R.id.list_g_price_value);
            TextView list_g_count_value=(TextView)view.findViewById(R.id.list_g_count_value);
            final ImageView list_image=(ImageView)view.findViewById(R.id.list_image);
            list_g_name_value.setText(list.get(position).getG_name());
            list_g_type_value.setText(list.get(position).getG_type());
            list_g_price_value.setText(list.get(position).getG_price());
            list_g_count_value.setText(list.get(position).getG_count());

            //开启另外一个线程去加载图书图片
            String img_url=CommonUrl.LOAD_IMG+list.get(position).getG_img();
            ImgUtils.loadImage(new ImgUtils.ImageCallBack() {
                @Override
                public void getBitmap(Bitmap bitmap) {
                    list_image.setImageBitmap(bitmap);
                }
            },img_url);

            return view;
        }
    }
}
