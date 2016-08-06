package com.gxu.smallshop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gxu.smallshop.db.domain.Admin;
import com.gxu.smallshop.db.domain.Agent;
import com.gxu.smallshop.db.domain.Log;
import com.gxu.smallshop.db.domain.User;
import com.gxu.smallshop.receiver.BookSystemReceiver;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.JsonService;
import com.gxu.smallshop.utils.JsonTools;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private final String SHARED_PREFERENCES_NAME = "sharedPreferences";
    private EditText user_value;
    private EditText password_value;
    private Button login;
    private Button register;
    private RadioGroup group;
    private String userType;
    private static Object nowUser = null;
    private static Map<String, Object> map = null;
    private BookSystemReceiver receiver;
    private String username;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        receiver=new BookSystemReceiver();

        // 当程序进入主页面的时候，他之后启动肯定就不是第一次启动了。所以我们可以在界面，或者是调用主页面的步骤中将他的状态设为false.
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFirstIn", true);
        // 提交修改
        editor.commit();
        //那么这样就可以实现了，当程序第二次进入的时候，在启动也就进行了判断。。if() else() 就执行了你想让他执行的操作。

        user_value = (EditText) findViewById(R.id.user_value);
        password_value = (EditText) findViewById(R.id.password_value);
        group = (RadioGroup) this.findViewById(R.id.person);
        RadioButton student = (RadioButton) group.findViewById(R.id.student);
        student.setChecked(true);
        login = (Button) this.findViewById(R.id.login);
        register = (Button) this.findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

