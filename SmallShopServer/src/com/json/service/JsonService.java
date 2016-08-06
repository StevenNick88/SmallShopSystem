package com.json.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class JsonService {

	public JsonService() {
		// TODO Auto-generated constructor stub
	}

	// 查询User表的最后一条记录
	public User getLastUser() {
		User user = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from user order by u_id desc limit 1";
		try {
			user = jdbcUtils.findSimpleRefResult(sql, null, User.class);
			System.out.println("----------------------------->" + user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return user;
	}

	// 查询Log表的最后一条记录
	public Log getLog() {
		Log log = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from log order by id desc limit 1";
		try {
			log = jdbcUtils.findSimpleRefResult(sql, null, Log.class);
			System.out.println("----------------------------->" + log);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return log;
	}

	// 查询message表的最后一条记录
	public Message getMessage() {
		Message message = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from message order by id desc limit 1";
		try {
			message = jdbcUtils.findSimpleRefResult(sql, null, Message.class);
			System.out.println("----------------------------->" + message);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return message;
	}

	// 根据的s_num从数据库中获得该表的一条记录
	public Student getStudent(String s_num) {
		Student student = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from student where s_num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(s_num);
		try {
			student = jdbcUtils.findSimpleRefResult(sql, params, Student.class);
			System.out.println("----------------------------->" + student);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return student;
	}

	// 从数据库中获得该Log表的全部数据
	public List<Log> getlistLogs() {
		List<Log> list = new ArrayList<Log>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from log";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Log.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	// 从数据库中获得该message表的全部数据
	public List<Message> getlistMessage() {
		List<Message> list = new ArrayList<Message>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from message";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Message.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	// 从数据库中获得该Agent表的全部数据
	public List<Agent> getListAgent() {
		List<Agent> list = new ArrayList<Agent>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from agent";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Agent.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	// 从数据库中获得该Admin表的全部数据
	public List<Admin> getListAdmin() {
		List<Admin> list = new ArrayList<Admin>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from admin";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Admin.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	// 从数据库中获得该Supplier表的全部数据
	public List<Supplier> getListSupplier() {
		List<Supplier> list = new ArrayList<Supplier>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from supplier";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Supplier.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}
	// 根据的s_num从数据库中获得supplier表的一条记录
	public Supplier getSupplier(String s_num) {
		Supplier supplier = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from supplier where s_num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(s_num);
		try {
			supplier = jdbcUtils.findSimpleRefResult(sql, params,
					Supplier.class);
			System.out.println("----------------------------->" + supplier);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return supplier;
	}
	// 根据的a_num从数据库中获得agent表的一条记录
	public Agent getAgent(String a_num) {
		Agent agent = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from agent where a_num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(a_num);
		try {
			agent = jdbcUtils.findSimpleRefResult(sql, params, Agent.class);
			System.out.println("----------------------------->" + agent);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return agent;
	}

	public User getUser(String u_num) {
		User user = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from user where u_num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(u_num);
		try {
			user = jdbcUtils.findSimpleRefResult(sql, params, User.class);
			System.out.println("----------------------------->" + user);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return user;
	}

	public List<User> getListUser() {
		List<User> list = new ArrayList<User>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from user";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, User.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public Admin getAdmin(String m_num) {
		Admin admin = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from admin where m_num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(m_num);
		try {
			admin = jdbcUtils.findSimpleRefResult(sql, params, Admin.class);
			System.out.println("----------------------------->" + admin);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return admin;
	}

	public List<Admin> getlistAdmin() {
		List<Admin> list = new ArrayList<Admin>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from admin";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Admin.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public Goods getGoods(String g_num) {
		Goods goods = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from goods where g_num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(g_num);
		try {
			goods = jdbcUtils.findSimpleRefResult(sql, params, Goods.class);
			System.out.println("----------------------------->" + goods);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return goods;
	}

	public Book getBook(String b_num) {
		Book book = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from book where b_num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(b_num);
		try {
			book = jdbcUtils.findSimpleRefResult(sql, params, Book.class);
			System.out.println("----------------------------->" + book);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return book;
	}

	public List<Goods> getListGoods() {
		List<Goods> list = new ArrayList<Goods>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from goods";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, Goods.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Goods> getlistGoodsWithG_name(String g_name) {
		List<Goods> list = new ArrayList<Goods>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from goods where  g_name like ?";
		List<Object> params = new ArrayList<Object>();
		params.add("%" + g_name + "%");
		try {
			list = jdbcUtils.findMoreRefResult(sql, params, Goods.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Book> getlistBookWithB_name(String b_name) {
		List<Book> list = new ArrayList<Book>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from book where b_name= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(b_name);
		try {
			list = jdbcUtils.findMoreRefResult(sql, params, Book.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<BuyedGoods> getlistBuyedGoods() {
		List<BuyedGoods> list = new ArrayList<BuyedGoods>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from  buyedgoods";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, BuyedGoods.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<BuyedGoods> getlistBuyedGoodsWith_usernum(String user_num) {
		List<BuyedGoods> list = new ArrayList<BuyedGoods>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from buyedgoods where  buyer_num = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(user_num);
		try {
			list = jdbcUtils.findMoreRefResult(sql, params, BuyedGoods.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<OrderedBook> getlistOrderedBookWith_usernum(String user_num) {
		List<OrderedBook> list = new ArrayList<OrderedBook>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from orderedbook  where  user_num = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(user_num);
		try {
			list = jdbcUtils.findMoreRefResult(sql, params, OrderedBook.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public OrderedBook getOrderedBookWith_usernum_b_num(String b_num,
			String user_num) {
		OrderedBook orderedBook = new OrderedBook();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from orderedbook where b_num= ?  and  user_num = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(b_num);
		params.add(user_num);
		try {
			orderedBook = jdbcUtils.findSimpleRefResult(sql, params,
					OrderedBook.class);
			System.out.println("----------------------------->" + orderedBook);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return orderedBook;
	}

	public List<BorrowedBook> getlistBorrowedBookWith_overtime_usernum(
			String user_num) {
		List<BorrowedBook> list = new ArrayList<BorrowedBook>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from borrowedbook where overtime <> '0'  and  user_num = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(user_num);
		try {
			list = jdbcUtils.findMoreRefResult(sql, params, BorrowedBook.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Goods> getlistGoodsWithG_type(String g_type) {
		List<Goods> list = new ArrayList<Goods>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from book where g_type= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(g_type);
		try {
			list = jdbcUtils.findMoreRefResult(sql, params, Goods.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> getlistBookWithB_press(String b_press) {
		List<Book> list = new ArrayList<Book>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from book where b_press= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(b_press);
		try {
			// list = jdbcUtils.findMoreRefResult(sql, null,Book.class);
			listmap = jdbcUtils.findMoreResult(sql, params);
			System.out.println("----------------------------->" + list);
			System.out.println("----------------------------->" + listmap);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return listmap;
	}

	public List<Goods> getlistGoodsWithG_name_type(String g_name, String g_type) {
		List<Goods> list = new ArrayList<Goods>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from goods where g_name like ? and g_type= ?";
		List<Object> params = new ArrayList<Object>();
		params.add("%" + g_name + "%");
		params.add(g_type);
		try {
			list = jdbcUtils.findMoreRefResult(sql, params, Goods.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public BorrowedBook getBorrowedBook(String b_num) {
		BorrowedBook borrowedBook = null;
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from borrowedbook where b_num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(b_num);
		try {
			borrowedBook = jdbcUtils.findSimpleRefResult(sql, params,
					BorrowedBook.class);
			System.out.println("----------------------------->" + borrowedBook);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return borrowedBook;
	}

	public List<LossBook> getlistLossBookWithUser_num(String user_num) {
		List<LossBook> list = new ArrayList<LossBook>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from lossbook where user_num= ?";
		List<Object> params = new ArrayList<Object>();
		params.add(user_num);
		try {
			list = jdbcUtils.findMoreRefResult(sql, params, LossBook.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<BorrowedBook> getListBorrowedBook() {
		List<BorrowedBook> list = new ArrayList<BorrowedBook>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from borrowedbook";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, BorrowedBook.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> getListOrderBook() {
		List<OrderedBook> list = new ArrayList<OrderedBook>();
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from orderedbook";
		try {
			listmap = jdbcUtils.findMoreResult(sql, null);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return listmap;
	}

	public List<LossBook> getListLossBook() {
		List<LossBook> list = new ArrayList<LossBook>();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String sql = "select * from lossbook";
		try {
			list = jdbcUtils.findMoreRefResult(sql, null, LossBook.class);
			System.out.println("----------------------------->" + list);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<String> getListString() {
		List<String> list = new ArrayList<String>();
		list.add("beijing");
		list.add("shanghai");
		list.add("hunan");
		return list;
	}

	public List<Map<String, Object>> getListMaps() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("id", 1001);
		map1.put("name", "jack");
		map1.put("address", "beijing");
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("id", 1002);
		map2.put("name", "rose");
		map2.put("address", "shanghai");
		list.add(map1);
		list.add(map2);
		return list;
	}
}
