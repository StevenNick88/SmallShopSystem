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


public class MessageHistoryDetailActivity extends BaseActivity {
    private TextView ms_num_value,msbuyer_num_value,ms_question_value;
    private TextView ms_answer_value;
    private Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_history_detail);
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

        ms_answer_value= (TextView) this.findViewById(R.id.ms_answer_value);
        ms_answer_value.setText(message.getMs_answer());
    }

}
