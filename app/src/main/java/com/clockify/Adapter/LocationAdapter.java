package com.clockify.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clockify.R;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyHolder> {

    private Context context;
    private List<String> list;
    private View view;


    public LocationAdapter(Context context, List<String> list){
        this.context=context;
        this.list=list;

    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(context).inflate(R.layout.list_location,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final String locationName = list.get(position);
        holder.namaLokasi.setText(locationName);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,locationName,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView namaLokasi;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            namaLokasi = itemView.findViewById(R.id.tempat);
        }
    }
}
