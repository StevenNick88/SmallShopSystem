package com.gxu.smallshop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gxu.smallshop.db.domain.User;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.HttpUtils;


public class BuyerInfoActivity extends BaseActivity implements View.OnClickListener{

    private TextView u_num_value,u_name_value,u_pwd_value,u_email_value,u_phone_value,u_rebate_value;
    private Button update_user,delete_user;
    private User user_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_info);
        init();

    }

    private void init() {
        //获得买家查询界面传过来的user
        user_query = (User)getIntent().getSerializableExtra("user_query");

        u_num_value=(TextView)this.findViewById(R.id.u_num_value);
        u_name_value=(TextView)this.findViewById(R.id.u_name_value);
        u_pwd_value=(TextView)this.findViewById(R.id.u_pwd_value);
        u_email_value=(TextView)this.findViewById(R.id.u_email_value);
        u_phone_value=(TextView)this.findViewById(R.id.u_phone_value);
        u_rebate_value=(TextView)this.findViewById(R.id.u_rebate_value);

        u_num_value.setText(user_query.getU_num());
        u_name_value.setText(user_query.getU_name());
        u_pwd_value.setText(user_query.getU_pwd());
        u_email_value.setText(user_query.getU_email());
        u_phone_value.setText(user_query.getU_phone());
        u_rebate_value.setText(user_query.getU_rebate());

        update_user=(Button)this.findViewById(R.id.update_user);
        delete_user=(Button)this.findViewById(R.id.delete_user);
        update_user.setOnClickListener(this);
        delete_user.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.update_user:
                Bundle bundle = new Bundle();
                bundle.putSerializable("update_user", user_query);
                launch(UpdateBuyerActivity.class,bundle);
                break;
            case R.id.delete_user:
                deleteUser();
                break;
        }
    }

    private void deleteUser() {
        //将买家的编码传递到服务端
        Boolean flag = HttpUtils.sendJavaBeanToServer(CommonUrl.DELETE_USER_URL,
                u_num_value.getText().toString());
        if (flag == true) {
            msg(this, "成功信息", "删除买家成功！");
            launch(BuyerMangeActivity.class);
        } else {
            msg(this, "失败信息", "删除买家失败！");
        }
    }
}
