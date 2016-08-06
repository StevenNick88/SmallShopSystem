package com.gxu.smallshop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gxu.smallshop.db.domain.Supplier;
import com.gxu.smallshop.utils.CommonUrl;
import com.gxu.smallshop.utils.HttpUtils;


public class SupplierInfoActivity extends BaseActivity implements View.OnClickListener{

    private TextView s_num_value,s_name_value,s_pwd_value,s_introduce_value;
    private Button update_supplier,delete_supplier;
    private Supplier supplier_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_info);
        init();

    }

    private void init() {
        supplier_query = (Supplier)getIntent().getSerializableExtra("supplier_query");

        s_num_value=(TextView)this.findViewById(R.id.s_num_value);
        s_name_value=(TextView)this.findViewById(R.id.s_name_value);
        s_pwd_value=(TextView)this.findViewById(R.id.s_pwd_value);
        s_introduce_value=(TextView)this.findViewById(R.id.s_introduce_value);

        s_num_value.setText(supplier_query.getS_num());
        s_name_value.setText(supplier_query.getS_name());
        s_pwd_value.setText(supplier_query.getS_pwd());
        s_introduce_value.setText(supplier_query.getS_introduce());

        update_supplier=(Button)this.findViewById(R.id.update_supplier);
        delete_supplier=(Button)this.findViewById(R.id.delete_supplier);
        update_supplier.setOnClickListener(this);
        delete_supplier.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.update_supplier:
                Bundle bundle = new Bundle();
                bundle.putSerializable("update_supplier", supplier_query);
                launch(UpdateSupplierActivity.class,bundle);
                break;
            case R.id.delete_supplier:
                deleteSupplier();
                break;
        }
    }

    private void deleteSupplier() {
        Boolean flag = HttpUtils.sendJavaBeanToServer(CommonUrl.DELETE_SUPPLIER_URL,
                s_num_value.getText().toString());
        if (flag == true) {
            msg(this, "成功信息", "删除供应商成功！");
            launch(SupplierMangeActivity.class);
        } else {
            msg(this, "失败信息", "删除供应商失败！");
        }
    }
}
