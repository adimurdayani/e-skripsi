package com.jepri.e_skripsi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.jepri.e_skripsi.R;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences session_data;
    private boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                session_data = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
                isLoggedIn = session_data.getBoolean("isLoggedIn", false);
                if (isLoggedIn) {
                    startActivity(new Intent(getApplicationContext(), MenuUtama.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), OnBoard.class));
                    finish();
                }
            }
        }, 3000);
    }
}