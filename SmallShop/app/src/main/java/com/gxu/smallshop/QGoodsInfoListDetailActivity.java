package com.gxu.smallshop;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxu.smallshop.db.domain.Admin;
import com.gxu.smallshop.db.domain.Agent;
import com.gxu.smallshop.db.domain.Goods;
import com.gxu.smallshop.db.domain.OrderedBook;
import com.gxu.smallshop.db.domain.Student;
import com.gxu.smallshop.db.domain.User;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.ImgUtils;
import com.gxu.smallshop.utils.JsonService;
import com.gxu.smallshop.utils.JsonTools;
import com.gxu.smallshop.utils.ShareUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class QGoodsInfoListDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView g_id_value, g_type_value, count_value, g_price_value, g_name_value,
            introduction_value;
    private Button share_goods, buy_goods;
    private Goods detail_goods;
    private ImageView g_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qgoods_info_list_detail);
        init();
    }

    private void init() {
        //获得列表信息界面传过来的goods
        detail_goods = (Goods) getIntent().getSerializableExtra("detail_goods");

        g_id_value = (TextView) this.findViewById(R.id.g_id_value);
        g_type_value = (TextView) this.findViewById(R.id.g_type_value);
        count_value = (TextView) this.findViewById(R.id.count_value);
        g_price_value = (TextView) this.findViewById(R.id.g_price_value);
        g_name_value = (TextView) this.findViewById(R.id.g_name_value);
        introduction_value = (TextView) this.findViewById(R.id.introduction_value);
        g_img = (ImageView) this.findViewById(R.id.g_img);
        getImage(g_img);

        g_id_value.setText(detail_goods.getG_num());
        g_type_value.setText(detail_goods.getG_type());
        count_value.setText(detail_goods.getG_count());
        g_price_value.setText(detail_goods.getG_price());
        g_name_value.setText(detail_goods.getG_name());
        introduction_value.setText(detail_goods.getG_introduce());

        share_goods = (Button) this.findViewById(R.id.share_goods);
        buy_goods = (Button) this.findViewById(R.id.buy_goods);
        buy_goods.setOnClickListener(this);
        share_goods.setOnClickListener(this);
    }

    private void getImage(final ImageView imageView) {
        ImgUtils.loadImage(new ImgUtils.ImageCallBack() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);

            }
        }, CommonUrl.LOAD_IMG + detail_goods.getG_img());

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.buy_goods:
                buyGoods();
                break;
            case R.id.share_goods:
                ShareUtils.showShare(QGoodsInfoListDetailActivity.this, CommonUrl.LOAD_IMG+detail_goods.getG_img(), detail_goods.getG_name(), null);
                break;
        }
    }

    //购买商品：首先查询goods表中商品的数量
    private void buyGoods() {
        Map<String, String> map = new HashMap<>();
        map.put("g_num", g_id_value.getText().toString());
        //从服务端获取json的数据并封装为javabean
        String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.GOODS_URL,
                JsonService.createJsonString(map));
        Goods goods = JsonTools.getGoods("goods", jsonString);
        //当图书馆中所借图书的数量大于0时才能借阅：将一条借书数据插入图书借阅信息表
        if ((!goods.getG_count().equals("")) && Integer.parseInt(goods.getG_count()) > 0) {
            //首先判断当前用户是普通买家还是代理，以便发送数据的服务端
            Map<String, Object> userMap = LoginActivity.getNowUserMap();
            for (String key : userMap.keySet()) {
                if (key.equals("买家")) {
                    User now_user = (User) userMap.get(key);
                    buyGoodsSendDataToServer(goods, now_user);
                } else if (key.equals("代理"))  {
                    Agent now_user = (Agent) userMap.get(key);
                    buyGoodsSendDataToServer(goods, now_user);
                }
                else if (key.equals("管理员"))  {
                    buy_goods.setEnabled(false);
                }
            }
        } else {
            msg(this, "失败信息", "对不起，没有这件商品或者该商品已没有库存！");
        }
    }

    private void buyGoodsSendDataToServer(Goods goods, User user) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date buy_time = new Date();
        Map<String, String> map2 = new HashMap<>();
        map2.put("buyer_num", user.getU_num());
        map2.put("goods_num", goods.getG_num());
        map2.put("goods_name", goods.getG_name());
        map2.put("buy_time", dateFormat.format(buy_time));
        map2.put("goods_img", goods.getG_img());
        map2.put("goods_rebate", user.getU_rebate());
        map2.put("goods_price",goods.getG_price());

        map2.put("g_count", goods.getG_count());
        Boolean flag = HttpUtils.sendJavaBeanToServer(CommonUrl.ADD_BUY_GOODS_URL,
                JsonService.createJsonString(map2));
        if (flag == true) {
            msg(this, "成功信息", "购买商品成功！");
            launch(QGoodsInfoActivity.class);
        } else {
            msg(this, "失败信息", "购买商品失败！");
        }
    }


    private void buyGoodsSendDataToServer(Goods goods, Agent agent) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date buy_time = new Date();
        Map<String, String> map2 = new HashMap<>();
        map2.put("buyer_num", agent.getA_num());
        map2.put("goods_num", goods.getG_num());
        map2.put("goods_name", goods.getG_name());
        map2.put("buy_time", dateFormat.format(buy_time));
        map2.put("goods_img", goods.getG_img());
        map2.put("goods_rebate", agent.getA_rebate());
        map2.put("goods_price",goods.getG_price());

        map2.put("g_count", goods.getG_count());
        Boolean flag = HttpUtils.sendJavaBeanToServer(CommonUrl.ADD_BUY_GOODS_URL,
                JsonService.createJsonString(map2));
        if (flag == true) {
            msg(this, "成功信息", "购买商品成功！");
            launch(QGoodsInfoActivity.class);
        } else {
            msg(this, "失败信息", "购买商品失败！");
        }
    }


}
