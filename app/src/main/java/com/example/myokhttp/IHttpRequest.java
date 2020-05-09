package com.example.myokhttp;

/**
 * 请求对象的 顶层接口
 */
public interface IHttpRequest {
//设置请求路径
    void setUrl(String url);
    //
    void setData(byte[] data);

    //设置请求毁掉接口
    void setListener(CallbackListener listener);

    void execute();
}
