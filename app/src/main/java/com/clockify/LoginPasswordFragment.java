package com.clockify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clockify.Model.GetToken;
import com.clockify.service.ClocklifyService;
import com.clockify.service.LoginSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPasswordFragment extends Fragment {
    EditText inputPassword;
    Button  btnOk;
    TextView btnForgot;
    public ConstraintLayout loading;
    private Context passwordContext;
    //private String userPassword ="ongthe";
    String password;
    private String email;

    private UserDefault userDefault = UserDefault.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_login_password, container, false) ;
        passwordContext = rootView.getContext();

        initLayout(rootView);

        if (getArguments() != null){
            email = getArguments().getString("email");
        }

        loginPassword();
        return rootView;
    }

    private void initLayout(ViewGroup viewGroup){
        inputPassword = viewGroup.findViewById(R.id.password);
        btnOk = viewGroup.findViewById(R.id.btn_ok);
        loading = viewGroup.findViewById(R.id.loading_pass);
    }

    public  void apiLogin(){

        LoginSession loginSession = ClocklifyService.create(LoginSession.class);
        loginSession.login(email,password).enqueue(new Callback<GetToken>() {
            @Override
            public void onResponse(Call<GetToken> call, Response<GetToken> response) {
                if (response.isSuccessful() && response.body() != null){
                    String token = response.body().token;
                    userDefault.setString(UserDefault.TOKEN_KEY, token);
                    Intent Ok = new Intent(passwordContext, TimerActivity.class);
                    startActivity(Ok);
                }else {
                    FailResponeHandler.handleRespone(passwordContext,response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GetToken> call, Throwable t) {
                loading.setVisibility(View.GONE);
                if(!call.isCanceled()){
                    FailResponeHandler.handlerErrorRespone(passwordContext,t);
                }

            }
        });
    }
    private void loginPassword() {
        ;
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loading.setVisibility(View.VISIBLE);
                password=inputPassword.getText().toString();
                if (inputPassword.getText().toString().equals("")||inputPassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(passwordContext, "Password harus diisi", Toast.LENGTH_LONG).show();

                } else {

                    apiLogin();

                }
            }
        });
    }

}
