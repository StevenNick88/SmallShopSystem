package com.gxu.smallshop.utils;

import android.text.SpannableString;

import com.gxu.smallshop.db.domain.Admin;
import com.gxu.smallshop.db.domain.Agent;
import com.gxu.smallshop.db.domain.Book;
import com.gxu.smallshop.db.domain.BorrowedBook;
import com.gxu.smallshop.db.domain.BuyedGoods;
import com.gxu.smallshop.db.domain.Goods;
import com.gxu.smallshop.db.domain.Log;
import com.gxu.smallshop.db.domain.LossBook;
import com.gxu.smallshop.db.domain.Message;
import com.gxu.smallshop.db.domain.OrderedBook;
import com.gxu.smallshop.db.domain.Student;
import com.gxu.smallshop.db.domain.Supplier;
import com.gxu.smallshop.db.domain.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 根据业务的需求数据类型
 * 完成对json数据的解析
 *
 * @author jack
 */
public class JsonTools {

    public JsonTools() {
        // TODO Auto-generated constructor stub
    }
    //user
    public static User getUser(String key, String jsonString, String desValuesStr) {
        User user = new User();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject userObject = jsonObject.getJSONObject(key);
//            user.setU_name(new String( AESUtils.decrypt(Base64Utils.decode(userObject.getString("u_name")), desValuesStr)));
//            user.setU_pwd(new String(DESUtils.decrypt(Base64Utils.decode(userObject.getString("u_pwd")), desValuesStr)));
            user.setU_id(userObject.getInt("u_id"));
            user.setU_num(userObject.getString("u_num"));


            user.setU_name(new String(DESUtils.decrypt(Base64Utils.decode(userObject.getString("u_name")), desValuesStr)));
            String s=new String(DESUtils.decrypt(Base64Utils.decode(userObject.getString("u_pwd")), desValuesStr));
            user.setU_pwd(new String(DESUtils.decrypt(Base64Utils.decode(userObject.getString("u_pwd")), desValuesStr)));
            user.setU_email(new String(DESUtils.decrypt(Base64Utils.decode(userObject.getString("u_email")), desValuesStr)));
            user.setU_phone(new String(DESUtils.decrypt(Base64Utils.decode(userObject.getString("u_phone")), desValuesStr)));
            user.setU_rebate(new String(DESUtils.decrypt(Base64Utils.decode(userObject.getString("u_rebate")), desValuesStr)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static User getUser(String key, String jsonString) {
        User user = new User();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject userObject = jsonObject.getJSONObject(key);
            user.setU_id(userObject.getInt("u_id"));
            user.setU_num(userObject.getString("u_num"));
            user.setU_name(userObject.getString("u_name"));
            user.setU_pwd(userObject.getString("u_pwd"));
            user.setU_email(userObject.getString("u_email"));
            user.setU_phone(userObject.getString("u_phone"));
            user.setU_rebate(userObject.getString("u_rebate"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return user;
    }


    public static List<User> getListUser(String key, String jsonString) {
        List<User> list = new ArrayList<User>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                User user = new User();
                user.setU_id(jsonObject2.getInt("u_id"));
                user.setU_num(jsonObject2.getString("u_num"));
                user.setU_name(jsonObject2.getString("u_name"));
                user.setU_pwd(jsonObject2.getString("u_pwd"));
                user.setU_email(jsonObject2.getString("u_email"));
                user.setU_phone(jsonObject2.getString("u_phone"));
                list.add(user);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }
    //supplier
    public static List<Supplier> getListSupplier(String key, String jsonString) {
        List<Supplier> list = new ArrayList<Supplier>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Supplier supplier = new Supplier();
                supplier.setS_num(jsonObject2.getString("s_num"));
                supplier.setS_name(jsonObject2.getString("s_name"));
                supplier.setS_pwd(jsonObject2.getString("s_pwd"));
                supplier.setS_introduce(jsonObject2.getString("s_introduce"));
                list.add(supplier);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }


    public static Supplier getSupplier(String key, String jsonString, String desValuesStr) {
        Supplier supplier = new Supplier();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject adminObject = jsonObject.getJSONObject(key);
            supplier.setS_num((adminObject.getString("s_num")));
            supplier.setS_name(new String(DESUtils.decrypt(Base64Utils.decode(adminObject.getString("s_name")), desValuesStr)));
            supplier.setS_pwd(new String(DESUtils.decrypt(Base64Utils.decode(adminObject.getString("s_pwd")), desValuesStr)));
            supplier.setS_introduce(new String(DESUtils.decrypt(Base64Utils.decode(adminObject.getString("s_introduce")), desValuesStr)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplier;
    }


    public static Supplier getSupplier(String key, String jsonString) {
        Supplier supplier = new Supplier();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject adminObject = jsonObject.getJSONObject(key);
            supplier.setS_num(adminObject.getString("s_num"));
            supplier.setS_name(adminObject.getString("s_name"));
            supplier.setS_pwd(adminObject.getString("s_pwd"));
            supplier.setS_introduce(adminObject.getString("s_introduce"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return supplier;
    }

    /**
     */
    //agent
    public static Agent getAgent(String key, String jsonString, String desValuesStr) {
        Agent agent = new Agent();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject adminObject = jsonObject.getJSONObject(key);
            agent.setA_num(adminObject.getString("a_num"));
            agent.setA_name(new String(DESUtils.decrypt(Base64Utils.decode(adminObject.getString("a_name")), desValuesStr)));
            agent.setA_pwd(new String(DESUtils.decrypt(Base64Utils.decode(adminObject.getString("a_pwd")), desValuesStr)));
            agent.setA_introduce(new String(DESUtils.decrypt(Base64Utils.decode(adminObject.getString("a_introduce")), desValuesStr)));
            agent.setA_level(new String(DESUtils.decrypt(Base64Utils.decode(adminObject.getString("a_level")), desValuesStr)));
            agent.setA_rebate(new String(DESUtils.decrypt(Base64Utils.decode(adminObject.getString("a_rebate")), desValuesStr)));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return agent;
    }

    public static Agent getAgent(String key, String jsonString) {
        Agent agent = new Agent();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject adminObject = jsonObject.getJSONObject(key);
            agent.setA_num(adminObject.getString("a_num"));
            agent.setA_name(adminObject.getString("a_name"));
            agent.setA_pwd(adminObject.getString("a_pwd"));
            agent.setA_introduce(adminObject.getString("a_introduce"));
            agent.setA_level(adminObject.getString("a_level"));
            agent.setA_rebate(adminObject.getString("a_rebate"));

        } catch (Exception e) {
            // TODO: handle exception
        }
        return agent;
    }

    public static List<Agent> getAgents(String key, String jsonString) {
        List<Agent> list = new ArrayList<Agent>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Agent agent = new Agent();
                agent.setA_num(jsonObject2.getString("a_num"));
                agent.setA_name(jsonObject2.getString("a_name"));
                agent.setA_pwd(jsonObject2.getString("a_pwd"));
                agent.setA_introduce(jsonObject2.getString("a_introduce"));
                list.add(agent);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }
    //admin
    public static Admin getAdmin(String key, String jsonString, String desValuesStr) {
        Admin admin = new Admin();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject adminObject = jsonObject.getJSONObject(key);
            admin.setM_num(adminObject.getString("m_num"));
            //解密，解码时先对其进行base64解密再进DES解密，这样就能保证接受数据的正确性并且不会缺失。
            admin.setM_name(new String(DESUtils.decrypt(Base64Utils.decode(adminObject.getString("m_name")), desValuesStr)));
            admin.setM_pwd(new String(DESUtils.decrypt(Base64Utils.decode(adminObject.getString("m_pwd")), desValuesStr)));
            admin.setM_remark(new String(DESUtils.decrypt(Base64Utils.decode(adminObject.getString("m_remark")), desValuesStr)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
    }

    public static Admin getAdmin(String key, String jsonString) {
        Admin admin = new Admin();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject adminObject = jsonObject.getJSONObject(key);
            admin.setM_num(adminObject.getString("m_num"));
            admin.setM_name(adminObject.getString("m_name"));
            admin.setM_pwd(adminObject.getString("m_pwd"));
            admin.setM_remark(adminObject.getString("m_remark"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return admin;
    }

    public static List<Admin> getAdmins(String key, String jsonString) {
        List<Admin> list = new ArrayList<Admin>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Admin admin = new Admin();
                admin.setM_num(jsonObject2.getString("m_num"));
                admin.setM_pwd(jsonObject2.getString("m_pwd"));
                admin.setM_remark(jsonObject2.getString("m_remark"));
                list.add(admin);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    //goods
    public static Goods getGoods(String key, String jsonString) {
        Goods goods = new Goods();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject bookObject = jsonObject.getJSONObject(key);
            goods.setId(bookObject.getInt("id"));
            goods.setG_count(bookObject.getString("g_count"));
            goods.setG_num(bookObject.getString("g_num"));
            goods.setG_name(bookObject.getString("g_name"));
            goods.setG_img(bookObject.getString("g_img"));
            goods.setG_price(bookObject.getString("g_price"));
            goods.setG_introduce(bookObject.getString("g_introduce"));
            goods.setG_type(bookObject.getString("g_type"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return goods;
    }

    public static List<Goods> getGoodsList(String key, String jsonString) {
        List<Goods> list = new ArrayList<Goods>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Goods goods = new Goods();
                goods.setId(jsonObject2.getInt("id"));
                goods.setG_num(jsonObject2.getString("g_num"));
                goods.setG_name(jsonObject2.getString("g_name"));
                goods.setG_img(jsonObject2.getString("g_img"));
                goods.setG_price(jsonObject2.getString("g_price"));
                goods.setG_introduce(jsonObject2.getString("g_introduce"));
                goods.setG_type(jsonObject2.getString("g_type"));
                goods.setG_count(jsonObject2.getString("g_count"));
                list.add(goods);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    public static List<BuyedGoods> getBuyedGoods(String key, String jsonString) {
        List<BuyedGoods> list = new ArrayList<BuyedGoods>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                BuyedGoods buyedGoods = new BuyedGoods();
                buyedGoods.setId(jsonObject2.getInt("id"));
                buyedGoods.setBuyer_num(jsonObject2.getString("buyer_num"));
                buyedGoods.setGoods_num(jsonObject2.getString("goods_num"));
                buyedGoods.setGoods_name(jsonObject2.getString("goods_name"));
                buyedGoods.setBuy_time(jsonObject2.getString("buy_time"));
                buyedGoods.setGoods_img(jsonObject2.getString("goods_img"));
                buyedGoods.setGoods_rebate(jsonObject2.getString("goods_rebate"));
                buyedGoods.setBuy_price(jsonObject2.getString("buy_price"));
                list.add(buyedGoods);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    public static Log getLog(String key, String jsonString) {
        Log log = new Log();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject bookObject = jsonObject.getJSONObject(key);
            log.setId(bookObject.getInt("id"));
            log.setLog_num(bookObject.getString("log_num"));
            log.setLog_content(bookObject.getString("log_content"));
            log.setUser_num(bookObject.getString("user_num"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return log;
    }

    public static List<Log> getLogList(String key, String jsonString) {
        List<Log> list = new ArrayList<Log>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Log log = new Log();
                log.setId(jsonObject2.getInt("id"));
                log.setLog_num(jsonObject2.getString("log_num"));
                log.setLog_content(jsonObject2.getString("log_content"));
                log.setUser_num(jsonObject2.getString("user_num"));
                list.add(log);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    public static Message getMessage(String key, String jsonString) {
        Message message = new Message();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject bookObject = jsonObject.getJSONObject(key);
            message.setId(bookObject.getInt("id"));
            message.setMs_num(bookObject.getString("ms_num"));
            message.setMs_question(bookObject.getString("ms_question"));
            message.setMs_answer(bookObject.getString("ms_answer"));
            message.setBuyer_num(bookObject.getString("buyer_num"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return message;
    }

    public static List<Message> getMessageList(String key, String jsonString) {
        List<Message> list = new ArrayList<Message>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Message message = new Message();
                message.setId(jsonObject2.getInt("id"));
                message.setMs_num(jsonObject2.getString("ms_num"));
                message.setMs_question(jsonObject2.getString("ms_question"));
                message.setMs_answer(jsonObject2.getString("ms_answer"));
                message.setBuyer_num(jsonObject2.getString("buyer_num"));
                list.add(message);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }


    public static BorrowedBook getBorrowedBook(String key, String jsonString) {
        BorrowedBook borrowedBook = new BorrowedBook();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject bookObject = jsonObject.getJSONObject(key);
            borrowedBook.setId(bookObject.getInt("id"));
            borrowedBook.setB_num(bookObject.getString("b_num"));
            borrowedBook.setB_name(bookObject.getString("b_name"));
            borrowedBook.setUser_num(bookObject.getString("user_num"));
            borrowedBook.setBorrow_time(bookObject.getString("borrow_time"));
            borrowedBook.setReturn_time(bookObject.getString("return_time"));
            borrowedBook.setOvertime(bookObject.getString("overtime"));
            borrowedBook.setRemain_time(bookObject.getString("remain_time"));
            borrowedBook.setState(bookObject.getString("state"));
            borrowedBook.setShouldreturn_time(bookObject.getString("shouldreturn_time"));
            borrowedBook.setB_img(bookObject.getString("b_img"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return borrowedBook;
    }

    public static Student getStudent(String key, String jsonString) {
        Student student = new Student();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject userObject = jsonObject.getJSONObject(key);
            student.setS_id(userObject.getInt("s_id"));
            student.setS_num(userObject.getString("s_num"));
            student.setS_name(userObject.getString("s_name"));
            student.setS_age(userObject.getString("s_age"));
            student.setS_sex(userObject.getString("s_sex"));
            student.setS_department(userObject.getString("s_department"));
            student.setS_pwd(userObject.getString("s_pwd"));
            student.setS_permitborrowtime(userObject.getString("s_permitborrowtime"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return student;
    }
    public static List<OrderedBook> getOrderBooks(String key, String jsonString) {
        List<OrderedBook> list = new ArrayList<OrderedBook>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                OrderedBook orderedBook = new OrderedBook();
                orderedBook.setId(jsonObject2.getInt("id"));
                orderedBook.setB_num(jsonObject2.getString("b_num"));
                orderedBook.setB_name(jsonObject2.getString("b_name"));
                orderedBook.setUser_num(jsonObject2.getString("user_num"));
                orderedBook.setState(jsonObject2.getString("state"));
                orderedBook.setB_img(jsonObject2.getString("b_img"));
                list.add(orderedBook);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return list;
    }

    public static Book getBook(String key, String jsonString) {
        Book book = new Book();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject bookObject = jsonObject.getJSONObject(key);
            book.setId(bookObject.getInt("id"));
            book.setB_num(bookObject.getString("b_num"));
            book.setB_name(bookObject.getString("b_name"));
            book.setB_author(bookObject.getString("b_author"));
            book.setB_press(bookObject.getString("b_press"));
            book.setB_buytime(bookObject.getString("b_buytime"));
            book.setIntroduction(bookObject.getString("introduction"));
            book.setCount(bookObject.getString("count"));
            book.setB_img(bookObject.getString("b_img"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return book;
    }

    public static OrderedBook getOrderBook(String key, String jsonString) {
        OrderedBook orderedBook = new OrderedBook();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject bookObject = jsonObject.getJSONObject(key);
            orderedBook.setId(bookObject.getInt("id"));
            orderedBook.setB_num(bookObject.getString("b_num"));
            orderedBook.setB_name(bookObject.getString("b_name"));
            orderedBook.setUser_num(bookObject.getString("user_num"));
            orderedBook.setState(bookObject.getString("state"));
            orderedBook.setB_img(bookObject.getString("b_img"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return orderedBook;
    }


    public static List<LossBook> getLossBooks(String key, String jsonString) {
        List<LossBook> list = new ArrayList<LossBook>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                LossBook lossBook = new LossBook();
                lossBook.setId(jsonObject2.getInt("id"));
                lossBook.setB_num(jsonObject2.getString("b_num"));
                lossBook.setB_name(jsonObject2.getString("b_name"));
                lossBook.setUser_num(jsonObject2.getString("user_num"));
                lossBook.setState(jsonObject2.getString("state"));
                lossBook.setB_img(jsonObject2.getString("b_img"));
                list.add(lossBook);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }


    public static List<Book> getBooks(String key, String jsonString) {
        List<Book> list = new ArrayList<Book>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // 返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Book book = new Book();
                book.setId(jsonObject2.getInt("id"));
                book.setB_num(jsonObject2.getString("b_num"));
                book.setB_name(jsonObject2.getString("b_name"));
                book.setB_author(jsonObject2.getString("b_author"));
                book.setB_press(jsonObject2.getString("b_press"));
                book.setB_buytime(jsonObject2.getString("b_buytime"));
                book.setIntroduction(jsonObject2.getString("introduction"));
                book.setCount(jsonObject2.getString("count"));
                book.setB_img(jsonObject2.getString("b_img"));
                list.add(book);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    public static List<String> getList(String key, String jsonString) {
        List<String> list = new ArrayList<String>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                String msg = jsonArray.getString(i);
                list.add(msg);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }


    //将json数据转换成map
    public static Map<String, Object> getMaps(String key, String jsonString) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonObject2 = jsonObject.getJSONObject(key);
            Iterator<String> iterator = jsonObject2.keys();
            while (iterator.hasNext()) {
                String json_key = iterator.next();
                Object json_value = jsonObject2.get(json_key);
                if (json_value == null) {
                    json_value = "";
                }
                map.put(json_key, json_value);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return map;
    }

    public static List<Map<String, Object>> listKeyMaps(String key,
                                                        String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Map<String, Object> map = new HashMap<String, Object>();
                Iterator<String> iterator = jsonObject2.keys();
                while (iterator.hasNext()) {
                    String json_key = iterator.next();
                    Object json_value = jsonObject2.get(json_key);
                    if (json_value == null) {
                        json_value = "";
                    }
                    map.put(json_key, json_value);
                }
                list.add(map);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }
}
