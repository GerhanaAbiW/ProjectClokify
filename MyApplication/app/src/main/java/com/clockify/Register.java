package com.clockify;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.ConversationActions;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText email,password,c_password;
    private Button btn_regist;
    private ProgressBar loading;
    private static String URL_REGIST = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);

        loading = findViewById(R.id.loading);
        email = findViewById(R.id.email);
        password = findViewById(R.id.Password);
        c_password = findViewById(R.id.ConfirmPassword);

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void Regist() {
        loading.setVisibility(View.VISIBLE);
        btn_regist.setVisibility(View.GONE);

        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String c_password = this.c_password.getText().toString().trim();



    }
}
