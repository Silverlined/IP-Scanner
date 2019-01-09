package com.silverlined.ipscanner.TCP;

import android.util.Log;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

public class ConnectTask implements Callable<String> {
    private String mIpAddress;
    private int mPort;

    public ConnectTask(String mIpAddress, int mPort) {
        this.mIpAddress = mIpAddress;
        this.mPort = mPort;
    }

    @Override
    public String call() {
        try {
            Socket mSocket = new Socket(mIpAddress, mPort);
            if (mSocket.isConnected()) {
                mSocket.close();
                return mIpAddress;
            }
        } catch (ConnectException e) {
            String errorMessage = e.getLocalizedMessage();
            if (errorMessage.contains("ECONNREFUSED") || errorMessage.contains("ETIMEDOUT")) {
                return "r" + mIpAddress;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getmIpAddress() {
        return mIpAddress;
    }

    public int getmPort() {
        return mPort;
    }

    //TODO Further research needed, make a scanning app.
}

