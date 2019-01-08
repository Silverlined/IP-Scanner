package com.silverlined.ipscanner.TCP;

import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TaskManager implements Callable<String> {

    private long timeout;
    private TimeUnit timeUnit;
    private ConnectTask connectTask;

    public TaskManager(long timeout, TimeUnit timeUnit, ConnectTask connectTask) {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.connectTask = connectTask;
    }

    @Override
    public String call() {
        String mIpAddress = null;
        ExecutorService exec = Executors.newSingleThreadExecutor();
            try {
                mIpAddress = exec.submit(connectTask).get(timeout, timeUnit);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                // Whatever you want
                if (e instanceof TimeoutException) {
                    Log.e("TIME_OUT:", connectTask.getmIpAddress());
                } else {
                    e.printStackTrace();
                }
            }
            exec.shutdown();
        return mIpAddress;
    }

    //TODO Further research needed, make a scanning app.
}
