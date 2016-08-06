package com.gxu.smallshop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gxu.smallshop.R;
import com.gxu.smallshop.db.domain.Agent;
import com.gxu.smallshop.db.domain.User;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.HttpUtils;


public class AgentInfoActivity extends BaseActivity implements View.OnClickListener{

    private TextView a_num_value,a_name_value,a_pwd_value,a_introduce_value,a_level_value,a_rebate_value;
    private Button update_agent,delete_agent;
    private Agent agent_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_info);
        init();

    }

    private void init() {
        agent_query = (Agent)getIntent().getSerializableExtra("agent_query");

        a_num_value=(TextView)this.findViewById(R.id.a_num_value);
        a_name_value=(TextView)this.findViewById(R.id.a_name_value);
        a_pwd_value=(TextView)this.findViewById(R.id.a_pwd_value);
        a_introduce_value=(TextView)this.findViewById(R.id.a_introduce_value);
        a_level_value=(TextView)this.findViewById(R.id.a_level_value);
        a_rebate_value=(TextView)this.findViewById(R.id.a_rebate_value);

        a_num_value.setText(agent_query.getA_num());
        a_name_value.setText(agent_query.getA_name());
        a_pwd_value.setText(agent_query.getA_pwd());
        a_introduce_value.setText(agent_query.getA_introduce());
        a_level_value.setText(agent_query.getA_level());
        a_rebate_value.setText(agent_query.getA_rebate());

        update_agent=(Button)this.findViewById(R.id.update_agent);
        delete_agent=(Button)this.findViewById(R.id.delete_agent);
        update_agent.setOnClickListener(this);
        delete_agent.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.update_agent:
                Bundle bundle = new Bundle();
                bundle.putSerializable("update_agent", agent_query);
                launch(UpdateAgentActivity.class,bundle);
                break;
            case R.id.delete_agent:
                deleteAgent();
                break;
        }
    }

    private void deleteAgent() {
        Boolean flag = HttpUtils.sendJavaBeanToServer(CommonUrl.DELETE_AGENT_URL,
                a_num_value.getText().toString());
        if (flag == true) {
            msg(this, "成功信息", "删除代理成功！");
            launch(AgentMangeActivity.class);
        } else {
            msg(this, "失败信息", "删除代理失败！");
        }
    }
}
