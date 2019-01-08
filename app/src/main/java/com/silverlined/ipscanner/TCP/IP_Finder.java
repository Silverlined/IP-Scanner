package com.silverlined.ipscanner.TCP;

import android.os.AsyncTask;

import com.silverlined.ipscanner.MainActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class IP_Finder extends AsyncTask<Void, Void, String> {
    private static final String INITIAL_IP_ADDRESS = "192.168.178.1";
    private static final int SERVER_PORT = 5045;
    private WeakReference<MainActivity> activityWeakReference;
    private List<Future<String>> futureList;

    public IP_Finder(MainActivity activity) {
        activityWeakReference = new WeakReference<>(activity);
        futureList = new ArrayList<>();
    }

    @Override
    protected String doInBackground(Void... voids) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        String resultIp = null;
        String mIpAddress = INITIAL_IP_ADDRESS;
        int mPort = SERVER_PORT;
        for (int i = 1; i < 255; i++) {
            ConnectTask connectTask = new ConnectTask(mIpAddress, mPort);
            Future<String> future = executorService.submit(new TaskManager(100, TimeUnit.MILLISECONDS, connectTask));
            futureList.add(future);
            mIpAddress = mIpAddress.substring(0, 12) + String.valueOf(i);
        }

        for (Future<String> connection : futureList) {
            try {
                resultIp = connection.get();
                if (resultIp != null) {
                    return resultIp;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String ip) {
        super.onPostExecute(ip);
        MainActivity activity = activityWeakReference.get();
        if(activity == null || activity.isFinishing()) {
            return;
        }
        if (ip != null) {
            activity.txt_ips.setText(ip);
        }
        else activity.txt_ips.setText("404 Not Found");
    }
}
