package com.gxu.smallshop.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工具类
 *
 * @author lpa
 */
public class HttpUtils {

    public HttpUtils() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 根据url从服务端获取json数据的方法
     *client连接
     * @param url_path
     * @return
     */
    public static String getJsonString(String url_path) {
        String jsonString = "";
        //创建一个HTTP客户端
        HttpClient httpClient = new DefaultHttpClient();
        //基于url地址字符串构建HttpPost对象
        HttpPost httpPost = new HttpPost(url_path);
        try {
            //
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //http请求响应成功
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                //获取JSON格式数据
                jsonString = EntityUtils.toString(httpResponse.getEntity(),
                        "utf-8");
            }
        } catch (ClientProtocolException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally {
            //关闭连接，释放资源
            httpClient.getConnectionManager().shutdown();
        }

        return jsonString;
    }

    /**
     * 根据http接口获得json的数据并转换成字符串
     *connection连接
     * @param url_path
     * @return
     */
    public static String getJsonContent(String url_path) {
        HttpURLConnection connection;
        try {
            //建立连接
            URL url = new URL(url_path);//基于url地址字符串构建url对象
            connection = (HttpURLConnection) url.openConnection();//调用url的openConnection方法
            //设置连接属性
            connection.setConnectTimeout(3000);//设置连接主机超时
            connection.setRequestMethod("POST");//设置url请求方法
            connection.setDoInput(true);//设置输入流采用字节流
            int code = connection.getResponseCode();
            if (code == 200) {
                return changeInputStream(connection.getInputStream());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }

    /**
     * 将json格式的数据转换成字符串
     *
     * @param inputStream
     * @return
     */
    private static String changeInputStream(InputStream inputStream) {
        // TODO Auto-generated method stub
        String jsonString = "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] data = new byte[1024];
        try {
            while ((len = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            jsonString = new String(outputStream.toByteArray());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonString;
    }
    //向服务器发送数据
    public static Boolean sendJavaBeanToServer(String url_path, String jsonString) {
        Boolean flag = false;
        //创建一个http客户端
        HttpClient httpClient = new DefaultHttpClient();
        //基于url地址字符串构建HttpPost对象
        HttpPost post = new HttpPost(url_path);
        try {
            //为post方法对象设置请求参数
            StringEntity entity = new StringEntity(jsonString, "GBK");
            post.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String str = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");

                JSONObject jsonObject = new JSONObject(str);
                Object json_value = jsonObject.get(CommonUrl.SUCCESS);
                if (json_value.toString().equals(CommonUrl.SUCCESS)) {
                    flag = true;
                }

//                Map<String, Object> map = JsonTools.getMaps(str);
//                if (map.get(CommonUrl.SUCCESS).toString().equals(CommonUrl.SUCCESS)) {
//                    flag = true;
//                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            httpClient.getConnectionManager().shutdown();
        }

        return flag;
    }
    //向服务器发送数据，并获取返回的数据
    public static String sendInfoToServerGetJsonData(String url_path, String jsonString) {
        String str=null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url_path);
        try {

            StringEntity entity = new StringEntity(jsonString, "GBK");
            post.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                str = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            httpClient.getConnectionManager().shutdown();
        }

        return str;
    }
    public static Boolean sendJavaBeanToServer(String url_path, Map<String, String> params, String encode) {
        Boolean flag = false;
        StringBuilder url = new StringBuilder(url_path);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            url.append("?");
            url.append(entry.getKey()).append("=");
            url.append(URLEncoder.encode(entry.getValue()));
            url.append("&");

        }
        url.deleteCharAt(url.length() - 1);
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url.toString()).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }


    public static String postRequest(String url, Map<String, String> rawParams)
            throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        // 创建HttpPost对象。
        HttpPost post = new HttpPost(url);
        // 如果传递参数个数比较多的话可以对传递的参数进行封装
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (String key : rawParams.keySet()) {
            // 封装请求参数
            params.add(new BasicNameValuePair(key, rawParams.get(key)));
        }
        // 设置请求参数
        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        // 发送POST请求
        HttpResponse httpResponse = httpClient.execute(post);
        // 如果服务器成功地返回响应
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            // 获取服务器响应字符串
            String result = EntityUtils.toString(httpResponse.getEntity());
            return result;
        }
        return null;
    }
}