//    //注册广播
//    @Override
//    protected void onResume() {
//        super.onResume();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(receiver, filter);
//    }
//
//    // 卸载广播
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (receiver != null) {
//            unregisterReceiver(receiver);
//        }
//    }


    // 登录按钮监听
    public void login() {
        username = user_value.getText().toString();
        password = password_value.getText().toString();
        // 判断用户名是否为空
        if (username.equals("")) {
            msg(this, "提示", "用户名不能为空！");
        }
        // 判断密码是否为空
        else if (password.equals("")) {
            msg(this, "提示", "密码不能为空！");
        }
        // 输入不为空,提示输入秘钥
        else {
            msgWithEventDesDecryption(LoginActivity.this, "提示", "确定", "取消");
        }
    }

    private void onPositiveEventDesDecryption(String desValuesStr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int len = group.getChildCount();// 获得单选按钮组的选项个数
        for (int i = 0; i < len; i++) {
            RadioButton radioButton = (RadioButton) group.getChildAt(i);
            if (radioButton.isChecked()) {
                userType = radioButton.getText().toString();
                break;
            }
        }
        if (userType == null) {
            msg(this, "提示", "请选择用户类型");
        } else if (userType.equals("买家")) {
            Map<String, String> map = new HashMap<>();
            map.put("u_num", username);
            String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.USER_LOGIN_URL,
                    JsonService.createJsonString(map));
            User user = JsonTools.getUser("user", jsonString,desValuesStr);
            if (user.getU_pwd() != null && user.getU_pwd().equals(password)) {
                //首先查询现在数据库中log编号的最大值
                String jsonString2 = HttpUtils.getJsonContent(CommonUrl.LOG_LAST_URL);
                Log log = JsonTools.getLog("log", jsonString2);
                Map<String, String> map2 = new HashMap<>();
                if (log != null) {
                    //将编号加1
                    int ms_num = Integer.parseInt(log.getLog_num());
                    map2.put("log_num", String.valueOf(++ms_num));
                } else {
                    map2.put("log_num", String.valueOf(1));
                }

                String log_content = "编号为" + user.getU_num() + "的用户在" + df.format(new Date()) + "登录了系统";
                map2.put("log_content", log_content);
                map2.put("user_num", user.getU_num());
                Boolean flag1 = HttpUtils.sendJavaBeanToServer(CommonUrl.ADD_LOG_URL,
                        JsonService.createJsonString(map2));

                nowUser = user;
                setNowUserMap(userType);

                Bundle bundle = new Bundle();
                bundle.putString("user_type", userType);
                bundle.putSerializable("now_user", (Serializable) user);
                launch(BaseActivity.class, bundle);
                finish();
            } else {
                msg(this, "失败信息", "用户名或密码错误，请重新输入！");
                user_value.setText("");
                password_value.setText("");
            }

        } else if (userType.equals("代理")) {
            Map<String, String> map = new HashMap<>();
            map.put("a_num", username);
            String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.AGENT_LOGIN_URL,
                    JsonService.createJsonString(map));
            Agent agent = JsonTools.getAgent("agent", jsonString,desValuesStr);
            if (agent.getA_pwd() != null && agent.getA_pwd().equals(password)) {
                //首先查询现在数据库中log编号的最大值
                String jsonString2 = HttpUtils.getJsonContent(CommonUrl.LOG_LAST_URL);
                Log log = JsonTools.getLog("log", jsonString2);
                Map<String, String> map2 = new HashMap<>();
                if (log != null) {
                    //将编号加1
                    int ms_num = Integer.parseInt(log.getLog_num());
                    map2.put("log_num", String.valueOf(++ms_num));
                } else {
                    map2.put("log_num", String.valueOf(1));
                }

                String log_content = "编号为" + agent.getA_num() + "的用户在" + df.format(new Date()) + "登录了系统";
                map2.put("log_content", log_content);
                map2.put("user_num", agent.getA_num());
                Boolean flag1 = HttpUtils.sendJavaBeanToServer(CommonUrl.ADD_LOG_URL,
                        JsonService.createJsonString(map2));

                nowUser = agent;
                setNowUserMap(userType);

                Bundle bundle = new Bundle();
                bundle.putString("user_type", userType);
                bundle.putSerializable("now_user", (Serializable) agent);
                launch(BaseActivity.class, bundle);
                finish();
            } else {
                msg(this, "失败信息", "用户名或密码错误，请重新输入！");
                user_value.setText("");
                password_value.setText("");
            }

        } else if (userType.equals("管理员")) {
            Map<String, String> map = new HashMap<>();
            map.put("m_num", username);
            String jsonString = HttpUtils.sendInfoToServerGetJsonData(CommonUrl.ADMIN_LOGIN_URL,
                    JsonService.createJsonString(map));
            Admin admin = JsonTools.getAdmin("admin", jsonString,desValuesStr);
            if (admin.getM_pwd() != null && admin.getM_pwd().equals(password)) {
                //首先查询现在数据库中log编号的最大值
                String jsonString2 = HttpUtils.getJsonContent(CommonUrl.LOG_LAST_URL);
                Log log = JsonTools.getLog("log", jsonString2);
                Map<String, String> map2 = new HashMap<>();
                if ((log != null) && (log.getLog_num() != null)) {
                    //将编号加1
                    int ms_num = Integer.parseInt(log.getLog_num());
                    map2.put("log_num", String.valueOf(++ms_num));
                } else {
                    map2.put("log_num", String.valueOf(1));
                }

                String log_content = "编号为" + admin.getM_num() + "的用户在" + df.format(new Date()) + "登录了系统";
                map2.put("log_content", log_content);
                map2.put("user_num", admin.getM_num());
                Boolean flag1 = HttpUtils.sendJavaBeanToServer(CommonUrl.ADD_LOG_URL,
                        JsonService.createJsonString(map2));

                nowUser = admin;
                setNowUserMap(userType);

                Bundle bundle = new Bundle();
                bundle.putString("user_type", userType);
                bundle.putSerializable("now_user", (Serializable) admin);
                launch(BaseActivity.class, bundle);
                finish();
            } else {
                msg(this, "失败信息", "用户名或密码错误，请重新输入！");
                user_value.setText("");
                password_value.setText("");
            }
        }
    }

    public static void setNowUserMap(String key) {
        map = new HashMap<>();
        if (nowUser != null) {
            map.put(key, nowUser);
        } else {
            map.put(key, null);
        }
    }

    public static Map<String, Object> getNowUserMap() {
        return map;
    }

    public static Object getNowUser() {
        if (nowUser != null) {
            return nowUser;
        }
        return "";
    }

    public static void setNowUser(Object nowUser) {
        LoginActivity.nowUser = nowUser;
    }

    private void register() {
        launch(RegisterActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                login();
                break;
            case R.id.register:
                register();
                break;
            default:
                break;
        }
    }

    // des解密消息对话框
    public void msgWithEventDesDecryption(Context context, String title, String positiveButton, String negativeButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        View view = LayoutInflater.from(LoginActivity.this).inflate(
                R.layout.custom_dialog, null);
        final EditText des_values = (EditText) view.findViewById(R.id.des_values);
        builder.setView(view);
        builder.setIcon(R.drawable.ic_dialog_alert_holo_light);
        builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String desValuesStr = des_values.getText().toString();
                if ((desValuesStr == null) || desValuesStr.equals("")) {
                    msg(LoginActivity.this, "提示", "秘钥不能为空");
                } else if (desValuesStr.length() % 8 != 0) {
                    msg(LoginActivity.this, "提示", "密钥长度必须是8的倍数");
                } else {
                    onPositiveEventDesDecryption(desValuesStr);
                }
            }
        });
        builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create();
        builder.show();
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

    public void launch(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        launch_slideright2left(intent);
    }

    public void launch(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
        overridePendingTransition(R.anim.push_right_in, android.R.anim.fade_out);
    }
    public void launch_slideright2left(Intent it) {
        startActivity(it);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

}
