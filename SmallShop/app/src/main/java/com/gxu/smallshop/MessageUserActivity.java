package com.gxu.smallshop;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gxu.smallshop.db.domain.Agent;
import com.gxu.smallshop.db.domain.Message;
import com.gxu.smallshop.db.domain.User;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.JsonService;
import com.gxu.smallshop.utils.JsonTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MessageUserActivity extends BaseActivity implements View.OnClickListener{
    private Button history_ms_query,ms_submit;
    private EditText user_ms_value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_user);
        init();
    }

    private void init() {
        history_ms_query= (Button) this.findViewById(R.id.history_ms_query);
        ms_submit= (Button) this.findViewById(R.id.ms_submit);
        history_ms_query.setOnClickListener(this);
        ms_submit.setOnClickListener(this);
        user_ms_value= (EditText) this.findViewById(R.id.user_ms_value);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //历史留言查询
            case R.id.history_ms_query:
                launch(MessageHistoryActivity.class);

                break;
            //用户提交留言
            case R.id.ms_submit:
                submitMessage();
                break;
        }

    }

    private void submitMessage() {
        // 判断输入框是否为空
        if (user_ms_value.getText().toString().equals("")) {
            msg(this, "提示", "回复内容不能为空！");
        }else {
            //首先查询现在数据库中message编号的最大值
            String jsonString = HttpUtils.getJsonContent(CommonUrl.MESSAGE_LAST_URL);
            Message message = JsonTools.getMessage("message", jsonString);
            Map<String, String> map = new HashMap<>();
            if ((message!=null)&& (message.getMs_num()!=null)){
                //将编号加1
                int ms_num=Integer.parseInt(message.getMs_num());
                map.put("ms_num", String.valueOf(++ms_num));
            }else {
                map.put("ms_num", String.valueOf(1));
            }
            map.put("ms_question", user_ms_value.getText().toString());

            Map<String, Object> userMap = LoginActivity.getNowUserMap();
            for (String key : userMap.keySet()) {
                if (key.equals("学生")) {
                    User now_user = (User) userMap.get(key);
                    map.put("buyer_num", now_user.getU_num());
                }
                else if (key.equals("代理")) {
                    Agent now_user = (Agent) userMap.get(key);
                    map.put("buyer_num", now_user.getA_num());
                }
            }
            Boolean flag1 = HttpUtils.sendJavaBeanToServer(CommonUrl.ADD_MESSAGE_USER_URL,
                    JsonService.createJsonString(map));
            if (flag1 == true) {
                msgWithPositiveButton(this,"成功信息","确定", "提交成功！");
            } else {
                msg(this, "失败信息", "提交失败！");
            }
            //只能提交一次留言
            ms_submit.setEnabled(false);
        }
    }

    @Override
    public void onPositiveEvent() {
        super.onPositiveEvent();
        launch(BuyerMainActivity.class);
    }
}

