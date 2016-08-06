package com.gxu.smallshop;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.JsonService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class OutdatedDeleteActivity extends BaseActivity implements View.OnClickListener {
    private Button select_date, del_ensure, del_cancel;
    private TextView date_value, del_message;
    private SimpleDateFormat sdf;
    private Date date;
    private int year, monthOfYear, dayOfMonth;
    private long del_dateLong;
    private String del_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdated_delete);
        init();
    }

    private void init() {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -60);
        del_date = sdf.format(c.getTime());
        del_dateLong=c.getTimeInMillis();


        select_date = (Button) this.findViewById(R.id.select_date);
        del_ensure = (Button) this.findViewById(R.id.del_ensure);
        del_cancel = (Button) this.findViewById(R.id.del_cancel);
        select_date.setOnClickListener(this);
        del_ensure.setOnClickListener(this);
        del_cancel.setOnClickListener(this);

        date_value = (TextView) this.findViewById(R.id.date_value);
        date_value.setText(del_date);


        del_message = (TextView) this.findViewById(R.id.del_message);
        del_message.setText("你选择的日期是" + del_date + ",这将删除该日期前的所有数据，确定要删除吗？");

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_date:
                new DatePickerDialog(OutdatedDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String text = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        try {
                            long nowDateLong = sdf.parse(text).getTime();
                            if (nowDateLong > del_dateLong) {
                                msg(OutdatedDeleteActivity.this, "提示", "请选择" + del_date + "之前的数据，两个月之内的数据不可删除！");
                            } else {
                                date_value.setText(text);
                                del_message.setText("你选择的日期是" + text + ",这将删除该日期前的所有数据，确定要删除吗？");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, year, monthOfYear, dayOfMonth).show();
                break;
            case R.id.del_ensure:
                Map<String, String> map = new HashMap<>();
                map.put("del_date", date_value.getText().toString());
                Boolean flag1 = HttpUtils.sendJavaBeanToServer(CommonUrl.GOODS_AND_BUYEDGOODS_DEL_URL,
                        JsonService.createJsonString(map));
                if ((flag1 == true)) {
                    msg(this, "成功信息", "删除数据成功！");
                } else {
                    msg(this, "失败信息", "删除数据失败！");
                }
//                launch(SystemMangeActivity.class);
                break;
            case R.id.del_cancel:
                launch(SystemMangeActivity.class);
                break;
        }

    }
}
