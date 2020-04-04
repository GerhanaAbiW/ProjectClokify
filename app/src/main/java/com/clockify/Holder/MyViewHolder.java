package com.clockify.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clockify.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView date,startTimer,stopTimer,activity,location;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.date);
        //stopwatch = (TextView) itemView.findViewById(R.id.stop_watch);
        //time = (TextView) itemView.findViewById(R.id.waktu);
        startTimer =itemView.findViewById(R.id.startTimer);
        stopTimer = itemView.findViewById(R.id.endTimer);
        activity = (TextView) itemView.findViewById(R.id.aktivitas);
        location = (TextView) itemView.findViewById(R.id.lokasi);
    }


}
