package com.gxu.smallshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxu.smallshop.db.domain.Goods;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.ImgUtils;
import com.gxu.smallshop.utils.ShareUtils;


public class GoodsInfoActivity extends BaseActivity implements View.OnClickListener{

    private TextView g_id_value,g_type_value,count_value,g_price_value,
            g_name_value,introduction_value;
    private Button share_goods,delete_goods,update_goods;
    private Goods b_query;
    private ImageView g_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        init();

    }

    private void init() {
        //获得图书查询界面传过来的goods
        b_query = (Goods)getIntent().getSerializableExtra("b_query");

        g_id_value=(TextView)this.findViewById(R.id.g_id_value);
        g_type_value=(TextView)this.findViewById(R.id.g_type_value);
        count_value=(TextView)this.findViewById(R.id.count_value);
        g_price_value=(TextView)this.findViewById(R.id.g_price_value);
        g_name_value=(TextView)this.findViewById(R.id.g_name_value);
        introduction_value=(TextView)this.findViewById(R.id.introduction_value);

        g_img=(ImageView)this.findViewById(R.id.g_img);
        getImage(g_img);

        g_id_value.setText(b_query.getG_num());
        g_type_value.setText(b_query.getG_type());
        count_value.setText(b_query.getG_count());
        g_price_value.setText(b_query.getG_price());
        g_name_value.setText(b_query.getG_name());
        introduction_value.setText(b_query.getG_introduce());

        share_goods=(Button)this.findViewById(R.id.share_goods);
        update_goods=(Button)this.findViewById(R.id.update_goods);
        delete_goods=(Button)this.findViewById(R.id.delete_goods);
        share_goods.setOnClickListener(this);
        update_goods.setOnClickListener(this);
        delete_goods.setOnClickListener(this);

    }

    private void getImage(final ImageView imageView) {
        ImgUtils.loadImage(new ImgUtils.ImageCallBack() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);

            }
        },CommonUrl.LOAD_IMG+b_query.getG_img());

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.share_goods:
                ShareUtils.showShare(GoodsInfoActivity.this,null,b_query.getG_name(),null);
                break;
            case R.id.update_goods:
                launch(UpdateGoodsActivity.class,new Intent(),"update_goods",b_query);
                break;
            case R.id.delete_goods:
                deleteGoods();
                break;
        }
    }

    private void deleteGoods() {
        //将图书的编码传递到服务端
        Boolean flag = HttpUtils.sendJavaBeanToServer(CommonUrl.DELETE_GOODS_URL,
                g_id_value.getText().toString());
        if (flag == true) {
            msgWithPositiveButton(this, "成功信息", "确定", "删除商品成功！");
        } else {
            msg(this, "失败信息", "删除商品失败！");
        }
    }

    @Override
    public void onPositiveEvent() {
        launch(GoodsManageActivity.class);
        finish();
    }
}
