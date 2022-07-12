package com.jepri.e_skripsi.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.android.material.textfield.TextInputLayout;
import com.jepri.e_skripsi.R;
import com.jepri.e_skripsi.activity.Login;
import com.jepri.e_skripsi.activity.MenuUtama;
import com.jepri.e_skripsi.network.api.ApiUrl;
import com.jepri.e_skripsi.network.model.DataUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FragmentPengaturan extends Fragment {
    View view;
    EditText password, konfirmasi_password;
    TextInputLayout l_password, l_konf_pass;
    CardView btn_ubah;
    ImageView btn_kembali;
    SharedPreferences preferences;
    ProgressDialog dialog;
    StringRequest postPassword;
    ArrayList<DataUser> listUser;
    int id;
    public static final Pattern PASSWORD_FORMAT = Pattern.compile("^" +
            "(?=.*[1-9])" + //harus menggunakan satu angka
            "(?=.*[a-z])" + //harus menggunakan abjad
            "(?=.*[A-Z])" + //harus menggunakan huruf kapital
            "(?=.*[@#$%^&+=])" + //harus menggunakan sepesial karakter
            "(?=\\S+$)" + // tidak menggunakan spasi
            ".{6,}" + //harus lebih dari 6 karakter
            "$"
    );

    public FragmentPengaturan() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pengaturan, container, false);
        init();
        return view;
    }

    private void init() {
        preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        password = view.findViewById(R.id.password);
        konfirmasi_password = view.findViewById(R.id.konfirmasi_password);
        l_password = view.findViewById(R.id.l_password);
        l_konf_pass = view.findViewById(R.id.l_konf_pass);
        btn_ubah = view.findViewById(R.id.btn_ubah);
        btn_kembali = view.findViewById(R.id.btn_kembali);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        id = preferences.getInt("id", 0);

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.frm_menu, new FragmentOption())
                        .commit();
            }
        });

        btn_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validasi()) {
                    ubahPassword();
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.getText().toString().trim().isEmpty()) {
                    l_password.setErrorEnabled(false);
                } else if (PASSWORD_FORMAT.matcher(password.getText().toString().trim()).matches()) {
                    l_password.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        konfirmasi_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (konfirmasi_password.getText().toString().trim().isEmpty()) {
                    l_konf_pass.setErrorEnabled(false);
                } else if (PASSWORD_FORMAT.matcher(konfirmasi_password.getText().toString().trim()).matches()) {
                    l_konf_pass.setErrorEnabled(false);
                } else if (konfirmasi_password.getText().toString().trim().matches(password.getText().toString().trim())) {
                    l_konf_pass.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void ubahPassword() {
        listUser = new ArrayList<>();
        dialog.setMessage("Mengirim");
        dialog.show();

        postPassword = new StringRequest(Request.Method.POST, ApiUrl.POST_PASSWORD, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")) {
                    Toast.makeText(getContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent((MenuUtama) getContext(), Login.class);
                    startActivity(intent);
                    ((MenuUtama) getContext()).finish();
                } else {
                    Toast.makeText(getContext(), "Terjadi masalah", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Terjadi masalah: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        }, error -> {
            error.printStackTrace();
            dialog.dismiss();
            Toast.makeText(getContext(), "Terjadi masalah koneksi!", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = preferences.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + token);
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(id));
                map.put("password", password.getText().toString().trim());
                map.put("konfirmasi_password", konfirmasi_password.getText().toString().trim());
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(postPassword);
    }

    private boolean validasi() {
        if (password.getText().toString().trim().isEmpty()) {
            l_password.setErrorEnabled(true);
            l_password.setError("Password tidak boleh kosong!");
            return false;
        } else if (!PASSWORD_FORMAT.matcher(password.getText().toString().trim()).matches()) {
            l_password.setErrorEnabled(true);
            l_password.setError("Password sangat lemah!");
            return false;
        }
        if (konfirmasi_password.getText().toString().trim().isEmpty()) {
            l_konf_pass.setErrorEnabled(true);
            l_konf_pass.setError("Konfirmasi password tidak boleh kosong!");
            return false;
        } else if (!PASSWORD_FORMAT.matcher(konfirmasi_password.getText().toString().trim()).matches()) {
            l_konf_pass.setErrorEnabled(true);
            l_konf_pass.setError("Konfirmasi password sangat lemah!");
            return false;
        } else if (!konfirmasi_password.getText().toString().trim().matches(password.getText().toString().trim())) {
            l_konf_pass.setErrorEnabled(true);
            l_konf_pass.setError("Konfirmasi password tidak sama!");
            return false;
        }
        return true;
    }
}
