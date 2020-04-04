package com.clockify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Fragment login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        showPageEmail();
    }

    private void showPageEmail() {
        LoginEmailFragment loginEmail = new LoginEmailFragment();
        login = loginEmail;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login, login);
        transaction.commit();

        loginEmail.loginEmailListener = new LoginEmailFragment.LoginEmailListener() {
            @Override
            public void onButtonSignInClicked(String email) {
                Fragment newFragment = new LoginPasswordFragment();
                Bundle bundle = new Bundle();
                bundle.putString("email", email);
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.login, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        };

    }

}
