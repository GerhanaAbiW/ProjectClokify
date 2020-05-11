package com.clockify;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clockify.Adapter.ActivityAdapter;
import com.clockify.Model.ActivityModel;
import com.clockify.Model.GetToken;
import com.clockify.Model.Model;
import com.clockify.service.BaseResponse;
import com.clockify.service.ClocklifyService;
import com.clockify.service.CreateActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;


public class TimerFragment extends Fragment {
    TextView textView, startTime, endTime, startDate, endDate;
    ActivityAdapter adapter;
    LinearLayout stopReset, saveDelete;
    Button start, stop, reset, save, delete, maps;
    EditText Desc;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Hours, Seconds, Minutes, MilliSeconds;

    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private static final int REQUEST_LOCATION = 0;
    String start_timer, activity, location, stop_timer;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_timer, container, false);
        context = rootView.getContext();
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        statusCheck();

        initLayout(rootView);
        timer();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        maps.setText(0 + ", " + 0);
        try {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                maps.setText(latitude + ", " + longitude);
                            }
                        }
                    });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        checkPermission();
    }

    private void checkPermission() {
        ArrayList<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (permissionList.size() > 0) {
            maps.setText(0 + ", " + 0);
            String[] permissionArray = permissionList.toArray(new String[]{});
            ActivityCompat.requestPermissions(getActivity(), permissionArray, REQUEST_LOCATION);
        } else {
            try {
                fusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {

                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                    maps.setText(latitude + ", " + longitude);
                                }
                            }
                        });
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public void statusCheck() {
        LocationManager service = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Check Location")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    private void initLayout(ViewGroup viewGroup) {
        textView = viewGroup.findViewById(R.id.textView);
        maps = viewGroup.findViewById(R.id.maps);
        Desc = viewGroup.findViewById(R.id.desc);
        start = viewGroup.findViewById(R.id.button);
        stop = viewGroup.findViewById(R.id.button2);
        reset = viewGroup.findViewById(R.id.button3);
        save = viewGroup.findViewById(R.id.button4);
        delete = viewGroup.findViewById(R.id.button5);
        stopReset = viewGroup.findViewById(R.id.stop_reset);
        saveDelete = viewGroup.findViewById(R.id.save_delete);
        startDate = viewGroup.findViewById(R.id.start_date);
        endDate = viewGroup.findViewById(R.id.end_date);
        startTime = viewGroup.findViewById(R.id.start_time);
        endTime = viewGroup.findViewById(R.id.end_time);
    }

    public void timer() {
        saveDelete.setVisibility(View.GONE);
        stopReset.setVisibility(View.GONE);
        start.setVisibility(View.VISIBLE);
        handler = new Handler();


        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent maps = new Intent(context, LocationActivity.class);
                startActivity(maps);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                Calendar calender = Calendar.getInstance();
                int hour = calender.get(Calendar.HOUR_OF_DAY);
                int minutes = calender.get(Calendar.MINUTE);
                int second = calender.get(Calendar.SECOND);

                String currentDate = new SimpleDateFormat("dd MMM yy", Locale.getDefault()).format(new Date());


                startTime.setText(String.format("%02d", hour) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", second));
                startDate.setText("" + currentDate);

                start_timer = format.format(new Date()) + " " + String.format("%02d", hour) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", second);
                start.setVisibility(View.GONE);
                saveDelete.setVisibility(View.GONE);
                stopReset.setVisibility(View.VISIBLE);
                reset.setEnabled(false);
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);


                start.setVisibility(View.GONE);
                stopReset.setVisibility(View.GONE);
                saveDelete.setVisibility(View.VISIBLE);

                Calendar clr = Calendar.getInstance();
                int jam = clr.get(Calendar.HOUR_OF_DAY);
                int menit = clr.get(Calendar.MINUTE);
                int detik = clr.get(Calendar.SECOND);
                endTime.setText(String.format("%02d", jam) + ":" + String.format("%02d", menit) + ":" + String.format("%02d", detik));
                String currentDate = new SimpleDateFormat("dd MMM yy", Locale.getDefault()).format(new Date());
                endDate.setText("" + currentDate);

                stop_timer = format.format(new Date()) + " " + String.format("%02d", jam) + ":" + String.format("%02d", menit) + ":" + String.format("%02d", detik);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L;
                StartTime = 0L;
                TimeBuff = 0L;
                UpdateTime = 0L;
                Seconds = 0;
                Minutes = 0;
                Hours = 0;
                MilliSeconds = 0;

                textView.setText("00:00:00");

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiCreate();
                start.setVisibility(View.VISIBLE);
                stopReset.setVisibility(View.GONE);
                saveDelete.setVisibility(View.GONE);
                MillisecondTime = 0L;
                StartTime = 0L;
                TimeBuff = 0L;
                UpdateTime = 0L;
                Seconds = 0;
                Minutes = 0;
                Hours = 0;
                MilliSeconds = 0;
                textView.setText("00:00:00");
                startTime.setText("-");
                endTime.setText("-");
                startDate.setText("-");
                endDate.setText("-");
                Desc.setText("");


            }
        });

    }


    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);


            Minutes = Seconds / 60;
            if (Minutes == 60) {
                Minutes = 0;
            }
            Seconds = Seconds % 60;

            Hours = Minutes / 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            textView.setText(String.format("%02d", Hours) + ":"
                    + String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds));

            handler.postDelayed(this, 0);
        }

    };

    public void apiCreate() {
        activity = Desc.getText().toString();
        location = maps.getText().toString();
        Date newStartTime=null;
        Date newEndTime= null;
        try {
            newStartTime = newFormat.parse(start_timer);
            newEndTime = newFormat.parse(stop_timer);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CreateActivity createActivity = ClocklifyService.create(CreateActivity.class);
        createActivity.createActivity(newStartTime, newEndTime, activity, location).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    //adapter.notifyDataSetChanged();
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    FailResponeHandler.handleRespone(context, response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (!call.isCanceled()) {
                    FailResponeHandler.handlerErrorRespone(context, t);
                }

            }
        });
    }

}


