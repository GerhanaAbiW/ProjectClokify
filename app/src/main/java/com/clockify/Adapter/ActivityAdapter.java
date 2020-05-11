package com.clockify.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.clockify.Holder.MyViewDateHolder;
import com.bumptech.glide.Glide;
import com.clockify.Helper.ItemClickListener;
import com.clockify.Holder.MyViewHolder;
import com.clockify.Model.ActivityContent;
import com.clockify.Model.DateModel;

import com.clockify.R;
import com.clockify.Model.ActivityModel;
import com.clockify.UpdateActivity;
import com.clockify.service.Update;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ActivityAdapter extends RecyclerView.Adapter<MyViewHolder>{
    Context context;
    List<ActivityModel> itemList;
    private SimpleDateFormat parser;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyy", Locale.getDefault());
    

    public static final SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss",Locale.getDefault());

    private String search;


    public ActivityAdapter(Context context) {
        this.context = context;
    }




    public void updateAdapter(List<ActivityModel> itemList, String search) {
        this.itemList = itemList;
        this.search = search;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_activity, parent, false);
        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ActivityModel item = itemList.get(position);
        String createdAt = dateFormat.format(stringDateFormatter(item.getCreatedAt()));

        if(!search.equalsIgnoreCase("")){
            holder.date.setVisibility(View.GONE);
        } else {
            if (position > 0) {
                if (createdAt.equalsIgnoreCase(itemList.get(position - 1).createdAt)) {
                    holder.date.setVisibility(View.GONE);
                } else {
                    holder.date.setVisibility(View.VISIBLE);
                }
            } else {
                holder.date.setVisibility(View.VISIBLE);
            }
        }
//        Collections.sort(itemList, new Comparator<Date>(){
//            public int compare(Date date1, Date date2){
//                return date1.after(date2);
//            }
//        });



           if(position>0){
               if(itemList.get(position).createdAt.equalsIgnoreCase(itemList.get(position-1).createdAt)){
                   holder.date.setVisibility(View.GONE);
               }else{
                   holder.date.setVisibility(View.VISIBLE);
               }
           }


        holder.date.setText(createdAt);
        holder.stopwatch.setText(itemList.get(position).getStopwatch());

        if(item.getStart_timer()==null){
            holder.startTimer.setText("");

        }else{
            String start_timer = hour.format(stringDateFormatter(item.getStart_timer()));
            holder.startTimer.setText(start_timer);

        }
        if(item.getStop_timer()==null){
            holder.stopTimer.setText("");

        }else{
            String stop_timer = hour.format(stringDateFormatter(item.getStop_timer()));
            holder.stopTimer.setText(stop_timer);

        }

        holder.activity.setText(item.getActivity());
        holder.location.setText(item.getLocation());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnItemClickListener(View v, int position) {
                String start_timer = itemList.get(position).getStart_timer();
                String stop_timer = itemList.get(position).getStop_timer();
                String location = itemList.get(position).getLocation();
                String activity = itemList.get(position).getActivity();
                Integer id = itemList.get(position).getId();

                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("start_timer",start_timer);
                intent.putExtra("stop_timer",stop_timer);
                intent.putExtra("location",location);
                intent.putExtra("activity",activity);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });


    }

    public Date stringDateFormatter(String dateString) {
        Date date = null;
        if (parser == null) {
            parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        }
        parser.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
        try {
            date = parser.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }



    @Override
    public int getItemCount() {
        if (itemList != null)
            return itemList.size();
        return 0;
    }




}
