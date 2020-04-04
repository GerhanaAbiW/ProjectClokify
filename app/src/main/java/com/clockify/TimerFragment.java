package com.clockify;

import android.Manifest;
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

import com.clockify.Model.ActivityModel;
import com.clockify.Model.GetToken;
import com.clockify.Model.Model;
import com.clockify.service.BaseResponse;
import com.clockify.service.ClocklifyService;
import com.clockify.service.CreateActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

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
    LinearLayout stopReset, saveDelete;
    Button start, stop, reset, save, delete, maps;
    EditText Desc;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Hours, Seconds, Minutes, MilliSeconds;
    ListView listView;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private static final int REQUEST_LOCATION = 0;
    String start_time,activity,location;

    private Context context;

    private UserDefault userDefault = UserDefault.getInstance();

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_timer, container, false);
        context = rootView.getContext();
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        statusCheck();

        initLayout(rootView);
        timer();

//        if (checkLocationPermission())
//            getLocation();

        //gmaps();
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
        //listView = viewGroup.findViewById(R.id.listview1);
        startTime = viewGroup.findViewById(R.id.start_time);
        endTime = viewGroup.findViewById(R.id.end_time);


    }


    //SimpleDateFormat format = new SimpleDateFormat("HH:MM:SS");


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
                //String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calender.getTime());

                startTime.setText(String.format("%02d", hour) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", second));
                startDate.setText("" + currentDate);

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
                reset.setEnabled(false);
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
//
//                ListElementsArrayList.clear();
//
//                adapter.notifyDataSetChanged();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                apiCreate();

//                ActivityFragment activityFragment = new ActivityFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("start_time", startTime.getText().toString());
//                bundle.putString("end_time", endTime.getText().toString());
//                bundle.putString("start_date", startDate.getText().toString());
//                bundle.putString("end_date", endDate.getText().toString());
//                bundle.putString("stopwatch", textView.getText().toString());
//                //newFragment.setArguments(bundle);
////                bundle.putString(Desc);
////                bundle.putString(maps);
////                bundle.putString(textView);
////                bundle.putString(startTime);
////                bundle.putString(endTime.getText().toString());
//
////                ListElementsArrayList.add(textView.getText().toString());
////                ListElementsArrayList.add(Desc.getText().toString());
////                ListElementsArrayList.add(maps.getText().toString());
////
////
////                adapter.notifyDataSetChanged();
//
//                activityFragment.setArguments(bundle);
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.page, activityFragment, ActivityFragment.class.getSimpleName());
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();

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
    public  void apiCreate(){
        start_time = startTime.getText().toString();
        activity = Desc.getText().toString();
        location = maps.getText().toString();

        CreateActivity createActivity = ClocklifyService.create(CreateActivity.class);
        createActivity.createActivity(start_time,activity,location).enqueue(new Callback<GetToken>() {
            @Override
            public void onResponse(Call<GetToken> call, Response<GetToken> response) {
                if (response.isSuccessful() && response.body() != null){
                    MillisecondTime = 0L;
                    StartTime = 0L;
                    TimeBuff = 0L;
                    UpdateTime = 0L;
                    Seconds = 0;
                    Minutes = 0;
                    Hours = 0;
                    MilliSeconds = 0;

                    textView.setText("00:00:00");
                    String token = response.body().token;
                    userDefault.setString(UserDefault.TOKEN_KEY, "Bearer "+token);
                    //Intent Ok = new Intent(context, TimerActivity.class);
                    //startActivity(Ok);
                }else {
                    FailResponeHandler.handleRespone(context,response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GetToken> call, Throwable t) {
                //loading.setVisibility(View.GONE);
                if(!call.isCanceled()){
                    FailResponeHandler.handlerErrorRespone(context,t);
                }

            }
        });
    }

}


