package com.gxu.smallshop;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gxu.smallshop.db.domain.Admin;
import com.gxu.smallshop.db.domain.Agent;
import com.gxu.smallshop.db.domain.Supplier;
import com.gxu.smallshop.db.domain.User;
import com.gxu.smallshop.utils.Base64Utils;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.DESUtils;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.JsonService;

import java.util.HashMap;
import java.util.Map;


public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText user_value, password_value, confirm_password_value;
    private Button change_password, change_password_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
    }

    private void init() {
        user_value = (EditText) this.findViewById(R.id.user_value);
        password_value = (EditText) this.findViewById(R.id.password_value);
        confirm_password_value = (EditText) this.findViewById(R.id.confirm_password_value);

        Map<String, Object> userMap = LoginActivity.getNowUserMap();
        for (String key : userMap.keySet()) {
            if (key.equals("学生")) {
                User now_user = (User) userMap.get(key);
                user_value.setText(now_user.getU_num());
            } else if (key.equals("代理")) {
                Agent now_user = (Agent) userMap.get(key);
                user_value.setText(now_user.getA_num());
            } else if (key.equals("供应商")) {
                Supplier now_user = (Supplier) userMap.get(key);
                user_value.setText(now_user.getS_num());
            } else if (key.equals("管理员")) {
                Admin now_user = (Admin) userMap.get(key);
                user_value.setText(now_user.getM_num());
            }
        }
        //用户名不可编辑
        user_value.setEnabled(false);

        change_password = (Button) this.findViewById(R.id.change_password);
        change_password_cancel = (Button) this.findViewById(R.id.change_password_cancel);
        change_password.setOnClickListener(this);
        change_password_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_password:
                changePassword();
                break;
            case R.id.change_password_cancel:
                launch(SystemMangeActivity.class);
                break;
        }
    }

    private void changePassword() {
        // 判断输入框是否为空
        String password = password_value.getText().toString();
        String passwordConfirm = confirm_password_value.getText().toString();
        if (password.equals("")) {
            msg(this, "提示", "密码不能为空！");
        } else if (passwordConfirm.equals("")) {
            msg(this, "提示", "确认密码不能为空！");
        } else if (!(password.equals(passwordConfirm))) {
            msg(this, "提示", "密码和确认密码不一致，请重新输入！");
            confirm_password_value.setText("");
        } else {
            msgWithEventDesEncryption(ChangePasswordActivity.this, "提示", "确定", "取消");
        }
    }

    @Override
    public void onPositiveEventDesEncryption(String desValuesStr) {
        super.onPositiveEventDesEncryption(desValuesStr);
        Map<String, Object> userMap = LoginActivity.getNowUserMap();
        for (String key : userMap.keySet()) {
            if (key.equals("买家")) {
                sendInfoToServer(CommonUrl.UPDATE_USER_PWD,desValuesStr);
            } else if (key.equals("代理")) {
                sendInfoToServer(CommonUrl.UPDATE_AGENT_PWD,desValuesStr);
            } else if (key.equals("供应商")) {
                sendInfoToServer(CommonUrl.UPDATE_SUPPLIER_PWD,desValuesStr);
            } else if (key.equals("管理员")) {
                sendInfoToServer(CommonUrl.UPDATE_ADMIN_PWD,desValuesStr);
            }
        }
    }

    private void sendInfoToServer(String url,String desValuesStr){
        Map<String, String> map = new HashMap<>();
        map.put("user_num", user_value.getText().toString());
        map.put("user_password",  Base64Utils.encode(DESUtils.desCrypto(password_value.getText().toString().getBytes(), desValuesStr)));
        Boolean flag1 = HttpUtils.sendJavaBeanToServer(url,
                JsonService.createJsonString(map));
        if (flag1 == true) {
            msg(this, "成功信息", "修改成功！");
            launch(SystemMangeActivity.class);
        } else {
            msg(this, "失败信息", "修改失败！");
        }
    }
}
