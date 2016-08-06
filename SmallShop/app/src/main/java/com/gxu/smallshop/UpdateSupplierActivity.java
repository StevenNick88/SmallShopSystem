package com.gxu.smallshop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gxu.smallshop.db.domain.Supplier;
import com.gxu.smallshop.utils.Base64Utils;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.DESUtils;
import com.gxu.smallshop.utils.HttpUtils;
import com.gxu.smallshop.utils.JsonService;

import java.util.HashMap;
import java.util.Map;


public class UpdateSupplierActivity extends BaseActivity implements View.OnClickListener{

    private EditText s_num_value, s_name_value,s_pwd_value,s_introduce_value;
    private Button update_supplier;
    private Supplier up_supplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_supplier);
        init();
    }

    private void init() {
        up_supplier = (Supplier)getIntent().getSerializableExtra("update_supplier");

        s_num_value=(EditText)this.findViewById(R.id.s_num_value);
        s_name_value=(EditText)this.findViewById(R.id.s_name_value);
        s_pwd_value=(EditText)this.findViewById(R.id.s_pwd_value);
        s_introduce_value=(EditText)this.findViewById(R.id.s_introduce_value);

        s_num_value.setText(up_supplier.getS_num());
        s_num_value.setEnabled(false);
        s_name_value.setText(up_supplier.getS_name());
        s_pwd_value.setText(up_supplier.getS_pwd());
        s_introduce_value.setText(up_supplier.getS_introduce());

        update_supplier =(Button)this.findViewById(R.id.update_supplier);
        update_supplier.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        updateSupplier();
    }

    private void updateSupplier() {
        // 判断输入框是否为空
        if (s_num_value.getText().toString().equals("")) {
            msg(this, "提示", "供应商编号不能为空！");
        }
        else if ( s_name_value.getText().toString().equals("")) {
            msg(this, "提示", "供应商姓名不能为空！");
        }
        else if ( s_pwd_value.getText().toString().equals("")) {
            msg(this, "提示", "供应商密码不能为空！");
        }
        else if ( s_introduce_value.getText().toString().equals("")) {
            msg(this, "提示", "供应商描述不能为空！");
        }else{
            msgWithEventDesEncryption(UpdateSupplierActivity.this, "提示", "确定", "取消");
        }
    }


    @Override
    public void onPositiveEventDesEncryption(String desValuesStr) {
        super.onPositiveEventDesEncryption(desValuesStr);
        Map<String, String> map = new HashMap<>();
        Boolean flag = null;
        try {
            map.put("s_num", s_num_value.getText().toString());
            map.put("s_name",  Base64Utils.encode(DESUtils.desCrypto(s_name_value.getText().toString().getBytes(), desValuesStr)));
            map.put("s_pwd", Base64Utils.encode(DESUtils.desCrypto(s_pwd_value.getText().toString().getBytes(), desValuesStr)));
            map.put("s_introduce", Base64Utils.encode(DESUtils.desCrypto(s_introduce_value.getText().toString().getBytes(), desValuesStr)));
            flag = HttpUtils.sendJavaBeanToServer(CommonUrl.UPDATE_SUPPLIER_URL,
                    JsonService.createJsonString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag == true) {
            msg(this, "成功信息", "修改供应商成功！");
            launch(SupplierMangeActivity.class);
        } else {
            msg(this, "失败信息", "修改供应商失败！");
        }
    }
}
