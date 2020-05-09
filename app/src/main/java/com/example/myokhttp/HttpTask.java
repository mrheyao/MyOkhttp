package com.example.myokhttp;


import com.alibaba.fastjson.JSON;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 请求线程
 */
public class HttpTask<T> implements Runnable , Delayed {

    private IHttpRequest mIHttpRequest;
    private int num;
    private long delayTime;




    public HttpTask(IHttpRequest request, CallbackListener listener, String url,
                    T requestData) {
        mIHttpRequest = request;
        mIHttpRequest.setUrl(url);
        mIHttpRequest.setListener(listener);
        if (requestData != null) {
            String jsonStr = JSON.toJSONString(requestData);
            try {
                mIHttpRequest.setData(jsonStr.getBytes("utf-8"));
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void run() {
        try {
            mIHttpRequest.execute();
        } catch (Exception e) {

            ThreadManager.getInstance().addFailTask(this);
        }
    }


    @Override
    public long getDelay(TimeUnit unit) {
//        return 0;
        return unit.convert(getDelayTime()-System.currentTimeMillis(),
                TimeUnit.MILLISECONDS);
    }

    public IHttpRequest getmIHttpRequest() {
        return mIHttpRequest;
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime+System.currentTimeMillis();
    }
}
