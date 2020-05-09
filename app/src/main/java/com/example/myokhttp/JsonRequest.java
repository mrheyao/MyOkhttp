package com.example.myokhttp;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 请求对象的 实现类
 */
public class JsonRequest implements IHttpRequest {

    private String mUrl;
    private byte[] mData;
    private CallbackListener mcallbackListener;
    private HttpURLConnection mHttpurlconnection;


    @Override
    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public void setData(byte[] data) {
        mData = data;
    }

    @Override
    public void setListener(CallbackListener listener) {
        mcallbackListener = listener;
    }

    @Override
    public void execute() {
        URL url = null;
        try {
            url = new URL(mUrl);
            mHttpurlconnection = (HttpURLConnection) url.openConnection();
            mHttpurlconnection.setConnectTimeout(10000);
            mHttpurlconnection.setUseCaches(false);
            mHttpurlconnection.setInstanceFollowRedirects(true);
            mHttpurlconnection.setReadTimeout(5000);
            mHttpurlconnection.setDoInput(true);
            mHttpurlconnection.setDoOutput(true);
            mHttpurlconnection.setRequestMethod("POST");
            mHttpurlconnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            mHttpurlconnection.connect();
            OutputStream out = mHttpurlconnection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);
            bos.write(mData);
            bos.flush();
            out.close();
            bos.close();
            if (mHttpurlconnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = mHttpurlconnection.getInputStream();
                mcallbackListener.onSuccess(in);
            } else {
                throw new RuntimeException("请求失败");
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("请求失败");
        } catch (IOException e) {
            throw new RuntimeException("请求失败io");
        } catch (Exception e) {
            throw new RuntimeException("请求失败");
        } finally {

        }
    }

    public CallbackListener getMcallbackListener(){
        return  mcallbackListener;
    }
}
