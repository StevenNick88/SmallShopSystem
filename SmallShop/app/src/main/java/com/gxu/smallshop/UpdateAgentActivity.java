package com.gxu.smallshop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gxu.smallshop.db.domain.Agent;
import com.gxu.smallshop.utils.Base64Utils;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.DESUtils;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.JsonService;

import java.util.HashMap;
import java.util.Map;


public class UpdateAgentActivity extends BaseActivity implements View.OnClickListener{

    private EditText a_num_value, a_name_value,a_pwd_value,a_introduce_value,  a_level_value,a_rebate_value;
    private Button update_agent;
    private Agent up_agent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_agent);
        init();
    }

    private void init() {
        up_agent = (Agent)getIntent().getSerializableExtra("update_agent");

        a_num_value=(EditText)this.findViewById(R.id.a_num_value);
        a_name_value=(EditText)this.findViewById(R.id.a_name_value);
        a_pwd_value=(EditText)this.findViewById(R.id.a_pwd_value);
        a_introduce_value=(EditText)this.findViewById(R.id.a_introduce_value);
        a_level_value = (EditText)this.findViewById(R.id.a_level_value);
        a_rebate_value = (EditText) this.findViewById(R.id.a_rebate_value);

        a_num_value.setText(up_agent.getA_num());

        a_num_value.setEnabled(false);
        a_name_value.setText(up_agent.getA_name());
        a_pwd_value.setText(up_agent.getA_pwd());
        a_introduce_value.setText(up_agent.getA_introduce());
        a_level_value.setText(up_agent.getA_level());
        a_rebate_value.setText(up_agent.getA_rebate());

        update_agent =(Button)this.findViewById(R.id.update_agent);
        update_agent.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        updateAgent();
    }

    private void updateAgent() {
        // 判断输入框是否为空
        if (a_num_value.getText().toString().equals("")) {
            msg(this, "提示", "代理编号不能为空！");
        } else if (a_name_value.getText().toString().equals("")) {
            msg(this, "提示", "代理姓名不能为空！");
        } else if (a_pwd_value.getText().toString().equals("")) {
            msg(this, "提示", "代理密码不能为空！");
        } else if (a_level_value.getText().toString().equals("")) {
            msg(this, "提示", "代理等级不能为空！");
        } else if (a_rebate_value.getText().toString().equals("")) {
            msg(this, "提示", "代理折扣率不能为空！");
        } else if (a_introduce_value.getText().toString().equals("")) {
            msg(this, "提示", "代理描述不能为空！");
        }
        else {
            msgWithEventDesEncryption(UpdateAgentActivity.this, "提示", "确定", "取消");

        }


    }

    @Override
    public void onPositiveEventDesEncryption(String desValuesStr) {
        super.onPositiveEventDesEncryption(desValuesStr);
        Map<String, String> map = new HashMap<>();
        Boolean flag = null;
        try {
            map.put("a_num", a_num_value.getText().toString());
            map.put("a_name", Base64Utils.encode(DESUtils.desCrypto(a_name_value.getText().toString().getBytes(), desValuesStr)));
            map.put("a_pwd", Base64Utils.encode(DESUtils.desCrypto(a_pwd_value.getText().toString().getBytes(), desValuesStr)));
            map.put("a_level", Base64Utils.encode(DESUtils.desCrypto(a_level_value.getText().toString().getBytes(), desValuesStr)));
            map.put("a_rebate", Base64Utils.encode(DESUtils.desCrypto(a_rebate_value.getText().toString().getBytes(), desValuesStr)));
            map.put("a_introduce", Base64Utils.encode(DESUtils.desCrypto(a_introduce_value.getText().toString().getBytes(), desValuesStr)));
            flag = HttpUtils.sendJavaBeanToServer(CommonUrl.UPDATE_AGENT_URL,
                    JsonService.createJsonString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag == true) {
            msg(this, "成功信息", "修改代理成功！");
            launch(AgentMangeActivity.class);
        } else {
            msg(this, "失败信息", "修改代理失败！");
        }

    }
}
