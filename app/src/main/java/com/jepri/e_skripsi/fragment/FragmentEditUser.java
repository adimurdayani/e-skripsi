package com.jepri.e_skripsi.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.jepri.e_skripsi.network.api.ApiUrl;
import com.jepri.e_skripsi.network.model.DataUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentEditUser extends Fragment {
    View view;
    EditText nama, email, username;
    TextInputLayout l_nama, l_email, l_username;
    CardView btn_ubah;
    ImageView btn_kembali;
    SharedPreferences preferences;
    ProgressDialog dialog;
    StringRequest postUser;
    ArrayList<DataUser> listUser;
    int id;

    public FragmentEditUser() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_user, container, false);
        init();
        return view;
    }

    private void init() {
        preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        nama = view.findViewById(R.id.nama);
        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        l_nama = view.findViewById(R.id.l_nama);
        l_email = view.findViewById(R.id.l_email);
        l_username = view.findViewById(R.id.l_username);
        btn_ubah = view.findViewById(R.id.btn_ubah);
        btn_kembali = view.findViewById(R.id.btn_kembali);

        nama.setText(preferences.getString("nama", ""));
        email.setText(preferences.getString("email", ""));
        username.setText(preferences.getString("username", ""));
        id = preferences.getInt("id", 0);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        btn_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validasi()) {
                    ubahUser();
                }
            }
        });

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frm_menu, new FragmentOption())
                        .commit();
            }
        });

        nama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (nama.getText().toString().trim().isEmpty()) {
                    l_nama.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (username.getText().toString().trim().isEmpty()) {
                    l_username.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (email.getText().toString().trim().isEmpty()) {
                    l_email.setErrorEnabled(false);
                } else if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
                    l_email.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void ubahUser() {
        listUser = new ArrayList<>();
        dialog.setMessage("Mengirim");
        dialog.show();

        postUser = new StringRequest(Request.Method.POST, ApiUrl.POST_USER, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("nama", nama.getText().toString().trim());
                    editor.putString("email", email.getText().toString().trim());
                    editor.putString("username", username.getText().toString().trim());
                    editor.apply();
                    Toast.makeText(getContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                }else{
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
        }){
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
                map.put("nama", nama.getText().toString().trim());
                map.put("email", email.getText().toString().trim());
                map.put("username", username.getText().toString().trim());
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(postUser);
    }

    private boolean validasi() {
        if (nama.getText().toString().trim().isEmpty()) {
            l_nama.setErrorEnabled(true);
            l_nama.setError("Nama tidak boleh kosong!");
            return false;
        }
        if (email.getText().toString().trim().isEmpty()) {
            l_email.setErrorEnabled(true);
            l_email.setError("Email tidak boleh kosong!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
            l_email.setErrorEnabled(true);
            l_email.setError("Format email salah!");
            return false;
        }
        if (username.getText().toString().trim().isEmpty()) {
            l_username.setErrorEnabled(true);
            l_username.setError("Username tidak boleh kosong!");
            return false;
        }
        return true;
    }
}
