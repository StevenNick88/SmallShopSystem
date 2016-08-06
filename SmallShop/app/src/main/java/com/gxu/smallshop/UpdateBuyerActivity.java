package com.gxu.smallshop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gxu.smallshop.db.domain.User;
import com.gxu.smallshop.utils.Base64Utils;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.DESUtils;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.JsonService;

import java.util.HashMap;
import java.util.Map;


public class UpdateBuyerActivity extends BaseActivity implements View.OnClickListener{

    private EditText u_num_value, u_name_value,u_pwd_value,u_email_value,u_phone_value,u_rebate_value;
    private Button update_user;
    private User user_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_buyer);
        init();


    }

    private void init() {
        //获得管理员详细信息界面传过来的admin
        user_update = (User)getIntent().getSerializableExtra("update_user");

        u_num_value=(EditText)this.findViewById(R.id.u_num_value);
        u_name_value=(EditText)this.findViewById(R.id.u_name_value);
        u_pwd_value=(EditText)this.findViewById(R.id.u_pwd_value);
        u_email_value=(EditText)this.findViewById(R.id.u_email_value);
        u_phone_value=(EditText)this.findViewById(R.id.u_phone_value);
        u_rebate_value = (EditText) this.findViewById(R.id.u_rebate_value);

        u_num_value.setText(user_update.getU_num());
        //管理员ID不可修改，修改之后无法插入数据库
        u_num_value.setEnabled(false);
        u_name_value.setText(user_update.getU_name());
        u_pwd_value.setText(user_update.getU_pwd());
        u_email_value.setText(user_update.getU_email());
        u_phone_value.setText(user_update.getU_phone());
        u_rebate_value.setText(user_update.getU_rebate());

        update_user =(Button)this.findViewById(R.id.update_user);
        update_user.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        updateUser();
    }

    private void updateUser() {
        // 判断输入框是否为空
        if (u_num_value.getText().toString().equals("")) {
            msg(this, "提示", "买家编号不能为空！");
        } else if (u_name_value.getText().toString().equals("")) {
            msg(this, "提示", "买家姓名不能为空！");
        } else if (u_pwd_value.getText().toString().equals("")) {
            msg(this, "提示", "买家密码不能为空！");
        } else if (u_email_value.getText().toString().equals("")) {
            msg(this, "提示", "电子邮箱不能为空！");
        }
        else if (u_phone_value.getText().toString().equals("")) {
            msg(this, "提示", "手机号码不能为空！");
        } else if (u_rebate_value.getText().toString().equals("")) {
            msg(this, "提示", "买家折扣率不能为空！");
        }
        else {
            msgWithEventDesEncryption(UpdateBuyerActivity.this, "提示", "确定", "取消");
        }
    }

    @Override
    public void onPositiveEventDesEncryption(String desValuesStr) {
        super.onPositiveEventDesEncryption(desValuesStr);
        Map<String, String> map = new HashMap<>();
        Boolean flag = null;
        try {
            map.put("u_num", u_num_value.getText().toString());
            map.put("u_name", Base64Utils.encode(DESUtils.desCrypto(u_name_value.getText().toString().getBytes(), desValuesStr)));
            map.put("u_pwd", Base64Utils.encode(DESUtils.desCrypto(u_pwd_value.getText().toString().getBytes(), desValuesStr)));
            map.put("u_email", Base64Utils.encode(DESUtils.desCrypto(u_email_value.getText().toString().getBytes(), desValuesStr)));
            map.put("u_phone", Base64Utils.encode(DESUtils.desCrypto(u_phone_value.getText().toString().getBytes(), desValuesStr)));
            map.put("u_rebate", Base64Utils.encode(DESUtils.desCrypto(u_rebate_value.getText().toString().getBytes(), desValuesStr)));
            flag = HttpUtils.sendJavaBeanToServer(CommonUrl.UPDATE_USER_URL,
                    JsonService.createJsonString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag == true) {
            msgWithPositiveButton(this, "提示", "确定", "修改买家成功!");
        } else {
            msg(this, "失败信息", "修改买家失败！");
        }
    }

    @Override
    public void onPositiveEvent() {
        super.onPositiveEvent();
        launch(BuyerMangeActivity.class);
    }
}
