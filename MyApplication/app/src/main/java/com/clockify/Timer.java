package com.clockify;

import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Timer extends AppCompatActivity {
    TimePicker tp;
    TextView tv;
    OnTimeChangedListener listener;

    private int seconds = 0;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        //supaya dia bisa lanjut ketika di layar putar
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }
        runTimer();

        tp = (TimePicker) findViewById(R.id.timePicker1);
        tv = (TextView) findViewById(R.id.textView1);

        //listener jika terjadi perubahan isi TimePicker

        listener = new OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(timer.this, "Set Waktu: " + hourOfDay + ":" + minute,
                        Toast.LENGTH_SHORT).show();
            }
        };
        //Menerapkan listener pada TimePicker tp
        tp.setOnTimeChangedListener(listener);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickRiset(View view) {
        running = false;
        seconds = 0;
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format("%02d : %02d : %02d", hours, minutes, secs);

                timeView.setText(time);

                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });

    }

}
