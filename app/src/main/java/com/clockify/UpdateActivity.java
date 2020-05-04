package com.clockify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.clockify.Adapter.ActivityAdapter;
import com.clockify.Model.ActivityModel;
import com.clockify.service.ClocklifyService;
import com.clockify.service.ShowActivity;
import com.clockify.service.Update;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {
    private Fragment activityFragment;
    ActivityAdapter adapter;
    TextView textView, startTime, endTime, startDate, endDate;
    LinearLayout  saveDelete;
    Button update, save, delete, maps;
    EditText Desc;
    List<ActivityModel> timer = new ArrayList<>();
    private SimpleDateFormat parser;


    public static final SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss",Locale.getDefault());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyy", Locale.getDefault());

    String location, activity;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity);

        textView = findViewById(R.id.textView);
        maps = findViewById(R.id.maps);
        Desc = findViewById(R.id.desc);
        update = findViewById(R.id.update_button);
        save = findViewById(R.id.save_button);
        delete = findViewById(R.id.delete_button);
        saveDelete = findViewById(R.id.save_delete_button);
        startDate = findViewById(R.id.start_date);
        endDate = findViewById(R.id.end_date);

        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);

        Intent intent = getIntent();
        String start = hourFormat.format(stringDateFormatter(intent.getStringExtra("start_timer")));
        String start_date = dateFormat.format(stringDateFormatter(intent.getStringExtra("start_timer")));
        String stop = hourFormat.format(stringDateFormatter(intent.getStringExtra("stop_timer")));
        String stop_date = dateFormat.format(stringDateFormatter(intent.getStringExtra("stop_timer")));


        String lokasi = intent.getStringExtra("location");
        String aktivitas = intent.getStringExtra("activity");


        startTime.setText(start);
        endTime.setText(stop);
        startDate.setText(start_date);
        endDate.setText(stop_date);
        maps.setText(lokasi);
        Desc.setText(aktivitas);
        //startTime.setText();


        //ini buat mapsnya bsa di edit
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent maps = new Intent(UpdateActivity.this, LocationActivity.class);
                startActivity(maps);
            }
        });
        //ini buat tombol update
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                update.setVisibility(View.GONE);
                saveDelete.setVisibility(View.VISIBLE);
                maps.getEditableText();
                Desc.getEditableText();


            }
        });

        //ini buat tombol save
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiUpdate();

            }
        });



        //ini buat tombol delete
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
    //ini api update
    public void apiUpdate() {
        ProgressDialog progress = new ProgressDialog(UpdateActivity.this);
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();
        activity = Desc.getText().toString();
        location = maps.getText().toString();
        id = getIntent().getIntExtra("id",0);

        //adapter = new ActivityAdapter(UpdateActivity.this);

        //recyclerView.setAdapter(adapter);

        Update update = ClocklifyService.create(Update.class);
        update.updateUser(id,activity, location).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                    //timer = (List<ActivityModel>) response.body();
                    //adapter.notifyDataSetChanged();
                    Intent intent = new Intent(UpdateActivity.this, ActivityFragment.class);
                    startActivity(intent);
                } else {
                    FailResponeHandler.handleRespone(UpdateActivity.this, response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (!call.isCanceled()) {
                    FailResponeHandler.handlerErrorRespone(UpdateActivity.this, t);
                }
            }
        });
    }

}
