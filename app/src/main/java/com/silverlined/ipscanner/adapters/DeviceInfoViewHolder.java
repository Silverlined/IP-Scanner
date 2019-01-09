package com.silverlined.ipscanner.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.silverlined.ipscanner.R;

class DeviceInfoViewHolder extends RecyclerView.ViewHolder {
    private TextView txt_ip;

    public DeviceInfoViewHolder(View itemView) {
        super(itemView);
        txt_ip = itemView.findViewById(R.id.txt_ip);
    }

    public void setText(String ip, int position) {
        txt_ip.setText(ip);
    }
}
