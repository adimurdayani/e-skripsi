package com.jepri.e_skripsi.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jepri.e_skripsi.R;
import com.jepri.e_skripsi.fragment.FragmentKirim;
import com.jepri.e_skripsi.fragment.FragmentMenuUtama;
import com.jepri.e_skripsi.fragment.FragmentOption;

public class MenuUtama extends AppCompatActivity {

    private FragmentManager manager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frm_menu, new FragmentMenuUtama()).commit();
        init();
    }
    public void init(){
        bottomNavigationView = findViewById(R.id.btn_navigasi);
        BottomNavigationView.OnNavigationItemSelectedListener navigasi = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new FragmentMenuUtama();
                        break;
                    case R.id.kirim:
                        fragment = new FragmentKirim();
                        break;
                    case R.id.menu:
                        fragment = new FragmentOption();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frm_menu, fragment).commit();
                return true;
            }
        };

        bottomNavigationView.setOnNavigationItemSelectedListener(navigasi);
    }
}