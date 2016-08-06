package com.json.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jdbc.dbutils.JdbcUtils;
import com.jdbc.dbutils.domain.Admin;
import com.jdbc.dbutils.domain.Agent;
import com.jdbc.dbutils.domain.Book;
import com.jdbc.dbutils.domain.BorrowedBook;
import com.jdbc.dbutils.domain.BuyedGoods;
import com.jdbc.dbutils.domain.Goods;
import com.jdbc.dbutils.domain.Log;
import com.jdbc.dbutils.domain.LossBook;
import com.jdbc.dbutils.domain.Message;
import com.jdbc.dbutils.domain.OrderedBook;
import com.jdbc.dbutils.domain.Student;
import com.jdbc.dbutils.domain.Supplier;
import com.jdbc.dbutils.domain.User;
import com.json.service.JsonService;
import com.json.tools.JsonTools;
import com.json.tools.JsonTools2;

public class JsonAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletFileUpload upload;
	private final long MAXSize = 4194304 * 2L;// 4*2MB
	private String filedir = null;

	private static final String FLAG = "updatedImage";
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	private JsonService service;
	private String jsonString = "";
	private String filename = "";
	private String addBookuploadPictureState = "";
	private String updateBookuploadPictureState = "";

	public JsonAction() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String action_flag = request.getParameter("action_flag");
		// 文件上传
		if (action_flag.equals("upload_file")) {
			uploadFile(request, response);
		}

		// 修改密码组
		else if (action_flag.equals("update_user_pwd")) {
			String sql = "update user set  u_pwd = ?  where u_num = ? ";
			updatePwdFromDateBase(request, sql);
		} else if (action_flag.equals("update_agent_pwd")) {
			String sql = "update agent set  a_pwd = ?  where a_num = ? ";
			updatePwdFromDateBase(request, sql);
		} else if (action_flag.equals("update_supplier_pwd")) {
			String sql = "update supplier set  s_pwd = ?  where s_num = ? ";
			updatePwdFromDateBase(request, sql);
		} else if (action_flag.equals("update_admin_pwd")) {
			String sql = "update admin set  m_pwd = ?  where m_num = ? ";
			updatePwdFromDateBase(request, sql);
		}

		// 日志组
		else if (action_flag.equals("logs")) {
			jsonString = JsonTools.createJsonString("logs",
					service.getlistLogs());
		} else if (action_flag.equals("add_log_url")) {
			addLogToDateBase(request);
		} else if (action_flag.equals("log_last_url")) {
			queryLogLastFromDateBase(request);
		}
		
		// 买家注册
		else if (action_flag.equals("buyer_last_url")) {
			queryBuyerLastFromDateBase(request);
		}
		
		

		// 留言板组
		else if (action_flag.equals("message_last_url")) {
			queryMessageLastFromDateBase(request);
		} else if (action_flag.equals("messages")) {
			jsonString = JsonTools.createJsonString("messages",
					service.getlistMessage());
		} else if (action_flag.equals("updatemessage")) {
			updateMessageToDateBase(request);
		} else if (action_flag.equals("add_message_user")) {
			addMessageUserToDateBase(request);
		}

		// 过期数据删除组
		else if (action_flag.equals("goods_and_buyedgoods_del")) {
			deleteOverTimeDateFromDateBase(request);
		}
		// 系统初始化组
		else if (action_flag.equals("system_initialization")) {
			deleteAllDateFromDateBase(request);
		}
		
		

		// 学生组
		else if (action_flag.equals("stu")) {
			queryStudentFromDateBase(request);
		} else if (action_flag.equals("stus")) {
			jsonString = JsonTools.createJsonString("stus",
					service.getListSupplier());
		} else if (action_flag.equals("addstu")) {
			addStudentToDateBase(request);
		} else if (action_flag.equals("updatestu")) {
			updateStudentToDateBase(request);
		} else if (action_flag.equals("deletestu")) {
			deleteStudentFromDateBase(request);
		}

		// 供应商组

		else if (action_flag.equals("suppliers")) {
			jsonString = JsonTools.createJsonString("suppliers",
					service.getListSupplier());
		} else if (action_flag.equals("supplier")) {
			querySupplierFromDateBase(request);
		} else if (action_flag.equals("addsupplier")) {
			addSupplierToDateBase(request);
		} else if (action_flag.equals("updatesupplier")) {
			updateSupplierToDateBase(request);
		} else if (action_flag.equals("deletesupplier")) {
			deleteSupplierFromDateBase(request);
		}

		// 代理组
		else if (action_flag.equals("agent")) {
			queryAgentFromDateBase(request);
		} else if (action_flag.equals("agents")) {
			jsonString = JsonTools.createJsonString("agents",
					service.getListAgent());
		} else if (action_flag.equals("addagent")) {
			addAgentToDateBase(request);
		} else if (action_flag.equals("updateagent")) {
			updateAgentToDateBase(request);
		} else if (action_flag.equals("deleteagent")) {
			deleteAgentFromDateBase(request);
		}

		// 管理员组
		else if (action_flag.equals("admin")) {
			queryAdminFromDateBase(request);
		} else if (action_flag.equals("admins")) {
			jsonString = JsonTools.createJsonString("admins",
					service.getListAdmin());
		} else if (action_flag.equals("addadmin")) {
			addAdminToDateBase(request);
		} else if (action_flag.equals("updateadmin")) {
			updateAdminToDateBase(request);
		} else if (action_flag.equals("deleteadmin")) {
			deleteAdminFromDateBase(request);
		}

		// 买家组

		else if (action_flag.equals("users")) {
			jsonString = JsonTools.createJsonString("users",
					service.getListUser());
		} else if (action_flag.equals("user")) {
			queryUserFromDateBase(request);
		} else if (action_flag.equals("adduser")) {
			addUserToDateBase(request);
		} else if (action_flag.equals("updateuser")) {
			updateUserToDateBase(request);
		} else if (action_flag.equals("deleteuser")) {
			deleteUserFromDateBase(request);
		}

		// 商品组：增删改查
		else if (action_flag.equals("q_goods_with_g_num")) {
			queryGoodsFromDateBaseWithG_num(request);
		} else if (action_flag.equals("q_goods_with_g_name")) {
			queryGoodsFromDateBaseWithG_name(request);
		} else if (action_flag.equals("q_goods_with_g_type")) {
			queryGoodsFromDateBaseWithG_type(request);
			// } else if (action_flag.equals("q_book_with_b_press")) {
			// queryBookFromDateBaseWithB_press(request);
		} else if (action_flag.equals("q_goods_with_g_name_type")) {
			queryGoodsFromDateBaseWithG_name_type(request);
		} else if (action_flag.equals("goods_list")) {
			jsonString = JsonTools.createJsonString("goods_list",
					service.getListGoods());
		} else if (action_flag.equals("addgoods")) {
			addGoodsToDateBase(request);
		} else if (action_flag.equals("updategoods")) {
			updateGoodsToDateBase(request);
		} else if (action_flag.equals("deletegoods")) {
			deleteGoodsFromDateBase(request);
		}

		// 图书组：借书，预约，还书，交纳欠费
		// 查询图书借阅信息表中单条记录
		else if (action_flag.equals("borrowbook")) {
			queryBorrowBookFromDateBase(request);
		} else if (action_flag.equals("buygoods_with_buyer_num")) {
			queryBuyedGoodsFromDateBaseWith_usernum(request);
		} else if (action_flag.equals("buyedgoods")) {
			jsonString = JsonTools.createJsonString("buyedgoods",
					service.getlistBuyedGoods());
		}
		// 查询图书借阅信息表中overtime>0 && user_num=? 的数据集合
		else if (action_flag.equals("borrowbook_with_overtime_usernum")) {
			queryBorrowedBookFromDateBaseWith_overtime_usernum(request);
		}
		// 查询图书借阅信息表中所有记录
		else if (action_flag.equals("borrowbooks")) {
			jsonString = JsonTools.createJsonString("borrowbooks",
					service.getListBorrowedBook());
		}
		// 购买商品
		else if (action_flag.equals("addbuygoods")) {
			addBuyGoodsFromDateBase(request);
		}
		// 还书之前查询服务端计算好的overtime和remain_time的值，看是否超期，为还书做准备
		else if (action_flag.equals("returnborrowbookpre")) {
			returnBorrowBookFromDateBasePre(request, response);
		}
		// 还书
		else if (action_flag.equals("returnborrowbook")) {
			returnBorrowBookFromDateBase(request, response);
		}
		// 续借
		else if (action_flag.equals("againborrowbook")) {
			againBorrowBookFromDateBase(request);
		}
		// 预约
		else if (action_flag.equals("addorderbook")) {
			addOrderBookFromDateBase(request);
		}
		// 取消预约
		else if (action_flag.equals("cancel_orderbook")) {
			cancelOrderBookFromDateBase(request);
		}
		// 单个用户的预约集合
		else if (action_flag.equals("orderbookwith_user_num")) {
			orderBookFromDateBaseWith_usernum(request);
		}
		// 单个用户的预约记录
		else if (action_flag.equals("orderbookwith_user_num_b_num")) {
			orderBookFromDateBaseWith_usernum_b_num(request);
		}

		// 查询图书预约信息表中所有记录
		else if (action_flag.equals("orderbooks")) {
			jsonString = JsonTools.createJsonString("orderbooks",
					service.getListOrderBook());
		}
		// 挂失
		else if (action_flag.equals("addlossbook")) {
			addLossBookFromDateBase(request);
		}
		// 交纳欠款取消挂失
		else if (action_flag.equals("updatelossbook")) {
			updateLossBookFromDateBase(request);
		}
		// 查询图书挂失信息表中user_num=？的记录
		else if (action_flag.equals("lossbookwith_user_num")) {
			queryLossBookFromDateBase(request);
		}
		// 查询图书挂失信息表中所有记录
		else if (action_flag.equals("lossbooks")) {
			jsonString = JsonTools.createJsonString("lossbooks",
					service.getListLossBook());
		}

		// 交纳欠费:修改borrowedbook表中的state字段和其他字段的相应信息
		else if (action_flag.equals("pay_free")) {
			payFreeFromDateBase(request);
		}

		// 登录组
		else if (action_flag.equals("userlogin")) {
			stuLoginFromDateBase(request);
		} else if (action_flag.equals("adminlogin")) {
			adminLoginFromDateBase(request);
		}
		else if (action_flag.equals("agentlogin")) {
			agentLoginFromDateBase(request);
		}

		// 控制台显示日志
		System.out.println("服务器向客户端发送的数据---->" + jsonString);
		// 向客服端发送数据
		out.println(jsonString);
		// 每次向客户端发送数据后清空jsonString
		jsonString = "";
		out.flush();
		out.close();
	}

	private void queryBuyerLastFromDateBase(HttpServletRequest request) {
		User user = service.getLastUser();
		if (user != null) {
			jsonString = JsonTools.createJsonString("buyer_last", user);
		}		
	}

	private void agentLoginFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String username = (String) map.get("a_num");
		Agent agent = service.getAgent(username);
		if (agent != null) {
			jsonString = JsonTools.createJsonString("agent", agent);
		}		
	}

	private void deleteAllDateFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		String del_date = getClientDate(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		List<String> sqlList = new ArrayList<String>();
		String sql1 = " delete from admin";
		String sql2 = " delete from agent";
		String sql3 = " delete from buyedgoods";
		String sql4 = " delete from goods";
		String sql5 = " delete from log";
		String sql6 = " delete from message";
		String sql7 = " delete from supplier";
		String sql8 = " delete from user";
		String sql9 = "insert into admin( m_num,  m_name,  m_pwd, m_remark)"
				+ "values('001','admin','admin','超级管理员')";
		sqlList.add(sql1);
		sqlList.add(sql2);
		sqlList.add(sql3);
		sqlList.add(sql4);
		sqlList.add(sql5);
		sqlList.add(sql6);
		sqlList.add(sql7);
		sqlList.add(sql8);
		sqlList.add(sql9);
		try {
			flag = jdbcUtils.updateByStatementBatch(sqlList);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}		
	}

	private void deleteOverTimeDateFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		String del_date = getClientDate(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		List<String> sqlList = new ArrayList<String>();
		String sql1 = " delete from buyedgoods where  buy_time  < "+"'"+del_date +"'";
		String sql2 = " delete from goods where  g_time  < "+"'"+del_date +"'";
		sqlList.add(sql1);
		sqlList.add(sql2);
		try {
			flag = jdbcUtils.updateByStatementBatch(sqlList);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

		
	}

	private void queryAdminsFromDateBase(HttpServletRequest request) {
		// TODO 自动生成的方法存根

	}

	private void queryLogLastFromDateBase(HttpServletRequest request) {
		Log log = service.getLog();
		if (log != null) {
			jsonString = JsonTools.createJsonString("log", log);
		}
	}

	private void queryMessageLastFromDateBase(HttpServletRequest request) {
		Message message = service.getMessage();
		if (message != null) {
			jsonString = JsonTools.createJsonString("message", message);
		}

	}

	private void addLogToDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "insert into  log (log_num, log_content, user_num)"
				+ "values(?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("log_num"));
		params.add(map.get("log_content"));
		params.add(map.get("user_num"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}
	}

	private void updatePwdFromDateBase(HttpServletRequest request, String sql) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		// String sql = "update user set  u_pwd = ?  where u_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("user_password"));
		params.add(map.get("user_num"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

	}

	private void addMessageUserToDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "insert into  message (ms_num, ms_question, buyer_num)"
				+ "values(?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("ms_num"));
		params.add(map.get("ms_question"));
		params.add(map.get("buyer_num"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

	}

	private void updateMessageToDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "update message set  ms_answer = ?  where ms_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("ms_answer"));
		params.add(map.get("ms_num"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

	}

	private void addAdminToDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "insert into admin( m_num,  m_name,  m_pwd, m_remark)"
				+ "values(?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("m_num"));
		params.add(map.get("m_name"));
		params.add(map.get("m_pwd"));
		params.add(map.get("m_remark"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}
	}

	private void deleteAdminFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		String m_num = getClientDate(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "delete from admin where m_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(m_num);
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}
	}

	private void updateAdminToDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "update admin set  m_name = ?, m_pwd = ?,m_remark = ? where m_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("m_name"));
		params.add(map.get("m_pwd"));
		params.add(map.get("m_remark"));
		params.add(map.get("m_num"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

	}

	private void queryAdminFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String m_num = (String) map.get("m_num");
		Admin admin = service.getAdmin(m_num);
		if (admin != null) {
			jsonString = JsonTools.createJsonString("admin", admin);
		}
	}

	private void addSupplierToDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "insert into supplier( s_num,  s_name,  s_pwd, s_introduce)"
				+ "values(?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("s_num"));
		params.add(map.get("s_name"));
		params.add(map.get("s_pwd"));
		params.add(map.get("s_introduce"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}
	}

	private void deleteSupplierFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		String s_num = getClientDate(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "delete from supplier where s_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(s_num);
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}
	}

	private void updateSupplierToDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "update supplier set  s_name = ? , s_pwd = ?, s_introduce = ? where s_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("s_name"));
		params.add(map.get("s_pwd"));
		params.add(map.get("s_introduce"));
		params.add(map.get("s_num"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}
	}

	private void querySupplierFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String s_num = (String) map.get("s_num");
		Supplier supplier = service.getSupplier(s_num);
		if (supplier != null) {
			jsonString = JsonTools.createJsonString("supplier", supplier);
		}
	}

	private void addAgentToDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "insert into agent ( a_num,  a_name,  a_pwd, a_introduce, a_level, a_rebate )"
				+ "values(?,?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("a_num"));
		params.add(map.get("a_name"));
		params.add(map.get("a_pwd"));
		params.add(map.get("a_introduce"));
		params.add(map.get("a_level"));
		params.add(map.get("a_rebate"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}
	}

	private void deleteAgentFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		String a_num = getClientDate(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "delete from agent where a_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(a_num);
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}
	}

	private void updateAgentToDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "update agent set  a_name = ? , a_pwd = ?, a_introduce = ?,a_level = ?,a_rebate = ?  where a_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("a_name"));
		params.add(map.get("a_pwd"));
		params.add(map.get("a_introduce"));
		params.add(map.get("a_level"));
		params.add(map.get("a_rebate"));
		params.add(map.get("a_num"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}
	}

	private void queryAgentFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String a_num = (String) map.get("a_num");
		Agent agent = service.getAgent(a_num);
		if (agent != null) {
			jsonString = JsonTools.createJsonString("agent", agent);
		}
	}

	private void orderBookFromDateBaseWith_usernum_b_num(
			HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String b_num = (String) map.get("b_num");
		String user_num = (String) map.get("user_num");
		OrderedBook orderedBook = service.getOrderedBookWith_usernum_b_num(
				b_num, user_num);
		if (orderedBook != null) {
			jsonString = JsonTools.createJsonString(
					"orderbookwith_user_num_b_num", orderedBook);
		} else {

		}

	}

	private void orderBookFromDateBaseWith_usernum(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String user_num = (String) map.get("user_num");
		List<OrderedBook> list = service
				.getlistOrderedBookWith_usernum(user_num);
		if (list != null) {
			jsonString = JsonTools.createJsonString("orderbookwith_user_num",
					list);
		}

	}

	private void cancelOrderBookFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "delete  from  orderedbook  where b_num = ? and  user_num = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("b_num"));
		params.add(map.get("user_num"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

	}

	private void queryBuyedGoodsFromDateBaseWith_usernum(
			HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String user_num = (String) map.get("user_num");
		List<BuyedGoods> list = service.getlistBuyedGoodsWith_usernum(user_num);
		if (list != null) {
			jsonString = JsonTools.createJsonString("buygoods_with_buyer_num",
					list);
		}
	}

	@SuppressWarnings("unchecked")
	private void uploadFile(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO 自动生成的方法存根
		String path = request.getSession().getServletContext()
				.getRealPath("/upload");
		FileItemFactory factory = new DiskFileItemFactory();// Create a factory
															// for disk-based
															// file items
		this.upload = new ServletFileUpload(factory);// Create a new file upload
														// handler
		this.upload.setSizeMax(this.MAXSize);// Set overall request size
												// constraint 4194304
		filedir = path;
		System.out.println("filedir=" + filedir);

		PrintWriter out = response.getWriter();
		try {
			List<FileItem> items = this.upload.parseRequest(request);
			if (items != null && !items.isEmpty()) {
				for (FileItem fileItem : items) {
					filename = fileItem.getName();
					String filepath = filedir + File.separator + filename;
					System.out.println("文件保存路径为:" + filepath);
					File file = new File(filepath);
					InputStream inputSteam = fileItem.getInputStream();
					BufferedInputStream fis = new BufferedInputStream(
							inputSteam);
					FileOutputStream fos = new FileOutputStream(file);
					int f;
					while ((f = fis.read()) != -1) {
						fos.write(f);
					}
					fos.flush();
					fos.close();
					fis.close();
					inputSteam.close();
					System.out.println("文件：" + filename + "上传成功!");
				}
			}
			System.out.println("上传文件成功!");
			out.write("上传文件成功!");
			addBookuploadPictureState = SUCCESS;
			updateBookuploadPictureState = SUCCESS;
		} catch (FileUploadException e) {
			e.printStackTrace();
			out.write("上传文件失败:" + e.getMessage());
			addBookuploadPictureState = FAIL;
			updateBookuploadPictureState = FAIL;
		}

	}

	private void payFreeFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "update borrowedbook set  return_time = ?, "
				+ "overtime = ? , remain_time = ?, state = ?  where b_num = ? and user_num = ?";
		List<Object> params = new ArrayList<Object>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		params.add(df.format(new Date()));
		params.add("0");
		params.add("0");
		params.add("已交清欠费并还书");
		params.add(map.get("b_num"));
		params.add(map.get("user_num"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

	}

	private void queryBorrowedBookFromDateBaseWith_overtime_usernum(
			HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String user_num = (String) map.get("user_num");
		List<BorrowedBook> list = service
				.getlistBorrowedBookWith_overtime_usernum(user_num);
		if (list != null) {
			jsonString = JsonTools.createJsonString(
					"borrowbook_with_overtime_usernum", list);
		}

	}

	private void updateLossBookFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "update lossbook set state= ?  where b_num = ? and  user_num = ?";
		List<Object> params = new ArrayList<Object>();
		params.add("已交费并取消挂失");
		params.add(map.get("b_num"));
		params.add(map.get("user_num"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (flag == true) {
			boolean flag2 = false;
			String sql2 = "delete  from  borrowedbook  where b_num = ? and  user_num = ?";
			List<Object> params2 = new ArrayList<Object>();
			params2.add(map.get("b_num"));
			params2.add(map.get("user_num"));
			try {
				flag2 = jdbcUtils.updateByPreparedStatement(sql2, params2);
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} finally {
				jdbcUtils.releaseConn();
			}
			if (flag2 == true) {
				jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
			}
		}

	}

	private void queryLossBookFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String user_num = (String) map.get("user_num");
		List<LossBook> list = service.getlistLossBookWithUser_num(user_num);
		if (list != null) {
			jsonString = JsonTools.createJsonString("lossbookwith_user_num",
					list);
		}

	}

	private void againBorrowBookFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "update borrowedbook set  b_name = ? , user_num = ?, borrow_time= ?,shouldreturn_time = ? , return_time=?, "
				+ "overtime = ? , remain_time = ?, state = ?  where b_num = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("b_name"));
		params.add(map.get("user_num"));
		params.add(map.get("borrow_time"));
		params.add(map.get("shouldReturn_time"));
		params.add(map.get("return_time"));
		params.add("0");
		params.add("15"); // 学生的借书时间为15天
		params.add(map.get("state"));
		params.add(map.get("b_num"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

	}

	private void addLossBookFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "insert into lossbook(b_num,b_name, user_num, state,b_img)"
				+ "values(?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("b_num"));
		params.add(map.get("b_name"));
		params.add(map.get("user_num"));
		params.add(map.get("state"));
		params.add(map.get("b_img"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (flag == true) {
			String sql2 = "update borrowedbook set  state = ?  where  b_num = ? and user_num= ?";
			List<Object> params2 = new ArrayList<Object>();
			params2.add("挂失中");
			params2.add(map.get("b_num"));
			params2.add(map.get("user_num"));
			boolean flag2 = false;
			try {
				flag2 = jdbcUtils.updateByPreparedStatement(sql2, params2);
				System.out.println("----------------->" + flag2);
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} finally {
				jdbcUtils.releaseConn();
			}
			if (flag2 == true) {
				jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
			}
		}
	}

	private void returnBorrowBookFromDateBasePre(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = getJavaBeanMap(request);
		// 查询borrowedbook表中原来的借书时间，计算overtime和remain_time
		BorrowedBook borrowedBook = service.getBorrowedBook((String) map
				.get("b_num"));
		String borrow_time = borrowedBook.getBorrow_time();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String overtime = null;
		String remain_time = null;
		long days = 0;
		Date d1 = null;
		try {
			d1 = df.parse(borrow_time);
			Date d2 = df.parse((String) map.get("return_time"));
			long diff = d2.getTime() - d1.getTime(); // 这样得到的差值是微妙级别的
			days = diff / (1000 * 60 * 60 * 24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (days > 15) {
			overtime = String.valueOf(days - 15);
			remain_time = String.valueOf(0);
		} else {
			overtime = String.valueOf(0);
			remain_time = String.valueOf(15 - days);
		}
		long day15 = 15 * (1000 * 60 * 60 * 24);
		long shouldReturnTimeMills = d1.getTime() + day15; // 借书日期+15=应还日期
		String shouldReturnTime = df.format(new Date(shouldReturnTimeMills));
		Map<String, Object> data = new HashMap<>();
		data.put("overtime", overtime);
		data.put("remain_time", remain_time);
		data.put("shouldReturnTime", shouldReturnTime);

		jsonString = JsonTools.createJsonString("borrowedInfo", data);

	}

	/**
	 * 还书:在图书借阅信息表中修改该书的相关信息，将book表中该书的数量加1.
	 * 
	 * @param request
	 * @throws IOException
	 */
	private void returnBorrowBookFromDateBase(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "update borrowedbook set  b_name = ? , user_num = ?,shouldreturn_time = ?, return_time = ? ,"
				+ "overtime = ? , remain_time = ?, state = ?  where  b_num = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("b_name"));
		params.add(map.get("user_num"));
		params.add("");
		params.add(map.get("return_time"));
		params.add("0");
		params.add("0");
		params.add(map.get("state"));
		params.add(map.get("b_num"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (flag == true) {
			// 将book表中该书的数量加1.
			boolean flag2 = false;
			String sql2 = "update book set count = ?  where b_num = ? ";
			Book book = service.getBook((String) map.get("b_num"));
			int count = Integer.parseInt(book.getCount());
			List<Object> params2 = new ArrayList<Object>();
			params2.add(String.valueOf(++count));
			params2.add(map.get("b_num"));

			try {
				flag2 = jdbcUtils.updateByPreparedStatement(sql2, params2);
				System.out.println("----------------->" + flag2);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				jdbcUtils.releaseConn();
			}
			if (flag2 == true) {
				jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
			}
		}
	}

	private void queryBorrowBookFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String b_num = (String) map.get("b_num");
		BorrowedBook borrowedBook = service.getBorrowedBook(b_num);
		if (borrowedBook != null) {
			jsonString = JsonTools.createJsonString("borrowedBook",
					borrowedBook);
		}

	}

	private void addOrderBookFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "insert into orderedbook(b_num,b_name, user_num, state,b_img)"
				+ "values(?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("b_num"));
		params.add(map.get("b_name"));
		params.add(map.get("user_num"));
		params.add(map.get("state"));
		params.add(map.get("b_img"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

	}

	private void addBuyGoodsFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "insert into buyedgoods (buyer_num,goods_num, goods_name, buy_time,"
				+ "goods_img ,goods_rebate , buy_price) values (?,?,?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("buyer_num"));
		params.add(map.get("goods_num"));
		params.add(map.get("goods_name"));
		params.add(map.get("buy_time"));
		params.add(map.get("goods_img"));
		params.add(map.get("goods_rebate"));
		double goodsRebate = Double.parseDouble((String) map
				.get("goods_rebate"));
		double goodsPrice = Double.parseDouble((String) map.get("goods_price"));
		String buyPrice = String.valueOf(goodsPrice * goodsRebate);

		params.add(buyPrice);
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 购买成功后，将仓库中上商品的数量减一
		if (flag == true) {
			boolean flag2 = false;
			String sql2 = "update goods set g_count = ?  where g_num = ? ";
			List<Object> params2 = new ArrayList<Object>();
			int count = Integer.parseInt((String) map.get("g_count"));
			params2.add(String.valueOf(--count));
			params2.add(map.get("goods_num"));

			try {
				flag2 = jdbcUtils.updateByPreparedStatement(sql2, params2);
				System.out.println("----------------->" + flag2);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				jdbcUtils.releaseConn();
			}
			if (flag2 == true) {
				jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);

			}
		}
	}

	private void queryGoodsFromDateBaseWithG_name_type(
			HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String g_name = (String) map.get("g_name");
		String g_type = (String) map.get("g_type");
		List<Goods> list = service.getlistGoodsWithG_name_type(g_name, g_type);
		if (list != null) {
			jsonString = JsonTools.createJsonString("q_goods_with_g_name_type",
					list);
		}

	}

	private void queryGoodsFromDateBaseWithG_type(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String g_type = (String) map.get("g_type");
		List<Goods> list = service.getlistGoodsWithG_type(g_type);
		if (list != null) {
			jsonString = JsonTools.createJsonString("list_goods_withG_type",
					list);
		}

	}

	private void queryBookFromDateBaseWithB_press(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String b_press = (String) map.get("b_press");
		List<Map<String, Object>> list = service
				.getlistBookWithB_press(b_press);
		if (list != null) {
			jsonString = JsonTools.createJsonString("list_book_withB_press",
					list);
		}

	}

	private void queryGoodsFromDateBaseWithG_name(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String g_name = (String) map.get("g_name");
		List<Goods> list = service.getlistGoodsWithG_name(g_name);
		if (list != null) {
			jsonString = JsonTools.createJsonString("list_goods_withG_name",
					list);
		}

	}

	private void deleteUserFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		String u_num = getClientDate(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "delete from user where u_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(u_num);
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}
	}

	/*
	 * CREATE TABLE `user`
	 */
	private void updateUserToDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "update user set  u_name = ? , u_pwd = ?, u_email = ?,u_phone = ? where u_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("u_name"));
		params.add(map.get("u_pwd"));
		params.add(map.get("u_email"));
		params.add(map.get("u_phone"));
		params.add(map.get("u_num"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

	}

	/**
	 */
	private void addUserToDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "insert into user( u_num,  u_name,  u_pwd, u_email, u_phone,u_rebate)"
				+ "values(?,?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("u_num"));
		params.add(map.get("u_name"));
		params.add(map.get("u_pwd"));
		params.add(map.get("u_email"));
		params.add(map.get("u_phone"));
		params.add(map.get("u_rebate"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

	}

	private void queryUserFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String u_num = (String) map.get("u_num");
		User user = service.getUser(u_num);
		if (user != null) {
			jsonString = JsonTools.createJsonString("user", user);
		}
	}

	private void queryStudentFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String s_num = (String) map.get("s_num");
		Student student = service.getStudent(s_num);
		if (student != null) {
			jsonString = JsonTools.createJsonString("student", student);
		}

	}

	private void queryGoodsFromDateBaseWithG_num(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String g_num = (String) map.get("g_num");
		Goods goods = service.getGoods(g_num);
		if (goods != null) {
			jsonString = JsonTools.createJsonString("goods", goods);
		}
	}

	private void stuLoginFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String u_num = (String) map.get("u_num");
		User user = service.getUser(u_num);
		if (user != null) {
			jsonString = JsonTools.createJsonString("user", user);
		}
	}

	private void adminLoginFromDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String username = (String) map.get("m_num");
		Admin admin = service.getAdmin(username);
		if (admin != null) {
			jsonString = JsonTools.createJsonString("admin", admin);
		}

	}

	private void deleteGoodsFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		String g_num = getClientDate(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "delete from goods where g_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(g_num);
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

	}

	private void updateGoodsToDateBaseItem(Map<String, Object> map,
			HttpServletRequest request, String imgName) {
		boolean flag = false;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "update goods set g_name = ? ,g_price = ?, g_introduce = ? , g_type = ? , "
				+ "g_count= ?, g_img= ? where g_num = ? ";
		List<Object> params = new ArrayList<Object>();

		params.add(map.get("g_name"));
		params.add(map.get("g_price"));
		params.add(map.get("introduction"));
		params.add(map.get("g_type"));
		params.add(map.get("g_count"));
		params.add(imgName);
		params.add(map.get("g_num"));

		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}
	}

	private void updateGoodsToDateBase(HttpServletRequest request) {
		Map<String, Object> map = getJavaBeanMap(request);
		String flagValue = (String) map.get(FLAG);
		System.out.println("flagValue:---------->" + flagValue);
		// 没有执行图片上传操作：也就是客户端没有修改图片
		if (flagValue.equals("false")) {
			updateGoodsToDateBaseItem(map, request, (String) map.get("g_img"));
		}
		// 执行了图片上传操作：也就是客户端修改了图片，由于图片上传是异步的：等待图片上传完成获取图片名称
		else if (flagValue.equals("true")) {
			boolean s = true;
			// 循环等待图片上传完成
			while (s) {
				if (updateBookuploadPictureState.equals(SUCCESS)) {
					// 将session保留的数据销毁
					updateBookuploadPictureState = "";
					updateGoodsToDateBaseItem(map, request, filename);
					System.out
							.println("插入到数据库的filename:---------->" + filename);
					s = false;
					// 图片未上传成功,向客户端发送图片上传不成功的消息
				} else if (updateBookuploadPictureState.equals(FAIL)) {
					// 将session保留的数据销毁
					updateBookuploadPictureState = "";
					jsonString = JsonTools.createJsonString(SUCCESS, FAIL);
					s = false;
				}
			}
		}
	}

	private void addGoodsToDateBase(HttpServletRequest request) {
		boolean s = true;
		// 循环等待图片上传完成
		while (s) {
			if (addBookuploadPictureState.equals(SUCCESS)) {
				// 将session保留的数据销毁
				updateBookuploadPictureState = "";
				boolean flag = false;
				Map<String, Object> map = getJavaBeanMap(request);
				JdbcUtils jdbcUtils = new JdbcUtils();
				jdbcUtils.getConnection();
				String sql = "insert into goods(g_num,g_name, g_price, g_introduce,g_count,g_type,g_img)"
						+ "values(?,?,?,?,?,?,?)";
				List<Object> params = new ArrayList<Object>();
				params.add(map.get("g_num"));
				params.add(map.get("g_name"));
				params.add(map.get("g_price"));
				params.add(map.get("introduction"));
				params.add(map.get("count"));
				params.add(map.get("g_type"));
				// 由于是上传图书图片成功之后才执行添加图书的操作，所以filename（图片的名称）是不会=""的,除非上传失败！
				params.add(filename);
				try {
					flag = jdbcUtils.updateByPreparedStatement(sql, params);
					System.out.println("----------------->" + flag);
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					jdbcUtils.releaseConn();
				}
				if (flag == true) {
					jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
				}
				s = false;
				// 图片未上传成功,向客户端发送图片上传不成功的消息
			} else if (addBookuploadPictureState.equals(FAIL)) {
				// 将session保留的数据销毁
				updateBookuploadPictureState = "";
				jsonString = JsonTools.createJsonString(SUCCESS, FAIL);
				s = false;
			}

		}

	}

	private void deleteStudentFromDateBase(HttpServletRequest request) {
		boolean flag = false;
		String s_num = getClientDate(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "delete from student where s_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(s_num);
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

	}

	private void updateStudentToDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "update student set s_name = ? ,s_age = ?, s_sex = ? , s_department = ? , "
				+ "s_pwd = ?, s_permitborrowtime = ?  where s_num = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("s_name"));
		params.add(map.get("s_age"));
		params.add(map.get("s_sex"));
		params.add(map.get("s_department"));
		params.add(map.get("s_pwd"));
		params.add(map.get("s_permitborrowtime"));
		params.add(map.get("s_num"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

	}

	private void addStudentToDateBase(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = getJavaBeanMap(request);
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "insert into  student(s_num, s_name, s_age,s_sex, s_department, s_pwd,s_permitborrowtime)"
				+ "values(?,?,?,?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(map.get("s_num"));
		params.add(map.get("s_name"));
		params.add(map.get("s_age"));
		params.add(map.get("s_sex"));
		params.add(map.get("s_department"));
		params.add(map.get("s_pwd"));
		params.add(map.get("s_permitborrowtime"));
		try {
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("----------------->" + flag);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (flag == true) {
			jsonString = JsonTools.createJsonString(SUCCESS, SUCCESS);
		}

	}

	public String getClientDate(HttpServletRequest request) {

		String jsonString = "";
		BufferedInputStream in = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len = 0;
		byte[] data = new byte[1024];
		try {
			in = new BufferedInputStream(request.getInputStream());
			while ((len = in.read(data)) != -1) {
				outputStream.write(data, 0, len);
			}
			jsonString = new String(outputStream.toByteArray());
			// String s=new String(jsonString.getBytes("iso8859-1"),"utf-8");
			// System.out.println("从客户端传到服务端的数据---------1>"+s);
			System.out.println("从客户端传到服务端的数据jsonString----->" + jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString;

	}

	public Map<String, Object> getJavaBeanMap(HttpServletRequest request) {
		// map的value的类型为Object，增强对数据库数据的包容性
		Map<String, Object> map = JsonTools2.getMaps(getClientDate(request));
		System.out.println("从客户端传到服务端的数据jsonString转换成map----->"
				+ map.toString());
		return map;
	}

	public void init() throws ServletException {
		service = new JsonService();
	}

}
