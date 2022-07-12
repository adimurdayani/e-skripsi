package com.jepri.e_skripsi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jepri.e_skripsi.R;

public class DetailJudul extends AppCompatActivity {

    ImageView btn_kembali;
    TextView nama, nim, tanggal, keterangan, judul, no_hp, email, kelamin,
            id_tema, jurusan, konsentrasi, pem_satu, pem_dua, is_active;
    LinearLayout status_belum, status_aktif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_judul);
        init();
    }

    public void init() {
        btn_kembali = findViewById(R.id.btn_kembali);
        nama = findViewById(R.id.nama);
        tanggal = findViewById(R.id.tanggal);
        keterangan = findViewById(R.id.keterangan);
        judul = findViewById(R.id.judul);
        no_hp = findViewById(R.id.no_hp);
        email = findViewById(R.id.email);
        kelamin = findViewById(R.id.kelamin);
        nim = findViewById(R.id.nim);
        id_tema = findViewById(R.id.tema);
        jurusan = findViewById(R.id.jurusan);
        konsentrasi = findViewById(R.id.konsentrasi);
        pem_satu = findViewById(R.id.pem_satu);
        pem_dua = findViewById(R.id.pem_dua);
        status_aktif = findViewById(R.id.badge_aktif);
        status_belum = findViewById(R.id.badge_belum);
        is_active = findViewById(R.id.is_active);

        id_tema.setText(getIntent().getStringExtra("id_tema"));
        nama.setText(getIntent().getStringExtra("nama"));
        nim.setText(getIntent().getStringExtra("nim"));
        email.setText(getIntent().getStringExtra("email"));
        no_hp.setText(getIntent().getStringExtra("no_hp"));
        tanggal.setText(getIntent().getStringExtra("tanggal"));
        judul.setText(getIntent().getStringExtra("judul"));
        keterangan.setText(getIntent().getStringExtra("keterangan"));
        kelamin.setText(getIntent().getStringExtra("kelamin"));
        jurusan.setText(getIntent().getStringExtra("jurusan"));
        konsentrasi.setText(getIntent().getStringExtra("konsentrasi"));
        pem_satu.setText(getIntent().getStringExtra("pem_satu"));
        pem_dua.setText(getIntent().getStringExtra("pem_dua"));
        is_active.setText(getIntent().getStringExtra("is_active"));

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MenuUtama.class));
                finish();
            }
        });
    }
}