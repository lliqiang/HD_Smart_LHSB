package com.hengda.linhai.m.http;






import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 作者：Tailyou （祝文飞）
 * 时间：2016/6/15 10:07
 * 邮箱：tailyou@163.com
 * 描述：
 */
public class RequestSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException || e instanceof
                ConnectException) {
            failed(new RequestException("网络不可用"));
        } else {
            failed(e);
        }
    }

    @Override
    public void onNext(T t) {
        succeed(t);
    }

    public void succeed(T t) {
    }

    public void failed(Throwable e) {
    }

}
