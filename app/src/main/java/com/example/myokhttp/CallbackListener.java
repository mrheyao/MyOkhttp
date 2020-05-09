package com.example.myokhttp;

import java.io.InputStream;

/**
 * 内部接口
 * @param <T>
 */
public interface CallbackListener<T> {

    void onSuccess(InputStream stream);

    void onError();
}
