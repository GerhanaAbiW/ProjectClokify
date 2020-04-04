package com.clockify;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.clockify.service.ClocklifyService;
import com.clockify.service.LoginSession;


public class LoginEmailFragment extends Fragment {
    EditText textEmail;
    Button btnSign;
    TextView btnCreateAcc;
    private Context context;
    public ConstraintLayout loading;
    //private String userEmail = "gerhana@dihardja.com";
    public LoginEmailListener loginEmailListener;
    String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_login_email, container, false);
        context = rootView.getContext();

        initLayout(rootView);
        loginEmail();
        return rootView;
    }

    private void initLayout(ViewGroup viewGroup) {
        textEmail = viewGroup.findViewById(R.id.email);
        btnSign = viewGroup.findViewById(R.id.btn_sign);
        btnCreateAcc = viewGroup.findViewById(R.id.new_acc);
        loading = viewGroup.findViewById(R.id.progress_circular);

    }

    private void loginEmail() {
        email = textEmail.getText().toString();
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textEmail.getText().toString().equals("")) {
                    Toast.makeText(context, "E-mail harus diisi", Toast.LENGTH_LONG).show();
                } else if (!isValidEmail(textEmail.getText().toString())) {
                    Toast.makeText(context, "Masukan Format dengan benar", Toast.LENGTH_SHORT).show();

                }else
                    loginEmailListener.onButtonSignInClicked(textEmail.getText().toString());
            }
        });
        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerpage = new Intent(context, RegisterActivity.class);
                startActivity(registerpage);

            }
        });
    }


    public static boolean isValidEmail(CharSequence email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public interface LoginEmailListener {
        void onButtonSignInClicked(String email);

    }
}
