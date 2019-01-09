package com.silverlined.ipscanner.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.silverlined.ipscanner.R;

import java.util.List;

public class IpAdapter extends RecyclerView.Adapter<DeviceInfoViewHolder> {

    private List<String> mDeviceIps;

    public IpAdapter(List<String> mDeviceIps, Context mContext) {
        this.mDeviceIps = mDeviceIps;
    }

    @NonNull
    @Override
    public DeviceInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview, parent, false);
        return new DeviceInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceInfoViewHolder holder, int position) {
        if (!mDeviceIps.isEmpty()) {
            holder.setText(mDeviceIps.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return mDeviceIps == null ? 0 : mDeviceIps.size();
    }
}
