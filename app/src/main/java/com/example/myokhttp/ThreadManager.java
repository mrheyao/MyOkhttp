package com.example.myokhttp;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程管理
 */
public class ThreadManager {

    private static ThreadManager mThreadManager = new ThreadManager();

    private LinkedBlockingQueue<Runnable> mQuene =
            new LinkedBlockingQueue<>();

    private DelayQueue<Delayed> mFailQuene = new DelayQueue<>();


    private ThreadPoolExecutor mThreadPoolExecutor;

    private ThreadManager() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 10, 15,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                addTask(r);
            }
        });

        mThreadPoolExecutor.execute(runnable);
        mThreadPoolExecutor.execute(mFailRunnable);
    }

    public static ThreadManager getInstance() {
        return mThreadManager;
    }

    /**
     * 将请求线程加入到队列中
     *
     * @param runnable
     */
    public void addTask(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        try {
            mQuene.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Runnable task = mQuene.take();
                    mThreadPoolExecutor.execute(task);
                } catch (Exception e) {

                }
            }
        }
    };

    public void addFailTask(Delayed delayed) {
        if (delayed == null) {
            return;
        }
        mFailQuene.offer(delayed);
    }

    private Runnable mFailRunnable = new Runnable() {
        @Override
        public void run() {
            HttpTask task;
            try {
                task = (HttpTask) mFailQuene.take();
                if (task.getNum() < 3) {
                    task.setNum(task.getNum() + 1);
                    mThreadPoolExecutor.execute(task);
                } else {
                    JsonRequest request = (JsonRequest) task.getmIHttpRequest();
                    request.getMcallbackListener().onError();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

}
