package com.example.myokhttp;

public interface IJsonListener<T> {

    void onSuccess(T t);

    void onError();
}
