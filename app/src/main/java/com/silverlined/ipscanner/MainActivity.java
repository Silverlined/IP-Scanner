package com.silverlined.ipscanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.silverlined.ipscanner.TCP.IP_Finder;
import com.silverlined.ipscanner.adapters.IpAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IP_Finder IPFinder;
    private List<String> mListIps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        mListIps = new ArrayList<>();
        IpAdapter adapter = new IpAdapter(mListIps, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayout.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        IPFinder = new IP_Finder(adapter, mListIps);
        IPFinder.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IPFinder.cancel(true);
    }
}
