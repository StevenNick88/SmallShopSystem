package com.gxu.smallshop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gxu.smallshop.db.domain.Message;
import com.gxu.smallshop.db.domain.User;
import com.gxu.smallshop.utils.AESUtils;
import com.gxu.smallshop.utils.Base64Utils;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.DESUtils;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.JsonService;
import com.gxu.smallshop.utils.JsonTools;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends ActionBarActivity implements View.OnClickListener {
    //默认买家折扣率
    private static final String USER_REBATE = "1";
    //默认买家秘钥
    private static final String USER_SECRET_KEY = "12345678";
    private EditText  u_name_value, u_pwd_value, u_email_value, u_phone_value,  u_pwd_confirm_value;
    private Button register_user;
    private String buyerBum;

    public String getBuyerBum() {
        return buyerBum;
    }

    public void setBuyerBum(String buyerBum) {
        this.buyerBum = buyerBum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        register_user = (Button) this.findViewById(R.id.register_user);
        register_user.setOnClickListener(this);

        u_name_value = (EditText) this.findViewById(R.id.u_name_value);
        u_pwd_value = (EditText) this.findViewById(R.id.u_pwd_value);
        u_email_value = (EditText) this.findViewById(R.id.u_email_value);
        u_phone_value = (EditText) this.findViewById(R.id.u_phone_value);
        u_pwd_confirm_value = (EditText) this.findViewById(R.id.u_pwd_confirm_value);
    }


    @Override
    public void onClick(View v) {
        // 判断输入框是否为空
        String u_pwd = u_pwd_value.getText().toString();
        String u_pwd_confirm = u_pwd_confirm_value.getText().toString();
        if (u_name_value.getText().toString().equals("")) {
            msg(this, "提示", "买家姓名不能为空！");
        } else if (u_pwd_value.getText().toString().equals("")) {
            msg(this, "提示", "买家密码不能为空！");
        } else if (u_pwd_confirm_value.getText().toString().equals("")) {
            msg(this, "提示", "确认密码不能为空！");
        } else if (!(u_pwd_confirm.equals(u_pwd))) {
            msg(this, "提示", "买家密码和确认密码不一致！请重新输入。");
            u_pwd_confirm_value.setText("");
        } else if (u_email_value.getText().toString().equals("")) {
            msg(this, "提示", "电子邮箱不能为空！");
        } else if (u_phone_value.getText().toString().equals("")) {
            msg(this, "提示", "手机号码不能为空！");
        } else {
            //首先查询现在数据库中user编号的最大值
            String jsonString = HttpUtils.getJsonContent(CommonUrl.BUYER_LAST_URL);
            User user = JsonTools.getUser("buyer_last", jsonString, USER_SECRET_KEY);
            Map<String, String> map = new HashMap<>();
            if ((user != null) && (user.getU_num() != null)) {
                //将编号加1
                int ms_num = Integer.parseInt(user.getU_num());
                int rel_ms_num=++ms_num;
                map.put("u_num", String.valueOf(rel_ms_num));
                setBuyerBum(String.valueOf(rel_ms_num));
            } else {
                //从编号100开始添加买家
                map.put("u_num", String.valueOf(100));
                setBuyerBum(String.valueOf(100));
            }
            Boolean flag = false;
            try {
//                 Base64Utils.encode(AESUtils.encrypt(u_name_value.getText().toString(), desValuesStr)));(u_name_value.getText().toString(), desValuesStr)));
                map.put("u_name", Base64Utils.encode(AESUtils.encrypt(u_name_value.getText().toString(), USER_SECRET_KEY)));
                map.put("u_pwd", Base64Utils.encode(AESUtils.encrypt(u_pwd_value.getText().toString(), USER_SECRET_KEY)));
                map.put("u_email", Base64Utils.encode(AESUtils.encrypt(u_email_value.getText().toString(), USER_SECRET_KEY)));
                map.put("u_phone", Base64Utils.encode(AESUtils.encrypt(u_phone_value.getText().toString(), USER_SECRET_KEY)));
                map.put("u_rebate", Base64Utils.encode(AESUtils.encrypt(USER_REBATE, USER_SECRET_KEY)));
                flag = HttpUtils.sendJavaBeanToServer(CommonUrl.ADD_USER_URL,
                        JsonService.createJsonString(map));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (flag == true) {
                msgWithPositiveButton(RegisterActivity.this, "提示", "确定", "注册买家成功！您的用户名是：" + getBuyerBum() + ",\t密码是：" + u_pwd_value.getText().toString() + "。请牢记！");
            } else {
                msg(this, "失败信息", "注册买家失败！");
            }
        }
    }

//    onpos

//    @Override
    public void onPositiveEvent() {
        launch(LoginActivity.class);
    }

    // 消息对话框
    public void msg(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(R.drawable.ic_dialog_alert_holo_light);
        builder.setPositiveButton("确定", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // 消息对话框
    public void msgWithPositiveButton(Context context, String title, String positiveButton, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(R.drawable.ic_dialog_alert_holo_light);
        builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onPositiveEvent();
            }
        });
        builder.create();
        builder.show();
    }

    public void launch(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
        overridePendingTransition(R.anim.push_right_in, android.R.anim.fade_out);
    }


}
