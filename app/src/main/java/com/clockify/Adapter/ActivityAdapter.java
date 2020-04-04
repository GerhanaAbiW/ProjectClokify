package com.clockify.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.clockify.Holder.MyViewDateHolder;
import com.clockify.Holder.MyViewHolder;
import com.clockify.Model.ActivityContent;
import com.clockify.Model.DateModel;

import com.clockify.R;
import com.clockify.Model.ActivityModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<MyViewHolder> implements Filterable {
    Context context;
    List<ActivityModel> itemList;
    List<DateModel> dateList;
    List<ActivityModel> itemListFiltered;
    LayoutInflater inflater;
    Context c;



    public ActivityAdapter(Context context, List<ActivityModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_activity, parent, false);
        return new MyViewHolder(itemView);
        // buat headernya pake switch case tapi masih error
//        MyViewHolder viewHolder = null;
//        MyViewDateHolder dateHolder = null;
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//
//        switch(viewType) {
//
//            case ActivityContent.Type_Date:
//                View viewDate = inflater.inflate(R.layout.list_activity_header, parent, false);
//                dateHolder = new MyViewDateHolder(viewDate);
//                break;
//
//            case ActivityContent.Type_Content:
//                View viewContent = inflater.inflate(R.layout.list_activity, parent, false);
//                viewHolder = new MyViewHolder(viewContent);
//                break;
//        }
//
//       return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Glide.with(context).load(itemList.get(position).getImage()).into(holder.cart_item_img);
        holder.date.setText(itemList.get(position).getCreatedAt());
        //holder.stopwatch.setText(itemList.get(position).getStopwatch());
        //holder.time.setText(itemList.get(position).getTime());
        holder.startTimer.setText(itemList.get(position).getStart_timer());
        holder.stopTimer.setText(itemList.get(position).getStop_timer());
        holder.activity.setText(itemList.get(position).getActivity());
        holder.location.setText(itemList.get(position).getLocation());
        // klo pake header codenya pake switch case dibawah
//        switch(holder.getItemViewType()) {
//
//            case ActivityContent.Type_Content:
//
//                ActivityModel activityModel = (ActivityModel) itemList.get(position);
//                MyViewHolder myViewHolder = (MyViewHolder) holder;
//                //Populate the data here
//                break;
//
//            case ActivityContent.Type_Date:
//
//                DateModel dateModel = (DateModel) dateList.get(position);
//                MyViewDateHolder myViewDateHolder = (MyViewDateHolder) dateHolder;
//                //Populate the data here
//                break;
//
//        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    itemListFiltered = itemList;
                } else {
                    List<ActivityModel> filteredList = new ArrayList<>();
                    for (ActivityModel row : itemList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getActivity().toLowerCase().contains(charString.toLowerCase()) || row.getLocation().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    itemListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemListFiltered = (ArrayList<ActivityModel>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

//    public class ActivityList {

//        @Override
//        public List<ActivityContent> getAllActivity() {
//            List<ActivityModel> list = getRealmDataManager().getAllActivity();
//
//            List<ActivityContent> activityList = new ArrayList<>();
//            String date = "";
//            for (ActivityModel activityModel: list) {
//                String dateCursor = dateFormatter(activityModel.getCreatedAt());
//                if (date.equalsIgnoreCase(dateCursor)) {
//                    activityList.add(activityModel);
//                } else {
//                    date = dateCursor;
//                    DateModel dateModel = new DateModel(date);
//                    activityList.add(dateModel);
//                    activityList.add(list);
//                }
//            }
//
//            return activityList;
//        }

//    }
}
