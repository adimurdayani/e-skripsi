package com.jepri.e_skripsi.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private CardView btn_back, btn_sign_in;
    private TextInputLayout l_username, l_password, l_nama, l_email, l_konf_pass;
    private EditText username, password, nama, email, konf_pass;
    private ProgressDialog dialog;
    private StringRequest userRegist;

    public static final Pattern PASSWORD_FORMAT = Pattern.compile("^" +
            "(?=.*[1-9])" + //harus menggunakan satu angka
            "(?=.*[a-z])" + //harus menggunakan abjad
            "(?=.*[A-Z])" + //harus menggunakan huruf kapital
            "(?=.*[@#$%^&+=])" + //harus menggunakan sepesial karakter
            "(?=\\S+$)" + // tidak menggunakan spasi
            ".{6,}" + //harus lebih dari 6 karakter
            "$"
    );
    private String t_nama, t_email, t_username, t_password, conf_pass;
    private ArrayList<DataUser> dataUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    public void init() {
        btn_back = findViewById(R.id.btn_kembali);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        l_nama = findViewById(R.id.l_nama);
        l_email = findViewById(R.id.l_email);
        l_username = findViewById(R.id.l_username);
        l_password = findViewById(R.id.l_password);
        l_konf_pass = findViewById(R.id.l_konf_pass);
        nama = findViewById(R.id.edt_nama);
        email = findViewById(R.id.edt_email);
        username = findViewById(R.id.edt_username);
        password = findViewById(R.id.edt_password);
        konf_pass = findViewById(R.id.edt_konf_pass);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), OnBoard.class));
                finish();
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validasi()) {
                    register();
                }
            }
        });
        cekValidasi();
    }

    public void getTextInput() {
        t_nama = nama.getText().toString().trim();
        t_email = email.getText().toString().trim();
        t_username = username.getText().toString().trim();
        t_password = password.getText().toString().trim();
        conf_pass = konf_pass.getText().toString().trim();
    }

    private void register() {
//        ambil data user dari model
        dataUsers = new ArrayList<>();
//        tampilkan progers bar loading
        dialog.setMessage("Register...");
        dialog.show();
//        membuat response API regsiter
        userRegist = new StringRequest(Request.Method.POST,
                ApiUrl.REGISTER, response -> {
//            ubah data ke json
            try {
                JSONObject object = new JSONObject(response);
//                jika data berhasil di simpan
                if (object.getBoolean("status")) {
//                    inisialisasi data yang akan di post
                    JSONObject data = object.getJSONObject("data");
                    DataUser postUser = new DataUser();
                    postUser.setNama(data.getString("nama"));
                    postUser.setUsername(data.getString("username"));
                    postUser.setEmail(data.getString("email"));
                    postUser.setPassword(data.getString("password"));
//                    arahkan pengguna ke halaman login
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
//                    display jika data berhasil tersimpan
                    Toast.makeText(getApplicationContext(), "Register success!", Toast.LENGTH_LONG).show();
                } else {
//                    display jika data tidak berhasil disimpan
                    Toast.makeText(getApplicationContext(), "Message: " + object.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        }, error -> {
            dialog.dismiss();
            error.printStackTrace();
            Toast.makeText(getApplicationContext(), "Terjadi masalah penginputan data!", Toast.LENGTH_SHORT).show();
        }) {
            //   atur parameter yang akan di input ke dalam database
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("nama", nama.getText().toString().trim());
                map.put("username", username.getText().toString().trim());
                map.put("email", email.getText().toString().trim());
                map.put("password", password.getText().toString().trim());
                return map;
            }
        };
        RequestQueue koneksi = Volley.newRequestQueue(getApplicationContext());
        koneksi.add(userRegist);
    }

    private void cekValidasi() {
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

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (email.getText().toString().isEmpty()) {
                    l_email.setErrorEnabled(false);
                } else if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
                    l_email.setErrorEnabled(false);
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

        konf_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (konf_pass.getText().toString().trim().isEmpty()) {
                    l_konf_pass.setErrorEnabled(false);
                } else if (PASSWORD_FORMAT.matcher(konf_pass.getText().toString().trim()).matches()) {
                    l_konf_pass.setErrorEnabled(false);
                } else if (konf_pass.getText().toString().trim().matches(password.getText().toString().trim())) {
                    l_konf_pass.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean validasi() {
        getTextInput();
        if (t_nama.isEmpty()) {
            l_nama.setErrorEnabled(true);
            l_nama.setError("Nama tidak boleh kosong!");
            return false;
        }

        if (t_username.isEmpty()) {
            l_username.setErrorEnabled(true);
            l_username.setError("Username tidak boleh kosong!");
            return false;
        }

        if (t_email.isEmpty()) {
            l_email.setErrorEnabled(true);
            l_email.setError("Email tidak boleh kosong!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(t_email).matches()) {
            l_email.setErrorEnabled(true);
            l_email.setError("Format email salah!");
            return false;
        }

        if (t_password.isEmpty()) {
            l_password.setErrorEnabled(true);
            l_password.setError("Password tidak boleh kosong!");
            return false;
        } else if (!PASSWORD_FORMAT.matcher(t_password).matches()) {
            l_password.setErrorEnabled(true);
            l_password.setError("Password sangat lemah!");
            return false;
        }

        if (conf_pass.isEmpty()) {
            l_konf_pass.setErrorEnabled(true);
            l_konf_pass.setError("Konfirmasi password tidak boleh kosong!");
            return false;
        } else if (!PASSWORD_FORMAT.matcher(conf_pass).matches()) {
            l_konf_pass.setErrorEnabled(true);
            l_konf_pass.setError("Konfirmasi password sangat lemah!");
            return false;
        } else if (!conf_pass.matches(t_password)) {
            l_konf_pass.setErrorEnabled(true);
            l_konf_pass.setError("Konfirmasi password tidak sama!");
            return false;
        }
        return true;
    }
}