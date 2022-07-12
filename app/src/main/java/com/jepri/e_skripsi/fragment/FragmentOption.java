package com.jepri.e_skripsi.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jepri.e_skripsi.R;
import com.jepri.e_skripsi.activity.Login;
import com.jepri.e_skripsi.activity.MenuUtama;
import com.jepri.e_skripsi.network.api.ApiUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentOption extends Fragment {
    View view;
    CardView btn_ubah, btn_facebook, btn_instagram, btn_whatsapp;
    LinearLayout btn_pengaturan, btn_bantuan, btn_keluar;
    TextView nama;
    SharedPreferences preferences;
    StringRequest prosesLogout;

    public FragmentOption() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_option, container, false);
        init();
        return view;
    }

    private void init() {
        preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        btn_ubah = view.findViewById(R.id.btn_ubah);
        btn_pengaturan = view.findViewById(R.id.btn_pengaturan);
        btn_facebook = view.findViewById(R.id.btn_facebook);
        btn_instagram = view.findViewById(R.id.btn_instagram);
        btn_whatsapp = view.findViewById(R.id.btn_whatsapp);
        nama = view.findViewById(R.id.nama);
        btn_keluar = view.findViewById(R.id.btn_keluar);
        btn_bantuan = view.findViewById(R.id.btn_bantuan);

        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fb = new Intent();
                fb.setAction(Intent.ACTION_VIEW);
                fb.addCategory(Intent.CATEGORY_BROWSABLE);
                fb.setData(Uri.parse("https://web.facebook.com/?_rdc=1&_rdr/"));
                startActivity(fb);
            }
        });

        btn_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ig = new Intent();
                ig.setAction(Intent.ACTION_VIEW);
                ig.addCategory(Intent.CATEGORY_BROWSABLE);
                ig.setData(Uri.parse("https://www.instagram.com/"));
                startActivity(ig);
            }
        });
        btn_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wa = new Intent();
                wa.setAction(Intent.ACTION_VIEW);
                wa.addCategory(Intent.CATEGORY_BROWSABLE);
                wa.setData(Uri.parse("https://api.whatsapp.com/send?phone=6282148204971"));
                startActivity(wa);
            }
        });

        btn_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Apakah anda yakin ingin keluar?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        btn_bantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frm_menu, new FragmentBantuan())
                        .commit();
            }
        });

        nama.setText(preferences.getString("nama", ""));

        btn_pengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.frm_menu, new FragmentPengaturan())
                        .commit();
            }
        });
        btn_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                fragmentManager.beginTransaction()
                        .replace(R.id.frm_menu, new FragmentEditUser())
                        .commit();
            }
        });
    }

    private void logout() {
        prosesLogout = new StringRequest(Request.Method.GET, ApiUrl.LOGOUT, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(((MenuUtama) getContext()), Login.class));
                    ((MenuUtama) getContext()).finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(getContext(), "Terjadi Masalah Koneksi", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = preferences.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + token);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(prosesLogout);
    }
}
