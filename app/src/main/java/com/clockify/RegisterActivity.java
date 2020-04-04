package com.clockify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.clockify.Model.GetToken;
import com.clockify.service.ClocklifyService;
import com.clockify.service.SignUp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Button btnCreate;
    Dialog epicDialog;
    ImageView close;
    EditText inputName,inputEmail,inputPassword,inputConfirmPassword;
    String name,email,password,cPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        epicDialog = new Dialog(this);
        btnCreate = findViewById(R.id.btn_create);

        inputName = findViewById(R.id.name);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.Password);
        inputConfirmPassword = findViewById(R.id.ConfirmPassword);
        Validasi();


    }

    public void apiSignUP(){
        SignUp signUp = ClocklifyService.create(SignUp.class);
        signUp.createAcc(name,email,password).enqueue(new Callback<GetToken>() {
            @Override
            public void onResponse(Call<GetToken> call, Response<GetToken> response) {
                if (response.isSuccessful() && response.body() != null){
                    //String token = response.body().token;
                    //userDefault.setString(UserDefault.TOKEN_KEY, token);
                    //Intent Ok = new Intent(RegisterActivity.this, LoginEmailFragment.class);
                    //startActivity(Ok);
                    ShowPopUp();
                }else {
                    FailResponeHandler.handleRespone(RegisterActivity.this,response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GetToken> call, Throwable t) {
                //loading.setVisibility(View.GONE);
                if(!call.isCanceled()){
                    FailResponeHandler.handlerErrorRespone(RegisterActivity.this,t);
                }

            }
        });
    }

    public void Validasi(){

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = inputName.getText().toString();
                email=inputEmail.getText().toString();
                cPass = inputConfirmPassword.getText().toString();
                password = inputPassword.getText().toString();

                if (inputEmail.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "E-mail harus diisi", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(inputEmail.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Masukan Format email dengan benar", Toast.LENGTH_SHORT).show();

                }else if(inputPassword.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "Password harus diisi", Toast.LENGTH_LONG).show();

                }else if(!inputPassword.getText().toString().equals(inputConfirmPassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Password dan confirm password tidak valid", Toast.LENGTH_LONG).show();
                }else {
                    apiSignUP();
                }
            }
        });
    }
    public static boolean isValidEmail(CharSequence email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void ShowPopUp(){
        epicDialog.setContentView(R.layout.activity_pop);
        close=epicDialog.findViewById(R.id.closePopUp);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 5000);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
}
