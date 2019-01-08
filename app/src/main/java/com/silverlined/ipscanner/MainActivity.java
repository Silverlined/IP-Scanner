package com.silverlined.ipscanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.silverlined.ipscanner.TCP.IP_Finder;

public class MainActivity extends AppCompatActivity {

    public TextView txt_ips;
    IP_Finder IPFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_ips = findViewById(R.id.txt_ips);
        IPFinder = new IP_Finder(this);
        IPFinder.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IPFinder.cancel(true);
    }
}