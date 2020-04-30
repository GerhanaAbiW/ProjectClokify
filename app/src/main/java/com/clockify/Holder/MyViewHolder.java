package com.clockify.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clockify.Adapter.ActivityAdapter;
import com.clockify.Helper.ItemClickListener;
import com.clockify.R;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView date,startTimer,stopTimer,activity,location,stopwatch;
    public ImageView cart_item_img;
    public ItemClickListener itemClickListener;

    public MyViewHolder(@NonNull View itemView) {
         super(itemView);
         date = itemView.findViewById(R.id.date);
         stopwatch =  itemView.findViewById(R.id.stop_watch);
         startTimer = itemView.findViewById(R.id.startTimer);
         stopTimer = itemView.findViewById(R.id.stopTimer);
         activity = itemView.findViewById(R.id.aktivitas);
         location = itemView.findViewById(R.id.lokasi);

         itemView.setOnClickListener(this);
     }

    @Override
    public void onClick(View v) {
        this.itemClickListener.OnItemClickListener(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener click){
         this.itemClickListener=click;
    }


    }



