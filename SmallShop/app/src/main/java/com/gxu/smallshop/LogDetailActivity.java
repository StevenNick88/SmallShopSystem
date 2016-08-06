package com.gxu.smallshop;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.gxu.smallshop.db.domain.Log;
import com.gxu.smallshop.db.domain.Message;


public class LogDetailActivity extends BaseActivity {
    private TextView l_num_value,log_user_num_value,log_content_value;
    private Log log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_detail);
        init();
    }

    private void init() {
        log = (Log)getIntent().getSerializableExtra("log");
        l_num_value= (TextView) this.findViewById(R.id.l_num_value);
        log_user_num_value= (TextView) this.findViewById(R.id.log_user_num_value);
        log_content_value= (TextView) this.findViewById(R.id.log_content_value);
        l_num_value.setText(log.getLog_num());
        log_user_num_value.setText(log.getUser_num());
        log_content_value.setText(log.getLog_content());
    }

}
