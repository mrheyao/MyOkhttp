package com.example.myokhttp;

/**
 * 提供接口给应用层的api
 */
public class MyOkhttp {
    //请求路径   参数   回调接口   接收结果的参数类型
    public static <T, M> void sendRequest(String url,
                                          T requestData,
                                          Class<M> response,
                                          IJsonListener listener) {
        IHttpRequest iHttpRequest = new JsonRequest();

        CallbackListener mCallbackListener = new JsonCallbackListener(response, listener);
        //将请求对象封装成

        HttpTask httpTask = new HttpTask(iHttpRequest, mCallbackListener, url,
                requestData);

        ThreadManager.getInstance().addTask(httpTask);

    }
}
