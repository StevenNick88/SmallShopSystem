package com.gxu.smallshop;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxu.smallshop.db.domain.BuyedGoods;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.ImgUtils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class BuyedGoodsDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView g_num_value, goods_rebate_value, buy_price_value, buyer_num_value,
            g_name_value, buy_time_value;
    private Button return_book;
    private ImageView g_img;
    private BuyedGoods buyed_goods;
    private SimpleDateFormat dateFormat;
    private Date date;
    private int overtime;
    private int remain_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyed_goods_detail);
        init();
    }

    private void init() {
        //获得列表信息界面传过来的book
        buyed_goods = (BuyedGoods) getIntent().getSerializableExtra("buyed_goods");

        g_num_value = (TextView) this.findViewById(R.id.g_num_value);
        goods_rebate_value = (TextView) this.findViewById(R.id.goods_rebate_value);
        buy_price_value = (TextView) this.findViewById(R.id.buy_price_value);
        buyer_num_value = (TextView) this.findViewById(R.id.buyer_num_value);
        g_name_value = (TextView) this.findViewById(R.id.g_name_value);
        buy_time_value = (TextView) this.findViewById(R.id.buy_time_value);
        g_img = (ImageView) this.findViewById(R.id.g_img);
        getImage(g_img);

        g_num_value.setText(buyed_goods.getGoods_num());
        goods_rebate_value.setText(buyed_goods.getGoods_rebate());
        buy_price_value.setText(buyed_goods.getBuy_price());
        buyer_num_value.setText(buyed_goods.getBuyer_num());
        g_name_value.setText(buyed_goods.getGoods_name());
        buy_time_value.setText(buyed_goods.getBuy_time());

//        return_book = (Button) this.findViewById(R.id.return_book);
//        return_book.setOnClickListener(this);

//        getBorrowedInfo();

    }

    private void getImage(final ImageView imageView) {
        ImgUtils.loadImage(new ImgUtils.ImageCallBack() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);

            }
        }, CommonUrl.LOAD_IMG + buyed_goods.getGoods_img());

    }

//    //从服务端获取超期时间和剩余时间
//    private void getBorrowedInfo() {
//        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        date = new Date();
//        Map<String, String> map = new HashMap<>();
//        map.put("g_num", b_num_value.getText().toString());
//        map.put("return_time", dateFormat.format(date));
//        //从服务端获取json的数据并封装为javabean
//        String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.RETURN_BORROW_BOOK_PRE_URL,
//                JsonService.createJsonString(map));
//        Map<String, Object> borrowedInfo = JsonTools.getMaps("borrowedInfo", jsonString);
//        overtime_value.setText((String) borrowedInfo.get("overtime"));
//        remain_time_value.setText((String) borrowedInfo.get("remain_time"));
//        should_return_value.setText((String) borrowedInfo.get("shouldReturnTime"));
//
//        overtime = Integer.parseInt((String) borrowedInfo.get("overtime"));
//        remain_time = Integer.parseInt((String) borrowedInfo.get("remain_time"));
//
//    }

    @Override
    public void onClick(View v) {

//        switch (v.getId()) {
//
//            //还书
//            case R.id.return_book:
//                returnBook();
//                break;
//            //挂失
//            case R.id.loss_book:
//                lossBook();
//                break;
//            //续借
//            case R.id.borrow_book_again:
//                borrowBookAgain();
//                break;
//        }
    }


//    //续借：首先判断是否超期，未超期才能续借
//    private void borrowBookAgain() {
//        if (overtime > 0) {
//            msg(this, "提示信息", "对不起，请所借的图书已经超期，请先还书并交纳欠款再借阅！");
//            //未超期，可续借：修改图书借阅信息表中的borrow_time以及其他字段的值
//        } else {
//            Date d1 = null;
//            Date d2 = null;
//            try {
//                d1 = new Date();                     //今天的日期
//                long fifteenTimes = 15 * 24 * 60 * 60 * 1000;     //15天的毫秒数
//                long diff = d1.getTime() + fifteenTimes;    //这样得到的差值是微妙级别的
//                d2 = new Date(diff);                      //今天+15天之后的日期
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Map<String, String> map2 = new HashMap<>();
//            map2.put("b_num", b_num_value.getText().toString());
//            map2.put("b_name", b_name_value.getText().toString());
//            map2.put("user_num", user_num_value.getText().toString());
//            map2.put("borrow_time", dateFormat.format(d1));
//            map2.put("shouldReturn_time", dateFormat.format(d2));
//            map2.put("return_time", "");
//            map2.put("state", "续借中");
//            Boolean flag = HttpUtils.sendJavaBeanToServer(CommonUrl.AGAIN_BORROW_BOOK_URL,
//                    JsonService.createJsonString(map2));
//            if (flag == true) {
//                msg(this, "成功信息", "续借成功！");
//            } else {
//                msg(this, "失败信息", "续借失败！");
//            }
//        }
//    }


}
