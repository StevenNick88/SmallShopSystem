package com.gxu.smallshop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Map;


public class QGoodsActivity extends BaseActivity implements View.OnClickListener{

    private Button main_book_query,borrowed_book_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qgoods);
        main_book_query=(Button)this.findViewById(R.id.main_book_query);
        borrowed_book_query=(Button)this.findViewById(R.id.borrowed_book_query);
        main_book_query.setOnClickListener(this);
        borrowed_book_query.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.main_book_query:
                launch(QGoodsInfoActivity.class);
                break;
            case R.id.borrowed_book_query:
                //首先判断当前用户是买家，代理还是管理员，分别跳转到不同的界面
                Map<String, Object> userMap = LoginActivity.getNowUserMap();
                for (String key : userMap.keySet()) {
                    if (key.equals("买家")||(key.equals("代理"))){
                        launch(QBuyGoodsActivity.class);
                    } else if (key.equals("管理员")){
                        launch(QBuyedGoodsAdminActivity.class);
                    }
                }
                break;
        }

    }
}
