package com.puxtech.weipan.network;

import android.accounts.NetworkErrorException;
import android.os.Build;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mac on 15/11/4.
 */
public class HttpSender {

    private static final String ENCODE = "UTF-8";
    /**
     * 注册通讯访问方式，以及UI工具类处理地址时使用
     */
    private static final String HTTP = "http";
    /**
     * 注册通讯访问方式，以及UI工具类处理地址时使用
     */
    private static final String HTTPS = "https";
    /**
     * 设置链接池
     */
    private static final int CONN_PER_ROUTE_BEAN = 12;
    /**
     * 设置最大连接数
     */
    private static final int MAX_TOTAL_CONNECTIONS = 20;
    /**
     * 设置链接超时时间
     */
    private static final int CONNECTION_TIME_OUT = 10 * 1000;
    /**
     * 设置socket超时时间
     */
    private static final int SOCKET_TIME_OUT = 20 * 1000;
    /**
     * 设置sokect缓存最大字节数
     */
    private static final int SOCKET_BUFFER_SIZE = 8 * 1024;
    ;
    /**
     * http协议代理端口
     */
    private static final int HTTP_PROXY_PORT = 80;
    /**
     * https协议代理端口
     */
    private static final int HTTPS_PROXY_PORT = 443;

    public String requestJson(byte[] requestData, String urlStr, String property) throws Exception {


        URL url = new URL(urlStr + "?req=" + property);
        Logger.v("请求的URL:" + url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 单位是毫秒，设置超时时间为10秒
        conn.setConnectTimeout(CONNECTION_TIME_OUT);
        conn.setRequestMethod("POST");
        conn.setReadTimeout(SOCKET_TIME_OUT);
        conn.setRequestProperty("Content-Length",
                String.valueOf(requestData.length));
        conn.setRequestProperty("Content-Type",
                "application/json");
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");

        conn.setDoOutput(true);// 准备写出数据
        conn.setDoInput(true);
        conn.getOutputStream().write(requestData);
        conn.getOutputStream().flush();
        conn.getOutputStream().close();
        if (conn.getResponseCode() == 200) {
            InputStream is = conn.getInputStream();
            return new String(readStream(is));

        } else {
            throw new NetworkErrorException(
                    "requestJson请求服务器失败, ResponseCode:"
                            + conn.getResponseCode());
        }

    }

    //开户签约
    public String requestJson(byte[] requestData, String urlStr) throws Exception {


        URL url = new URL(urlStr);
        Logger.v("请求的URL:" + url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 单位是毫秒，设置超时时间为10秒
        conn.setConnectTimeout(CONNECTION_TIME_OUT);
        conn.setRequestMethod("POST");
        conn.setReadTimeout(SOCKET_TIME_OUT);
        conn.setRequestProperty("Content-Length",
                String.valueOf(requestData.length));
        conn.setRequestProperty("Content-Type",
                "application/json");
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");

        conn.setDoOutput(true);// 准备写出数据
        conn.setDoInput(true);
        conn.getOutputStream().write(requestData);
        conn.getOutputStream().flush();
        conn.getOutputStream().close();
        if (conn.getResponseCode() == 200) {
            InputStream is = conn.getInputStream();
            return new String(readStream(is));

        } else {
            throw new NetworkErrorException(
                    "requestJson请求服务器失败, ResponseCode:"
                            + conn.getResponseCode());
        }

    }
    /**
     * 将输入流转化为字符数组,,
     *
     * @param is
     * @return
     * @throws Exception
     */
    public byte[] readStream(InputStream is) throws Exception {
        ByteArrayOutputStream bout = null;
        bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            bout.write(buffer, 0, len);
        }
        is.close();
        return bout.toByteArray();
    }

    //暂时用于取验证码
    public byte[] requestImgBinaryApi9(byte[] requestData, String urlStr) {
        try {
            URL url = new URL(urlStr);
            // 利用HttpURLConnection对象,我们可以从网络中获取网页数据.
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 单位是毫秒，设置超时时间为10秒
            conn.setConnectTimeout(CONNECTION_TIME_OUT);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(SOCKET_TIME_OUT);

            conn.setRequestProperty("Content-Length",
                    String.valueOf(requestData.length));

            conn.setRequestProperty("Content-Type", "binary");
            // httppost.setHeader("Content-Type", "binary");

            // HttpURLConnection是通过HTTP协议请求path路径的，所以需要设置请求方式,可以不设置，因为默认为GET
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setDoOutput(true);// 准备写出数据
            conn.setDoInput(true);
            conn.getOutputStream().write(requestData);
            conn.getOutputStream().flush();
            conn.getOutputStream().close();
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                byte[] data1 = readStream(is);
                return data1;

            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //行情
    public byte[] requestBinary(byte[] requestData, String urlStr)
            throws Exception {

        return requestBinaryApi9(requestData, urlStr);

    }

    // 2.3及以上使用，因为2.2及以下使用HttpURLConnection会出现exception
    private byte[] requestBinaryApi9(byte[] requestData, String urlStr)
            throws Exception {
        URL url = new URL(urlStr);
        // 利用HttpURLConnection对象,我们可以从网络中获取网页数据.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 单位是毫秒，设置超时时间为10秒
        conn.setConnectTimeout(CONNECTION_TIME_OUT);
        conn.setReadTimeout(SOCKET_TIME_OUT);
        conn.setRequestProperty("Content-Length",
                String.valueOf(requestData.length));
        conn.setRequestProperty("Content-Type", "binary");
        // HttpURLConnection是通过HTTP协议请求path路径的，所以需要设置请求方式,可以不设置，因为默认为GET
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setDoOutput(true);// 准备写出数据
        conn.setDoInput(true);
        conn.getOutputStream().write(requestData);
        conn.getOutputStream().flush();
        conn.getOutputStream().close();
        if (conn.getResponseCode() == 200) {
            InputStream is = conn.getInputStream();
            byte[] responseData = readStream(is); // 将输入流转化为字符数组
            is.close();
            return responseData;
        } else {
            throw new NetworkErrorException(
                    "requestBinaryApi9请求服务器失败, ResponseCode:"
                            + conn.getResponseCode());
        }
    }

    //微信
    public String getRequest(String mUrl) throws Exception {

        URL url = new URL(mUrl);
        HttpURLConnection conn;
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        if (conn.getResponseCode() == 200) {
            InputStream is = conn.getInputStream();
            return new String(readStream(is));

        } else {
            throw new NetworkErrorException(
                    "weixin请求服务器失败,HttpURLConnection ResponseCode:"
                            + conn.getResponseCode());
        }
    }
    public  InputStream getStreamFromURL(String imageURL) {
        InputStream in=null;
        try {
            URL url=new URL(imageURL);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            in=connection.getInputStream();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return in;

    }
}
