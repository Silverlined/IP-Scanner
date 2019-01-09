package com.silverlined.ipscanner.TCP;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class IP_Finder extends AsyncTask<Void, Void, List> {
    private static final int SERVER_PORT = 5045;
    private String mPhoneIpAddress;
    private WeakReference<TextView> textViewWeakReference;
    private List<Future<String>> mFutureList;

    public IP_Finder(TextView view) {
        textViewWeakReference = new WeakReference<>(view);
        mFutureList = new ArrayList<>();
    }

    @Override
    protected List doInBackground(Void... voids) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        mPhoneIpAddress = getIPAddress();
        String mIpAddress = mPhoneIpAddress.substring(0, mPhoneIpAddress.lastIndexOf('.') + 1);
        Log.e("TAG", mIpAddress);
        for (int i = 1; i < 255; i++) {
            String testIp = mIpAddress + String.valueOf(i);
            ConnectTask connectTask = new ConnectTask(testIp, SERVER_PORT);
            Future<String> future = executorService.submit(new TaskManager(100, TimeUnit.MILLISECONDS, connectTask));
            mFutureList.add(future);
        }

        List<String> listOfDevices = new ArrayList<>();
        String resultIp = null;
        for (Future<String> connection : mFutureList) {
            try {
                resultIp = connection.get();
                if (resultIp != null) {
                    if (resultIp.charAt(0) == 'r') {
                        resultIp = resultIp.substring(1, resultIp.length());
                    }
                    listOfDevices.add(resultIp);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return listOfDevices;
    }

    @Override
    protected void onPostExecute(List listOfDevices) {
        super.onPostExecute(listOfDevices);
        TextView activity = textViewWeakReference.get();
        if (activity == null) {
            return;
        }
        if (!listOfDevices.isEmpty()) {
            activity.setText(Arrays.toString(listOfDevices.toArray()));
        } else activity.setText("404 Not Found");
    }

    private String getIPAddress() {
        List<NetworkInterface> interfaces = null;
        String ip;
        try {
            interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
        } catch (SocketException e) {
            e.printStackTrace();
        }
        for (NetworkInterface networkInterface : interfaces) {
            List<InetAddress> inetAddressList = Collections.list(networkInterface.getInetAddresses());
            for (InetAddress address : inetAddressList) {
                if (!address.isLoopbackAddress()) {
                    ip = address.getHostName();
                    if (isIPv4(ip)) {
                        return ip;
                    }
                }
            }
        }
        return null;
    }

    private boolean isIPv4(final String address) {
        Pattern regexPattern = Pattern.compile("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
        return Pattern.matches(String.valueOf(regexPattern), address);
    }
}
