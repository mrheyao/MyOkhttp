package com.example.myokhttp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 内部接口实现类，当请求成功后，得到inputstream交给这个类处理
 * inputstream中得到的数据拿出来，转为用户的数据类型
 */
public class JsonCallbackListener<T> implements CallbackListener {

    private Class<T> response;
    private IJsonListener iJsonListener;

    private Handler mhandler = new Handler(Looper.getMainLooper()) {

    };

    public JsonCallbackListener(Class<T> response, IJsonListener iJsonListener) {
        this.iJsonListener = iJsonListener;
        this.response = response;
    }

    @Override
    public void onSuccess(InputStream stream) {
        String json = getContent(stream);
        Log.d("testtt", "onsuccesss:" + json);
        final T t = JSONObject.parseObject(json, response);
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                iJsonListener.onSuccess(t);

            }
        });
    }

    @Override
    public void onError() {
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                iJsonListener.onError();
            }
        });
    }

    private String getContent(InputStream stream) {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(stream)
        );
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {

        } finally {
            try {
                stream.close();
            } catch (Exception e) {

            }
        }
        return sb.toString().replace("/n", "");
    }
}
