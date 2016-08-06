package com.gxu.smallshop;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gxu.smallshop.db.domain.Message;
import com.gxu.smallshop.db.domain.User;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.JsonService;

import java.util.HashMap;
import java.util.Map;


public class MessageAdminDetailActivity extends BaseActivity implements View.OnClickListener{
    private TextView ms_num_value,msbuyer_num_value,ms_question_value;
    private EditText ms_answer_value;
    private Button ms_reply;
    private Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_admin_detail);
        init();
    }

    private void init() {
        //获得留言列表界面传过来的Message
        message = (Message)getIntent().getSerializableExtra("message");
        ms_num_value= (TextView) this.findViewById(R.id.ms_num_value);
        msbuyer_num_value= (TextView) this.findViewById(R.id.msbuyer_num_value);
        ms_question_value= (TextView) this.findViewById(R.id.ms_question_value);
        ms_num_value.setText(message.getMs_num());
        msbuyer_num_value.setText(message.getBuyer_num());
        ms_question_value.setText(message.getMs_question());

        ms_answer_value= (EditText) this.findViewById(R.id.ms_answer_value);

        ms_reply= (Button) this.findViewById(R.id.ms_reply);
        ms_reply.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        // 判断输入框是否为空
        if (ms_answer_value.getText().toString().equals("")) {
            msg(this, "提示", "回复内容不能为空！");
        }else {
            Map<String, String> map = new HashMap<>();
            map.put("ms_num", ms_num_value.getText().toString());
            map.put("ms_answer", ms_answer_value.getText().toString());
            Boolean flag1 = HttpUtils.sendJavaBeanToServer(CommonUrl.UPDATE_MESSAGE_URL,
                    JsonService.createJsonString(map));
            if (flag1 == true) {
                msg(this, "成功信息", "回复成功！");
            } else {
                msg(this, "失败信息", "回复失败！");
            }
        }

    }
}
