package com.jepri.e_skripsi.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.google.android.material.textfield.TextInputLayout;
import com.jepri.e_skripsi.R;
import com.jepri.e_skripsi.network.api.ApiUrl;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private CardView btn_back, btn_sign;
    private TextInputLayout l_username, l_password;
    private EditText username, password;
    private ProgressDialog dialog;
    private StringRequest loginUser;
    private SharedPreferences session_data;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init() {
        btn_back = findViewById(R.id.btn_kembali);
        btn_sign = findViewById(R.id.btn_sign);
        l_username = findViewById(R.id.l_username);
        l_password = findViewById(R.id.l_password);
        username = findViewById(R.id.edt_username);
        password = findViewById(R.id.edt_password);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), OnBoard.class));
                finish();
            }
        });

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validasi()) {
                    login();
                }
            }
        });

        cekValidasi();
    }

    private void login() {
        dialog.setMessage("Loading...");
        dialog.show();
//        inisialisasi volley post login
        loginUser = new StringRequest(Request.Method.POST, ApiUrl.LOGIN, response -> {

//             ubah response data ke json
            try {
                JSONObject object = new JSONObject(response);

//                 jika login sukses maka maka menampilkan status true
                if (object.getBoolean("status")) {

//                     jika login sukses maka tampilkan data user yang login dan simpan ke dalam session
                    JSONObject data = object.getJSONObject("data");

//                     simpan data ke session
                    session_data = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                    editor = session_data.edit();
                    editor.putString("token", object.getString("token"));
                    editor.putInt("id", data.getInt("id"));
                    editor.putString("nama", data.getString("nama"));
                    editor.putString("username", data.getString("username"));
                    editor.putString("email", data.getString("email"));
                    editor.putInt("user_ID", data.getInt("user_id"));
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), MenuUtama.class));
                    finish();

//                    display jika login berhasil
                    Toast.makeText(getApplicationContext(), "Login success!", Toast.LENGTH_SHORT).show();
                } else {
//                    display jika login gagal
                    Toast.makeText(getApplicationContext(), "Username atau password salah!" , Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();

        }, error -> {
            dialog.dismiss();
            error.printStackTrace();
            Toast.makeText(getApplicationContext(), "Data tidak di temukan", Toast.LENGTH_SHORT).show();
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", username.getText().toString().trim());
                map.put("password", password.getText().toString().trim());
                return map;
            }
        };

//        membuat koneksi API dengan volley
        RequestQueue koneksi = Volley.newRequestQueue(getApplicationContext());
        koneksi.add(loginUser);
    }

    private void cekValidasi() {
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

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.getText().toString().trim().length() > 7) {
                    l_password.setErrorEnabled(false);
                } else if (password.getText().toString().trim().isEmpty()) {
                    l_password.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean validasi() {
        if (username.getText().toString().trim().isEmpty()) {
            l_username.setErrorEnabled(true);
            l_username.setError("Username tidak boleh kosong!");
            return false;
        }
        if (password.getText().toString().length() < 6) {
            l_password.setErrorEnabled(true);
            l_password.setError("Password tidak boleh kurang dari 6 karakter!");
            return false;
        } else if (password.getText().toString().trim().isEmpty()) {
            l_password.setErrorEnabled(true);
            l_password.setError("Password tidak boleh kosong!");
            return false;
        }
        return true;
    }
}