//package com.clockify;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.SystemClock;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.clockify.Adapter.ActivityAdapter;
//import com.clockify.Model.ActivityModel;
//import com.clockify.service.ClocklifyService;
//import com.clockify.service.ShowActivity;
//import com.clockify.service.Update;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class UpdateActivity extends AppCompatActivity {
//
//        TextView textView, startTime, endTime, startDate, endDate;
//        LinearLayout stopReset, saveDelete;
//        Button update, stop, reset, save, delete, maps;
//        EditText Desc;
//        String location, activity;
//
//@Override
//protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.update_activity);
//    textView = findViewById(R.id.textView);
//    maps = findViewById(R.id.maps);
//    Desc = findViewById(R.id.desc);
//    update = findViewById(R.id.update_button);
//    save = findViewById(R.id.save_button);
//    delete = findViewById(R.id.delete_button);
//    saveDelete = findViewById(R.id.save_delete_buutton);
//    startDate = findViewById(R.id.start_date);
//    endDate = findViewById(R.id.end_date);
//
//    startTime = findViewById(R.id.start_time);
//    endTime = findViewById(R.id.end_time);
//
//}
//ini buat mapsnya bsa di edit
// maps.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent maps = new Intent(context, LocationActivity.class);
//            startActivity(maps);
//        }
//    });
//ini buat tombol update
//        update.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//
//            stopReset.setVisibility(View.VISIBLE);
//
//
//
//        }
//    });
//
//ini buat tombol save
//        save.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//
//            activity = Desc.getText().toString();
//            location = maps.getText().toString();
//            apiUpdate();
//
//
//        }
//    });
//ini buat tombol delete
//        delete.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//
////
////                ListElementsArrayList.clear();
////
////                adapter.notifyDataSetChanged();
//        }
//    });
//ini api update
//    public  void apiUpdate(){
//
//        adapter = new ActivityAdapter(UpdateActivity.this,timer);
//
//        recyclerView.setAdapter(adapter);
//
//        Update update = ClocklifyService.create(Update.class);
//        update.updateUser(activity,location).enqueue(new Callback<ActivityModel>() {
//            @Override
//            public void onResponse(Call<ActivityModel> call, Response<ActivityModel> response) {
//                if (response.isSuccessful() && response.body() != null){
//                    timer = response.body();
//                    adapter.notifyDataSetChanged();
////                    List<Item> itemList=new ArrayList<>();
////                    itemList.add(new Item(timer));
////                    adapter = new ActivityAdapter(context,itemList);
////                    recyclerView.setAdapter(adapter);
//                    //listStopwatch.addAll(timer);
//
//                }else {
//                    FailResponeHandler.handleRespone(UpdateActivity.this,response.errorBody());
//                    Toast.makeText(UpdateActivity.this, "Gagal mengambil data Activity", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ActivityModel> call, Throwable t) {
//                if(!call.isCanceled()){
//                    FailResponeHandler.handlerErrorRespone(UpdateActivity.this,t);
//                }
//            }
//        });
//    }
//
//}
