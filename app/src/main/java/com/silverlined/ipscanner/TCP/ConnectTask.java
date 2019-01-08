package com.silverlined.ipscanner.TCP;

import java.net.Socket;
import java.util.concurrent.Callable;

public class ConnectTask implements Callable<String> {
    private String mIpAddress;
    private int mPort;

    public ConnectTask(String mIpAddress, int mPort) {
        this.mIpAddress = mIpAddress;
        this.mPort = mPort;
    }

    @Override
    public String call() throws Exception {
        Socket mSocket = new Socket(mIpAddress, mPort);
        if (mSocket.isConnected()) {
            mSocket.close();
            return mIpAddress;
        } else return null;
    }

    public String getmIpAddress() {
        return mIpAddress;
    }

    public int getmPort() {
        return mPort;
    }

    //TODO Further research needed, make a scanning app.
}

