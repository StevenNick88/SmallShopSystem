package com.gxu.smallshop;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminMainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private int resIds[] = new int[]{R.drawable.notice,
            R.drawable.commodity_management,
            R.drawable.buyer_mangement,
            R.drawable.agent_mangement,
            R.drawable.supplier_mangment,
            R.drawable.admin_mangement,
            R.drawable.system_mangement,
            R.drawable.goods_query,
            R.drawable.online_mall,
            R.drawable.relax,
            R.drawable.message_board,
            R.drawable.about_shop,
            R.drawable.help_b,
            R.drawable.unregiste,
            R.drawable.exit,
            R.drawable.more,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        getOverflowMenu();

        gridView = (GridView) this.findViewById(R.id.gridViews);
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < resIds.length; i++) {
            Map<String, Object> cell = new HashMap<>();
            cell.put("imageView", resIds[i]);
            list.add(cell);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.admin_mangement_item,
                new String[]{"imageView"}, new int[]{R.id.imageView});
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onPositiveEvent() {
        launch(LoginActivity.class);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                msg(AdminMainActivity.this, getResources().getString(R.string.notice),
                        getResources().getString(R.string.notice_values));
                break;
            case 1:
                launch(GoodsManageActivity.class);
                break;
            case 2:
                launch(BuyerMangeActivity.class);
                break;
            case 3:
                launch(AgentMangeActivity.class);
                break;
            case 4:
                launch(SupplierMangeActivity.class);
                break;
            case 5:
                launch(AdminMangeActivity.class);
                break;
            case 6:
                launch(SystemMangeActivity.class);
                break;
            case 7:
                launch(QGoodsActivity.class);
                break;
            case 8:
                launch(OnlineGoodsActivity.class);
                break;
            case 9:
                launch(RelaxationActivity.class);
                break;
            case 10:
                launch(MessageAdminActivity.class);
                break;
            case 11:
                msg(AdminMainActivity.this, "软件说明", getResources().getString(R.string.about_software_value));
                break;
            case 12:
                msg(AdminMainActivity.this, "帮助说明", getResources().getString(R.string.help_value));
                break;
            case 13:
                msgWithEvent(AdminMainActivity.this, "提示", "确定", "取消", "确定要注销吗？");
                break;
            case 14:
                onBackPressed();
                break;
            case 15:
                msg(AdminMainActivity.this, "提示", "更多功能尽请期待！");
                break;
            default:
                break;

        }
    }


    long waitTime = 2000;
    long touchTime = 0;

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - touchTime) >= waitTime) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            touchTime = currentTime;
        } else {
            finish();
        }
    }

}
