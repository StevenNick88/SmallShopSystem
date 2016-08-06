package com.gxu.smallshop.utils;

public class CommonUrl {
    //访问服务器数据的连接
    public static final String BASE_URL = "http://192.168.173.1:8080/";
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    public static final String UPLOAD_FILE = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=upload_file";
    public static final String LOAD_IMG = BASE_URL + "SmallShopServer/upload/";


    //普通用户登录向服务端发送数据url
    public static final String USER_LOGIN_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=userlogin";
    //代理登录向服务端发送数据url
    public static final String AGENT_LOGIN_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=agentlogin";
    //管理员登录向服务端发送数据url
    public static final String ADMIN_LOGIN_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=adminlogin";


    public static final String STUDENT_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=stu";
    public static final String STUDENTS_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=stus";
    public static final String ADD_STUDENT_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=addstu";
    public static final String UPDATE_STUDENT_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=updatestu";
    public static final String DELETE_STUDENT_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=deletestu";


    //查询supplier表中单条记录的url
    public static final String SUPPLIER_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=supplier";
    //向服务器修改supplier表中单条记录的url
    public static final String UPDATE_SUPPLIER_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=updatesupplier";
    //向服务器删除supplier表中单条记录的url
    public static final String DELETE_SUPPLIER_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=deletesupplier";
    //向服务器添加supplier表中单条记录的url
    public static final String ADD_SUPPLIER_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=addsupplier";


    //查询admin表中单条记录的url
    public static final String ADMIN_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=admin";
    //向服务器修改admin表中单条记录的url
    public static final String UPDATE_ADMIN_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=updateadmin";
    //向服务器删除admin表中单条记录的url
    public static final String DELETE_ADMIN_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=deleteadmin";
    //向服务器添加admin表中单条记录的url
    public static final String ADD_ADMIN_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=addadmin";


    //查询agent表中单条记录的url
    public static final String AGENT_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=agent";
    //向服务器修改agent表中单条记录的url
    public static final String UPDATE_AGENT_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=updateagent";
    //向服务器删除agent表中单条记录的url
    public static final String DELETE_AGENT_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=deleteagent";
    //向服务器添加agent表中单条记录的url
    public static final String ADD_AGENT_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=addagent";


    //查询user表中单条记录的url
    public static final String USER_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=user";
    //查询admin表中所有记录的url
//    public static final String ADMINS_URL=BASE_URL+"SmallShopServer/servlet/JsonAction?action_flag=admins";
    //向服务器添加user表中单条记录的url
    public static final String ADD_USER_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=adduser";
    //向服务器修改user表中单条记录的url
    public static final String UPDATE_USER_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=updateuser";
    //向服务器删除user表中单条记录的url
    public static final String DELETE_USER_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=deleteuser";


    //根据商品编号查询goods表中单条记录的url
    public static final String GOODS_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=q_goods_with_g_num";
    //根据商品名查询goods表中单条记录的url
    public static final String GOODS_URL_WITH_G_NAME = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=q_goods_with_g_name";
    //根据商品类型查询goods表中单条记录的url
    public static final String GOODS_URL_WITH_G_TYPE = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=q_goods_with_g_type";
    public static final String BOOK_URL_WITH_B_PRESS = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=q_book_with_b_press";
    //根据商品名称，商品类型查询goods表中单条记录的url
    public static final String GOODS_URL_WITH_G_NAME_TYPE = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=q_goods_with_g_name_type";


    public static final String BOOKS_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=books";
    //向服务器添加goods表中单条记录的url
    public static final String ADD_GOODS_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=addgoods";
    //向服务器修改goods表中单条记录的url
    public static final String UPDATE_GOODS_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=updategoods";
    //向服务器删除goods表中单条记录的url
    public static final String DELETE_GOODS_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=deletegoods";


    //留言板
    public static final String MESSAGE_LAST_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=message_last_url";
    public static final String MESSAGES_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=messages";
    public static final String UPDATE_MESSAGE_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=updatemessage";
    public static final String ADD_MESSAGE_USER_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=add_message_user";


    //添加销售日志
    public static final String ADD_BUY_GOODS_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=addbuygoods";
    public static final String BUY_GOODS_WITH_USER_NUM_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=buygoods_with_buyer_num";


    public static final String BORROW_BOOK_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=borrowbook";
    public static final String BORROW_BOOKS_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=borrowbooks";
    public static final String BORROW_BOOK_WITH_OVERTIME_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=borrowbook_with_overtime";
    public static final String BORROW_BOOK_WITH_OVERTIME_USER_NUM_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=borrowbook_with_overtime_usernum";



    public static final String RETURN_BORROW_BOOK_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=returnborrowbook";
    public static final String RETURN_BORROW_BOOK_PRE_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=returnborrowbookpre";
    public static final String AGAIN_BORROW_BOOK_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=againborrowbook";
    public static final String ADD_ORDER_BOOK_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=addorderbook";
    public static final String ORDER_BOOKS_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=orderbooks";
    public static final String ORDER_BOOK_WITH_USER_NUM_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=orderbookwith_user_num";
    public static final String ORDER_BOOK_WITH_USER_NUM_B_BUM_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=orderbookwith_user_num_b_num";
    public static final String CANCEL_ORDER_BOOK_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=cancel_orderbook";


    //图书挂失信息表
    public static final String ADD_LOSS_BOOK_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=addlossbook";
    //查询图书挂失信息表所有记录
    public static final String LOSS_BOOKS_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=lossbooks";
    //查询图书挂失信息表单条记录
    public static final String LOSS_BOOK_WITH_USER_NAME_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=lossbookwith_user_num";
    public static final String UPDATE_LOSS_BOOK_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=updatelossbook";


    //交纳欠费url:修改boorowedbook表中的state字段和其他字段的相应信息
    public static final String PAY_FREE_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=pay_free";


    //修改密码组
    public static final String UPDATE_USER_PWD = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=update_user_pwd";
    public static final String UPDATE_AGENT_PWD = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=update_agent_pwd";
    public static final String UPDATE_SUPPLIER_PWD = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=update_supplier_pwd";
    public static final String UPDATE_ADMIN_PWD = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=update_admin_pwd";


    //系统日志组
    public static final String LOGS_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=logs";
    public static final String ADD_LOG_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=add_log_url";
    public static final String LOG_LAST_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=log_last_url";


    //备份数据组
    public static final String ADMINS_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=admins";
    public static final String AGENTS_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=agents";
    public static final String BUYEDGOODS_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=buyedgoods";
    public static final String GOODS_LIST_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=goods_list";
    public static final String SUPPLIERS_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=suppliers";
    public static final String USERS_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=users";

    public static String[] backup_urls = new String[]{
            CommonUrl.ADMINS_URL,
            CommonUrl.AGENTS_URL,
            CommonUrl.BUYEDGOODS_URL,
            CommonUrl.GOODS_LIST_URL,
            CommonUrl.LOGS_URL,
            CommonUrl.MESSAGES_URL,
            CommonUrl.SUPPLIERS_URL,
            CommonUrl.USERS_URL
    };

    //过期数据删除组
    public static final String GOODS_AND_BUYEDGOODS_DEL_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=goods_and_buyedgoods_del";

    //系统初始化组
    public static final String SYSTEM_INITIALIZATION_URL = BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=system_initialization";

    //注册
    public static final String BUYER_LAST_URL= BASE_URL + "SmallShopServer/servlet/JsonAction?action_flag=buyer_last_url";


}
