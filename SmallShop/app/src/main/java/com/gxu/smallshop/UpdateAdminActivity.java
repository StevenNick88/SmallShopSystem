package com.gxu.smallshop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gxu.smallshop.db.domain.Admin;
import com.gxu.smallshop.utils.Base64Utils;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.DESUtils;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.JsonService;

import java.util.HashMap;
import java.util.Map;


public class UpdateAdminActivity extends BaseActivity implements View.OnClickListener {

    private EditText m_num_value, m_name_value, m_pwd_value, m_remark_value;
    private Button update_admin;
    private Admin up_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_admin);
        init();
    }

    private void init() {
        up_admin = (Admin) getIntent().getSerializableExtra("update_admin");

        m_num_value = (EditText) this.findViewById(R.id.m_num_value);
        m_name_value = (EditText) this.findViewById(R.id.m_name_value);
        m_pwd_value = (EditText) this.findViewById(R.id.m_pwd_value);
        m_remark_value = (EditText) this.findViewById(R.id.m_remark_value);

        m_num_value.setText(up_admin.getM_num());
        m_num_value.setEnabled(false);
        m_name_value.setText(up_admin.getM_name());
        m_pwd_value.setText(up_admin.getM_pwd());
        m_remark_value.setText(up_admin.getM_remark());

        update_admin = (Button) this.findViewById(R.id.update_admin);
        update_admin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        updateAdmin();
    }

    private void updateAdmin() {
        // 判断输入框是否为空
        if (m_num_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员编号不能为空！");
        } else if (m_name_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员姓名不能为空！");
        } else if (m_pwd_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员密码不能为空！");
        } else if (m_remark_value.getText().toString().equals("")) {
            msg(this, "提示", "管理员备注不能为空！");
        } else {
            msgWithEventDesEncryption(UpdateAdminActivity.this, "提示", "确定", "取消");
        }


    }

    @Override
    public void onPositiveEventDesEncryption(String desValuesStr) {
        super.onPositiveEventDesEncryption(desValuesStr);
        Map<String, String> map = new HashMap<>();
        Boolean flag = false;
        try {
            map.put("m_num", m_num_value.getText().toString());
            map.put("m_name", Base64Utils.encode(DESUtils.desCrypto(m_name_value.getText().toString().getBytes(), desValuesStr)));
            map.put("m_pwd", Base64Utils.encode(DESUtils.desCrypto(m_pwd_value.getText().toString().getBytes(), desValuesStr)));
            map.put("m_remark", Base64Utils.encode(DESUtils.desCrypto(m_remark_value.getText().toString().getBytes(), desValuesStr)));
            flag = HttpUtils.sendJavaBeanToServer(CommonUrl.UPDATE_ADMIN_URL,
                    JsonService.createJsonString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag == true) {
            msg(this, "成功信息", "修改管理员成功！");
            launch(AdminMangeActivity.class);
        } else {
            msg(this, "失败信息", "修改管理员失败！");
        }


    }
}
