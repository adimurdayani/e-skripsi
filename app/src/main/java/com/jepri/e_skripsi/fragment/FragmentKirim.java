package com.jepri.e_skripsi.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.android.material.textfield.TextInputLayout;
import com.jepri.e_skripsi.R;
import com.jepri.e_skripsi.activity.MenuUtama;
import com.jepri.e_skripsi.network.api.ApiUrl;
import com.jepri.e_skripsi.network.model.DataJudul;
import com.jepri.e_skripsi.network.model.DataKonsentrasi;
import com.jepri.e_skripsi.network.model.DataPembimbing;
import com.jepri.e_skripsi.network.model.DataTema;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentKirim extends Fragment {
    View view;
    EditText nim, nama2, email, no_hp, jurusan, judul, keterangan;
    Spinner konsentrasi, tema, dospem1, dospem2, kelamin;
    TextInputLayout l_nim, l_nama, l_email, l_no_hp, l_jurusan, l_judul, l_keterangan;
    TextView nama, id_konsentrasi, id_tema, id_pem_satu, id_pem_dua, j_kelamin,is_active;
    CardView btn_kirim;
    ImageView btn_kembali;
    SharedPreferences preferences;
    public String isi = "belum diterima";
    public static ArrayList<DataJudul> listjudul;
    public static ArrayList<DataKonsentrasi> listkonsentrasi;
    public static ArrayList<DataTema> listtema;
    public static ArrayList<DataPembimbing> listpembimbing;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> list2 = new ArrayList<String>();
    private ArrayList<String> list3 = new ArrayList<String>();
    private StringRequest kirimData, getkonsetrasi, gettema, getpembimbing;
    private ProgressDialog dialog;

    public FragmentKirim() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kirim, container, false);
        init();
        return view;
    }

    private void init() {
        preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        nim = view.findViewById(R.id.nim);
        nama2 = view.findViewById(R.id.nama2);
        email = view.findViewById(R.id.email);
        no_hp = view.findViewById(R.id.no_hp);
        jurusan = view.findViewById(R.id.jurusan);
        is_active = view.findViewById(R.id.is_active);
        judul = view.findViewById(R.id.judul);
        keterangan = view.findViewById(R.id.keterangan);
        konsentrasi = view.findViewById(R.id.konsentrasi);
        tema = view.findViewById(R.id.tema);
        dospem1 = view.findViewById(R.id.pem_satu);
        dospem2 = view.findViewById(R.id.pem_dua);
        l_nama = view.findViewById(R.id.l_nama);
        l_nim = view.findViewById(R.id.l_nim);
        l_no_hp = view.findViewById(R.id.l_no_hp);
        l_email = view.findViewById(R.id.l_email);
        l_jurusan = view.findViewById(R.id.l_jurusan);
        l_judul = view.findViewById(R.id.l_judul);
        l_keterangan = view.findViewById(R.id.l_keterangan);
        nama = view.findViewById(R.id.nama);
        id_konsentrasi = view.findViewById(R.id.id_konsentrasi);
        id_tema = view.findViewById(R.id.id_tema);
        id_pem_satu = view.findViewById(R.id.id_pem_satu);
        id_pem_dua = view.findViewById(R.id.id_pem_dua);
        btn_kirim = view.findViewById(R.id.btn_kirim);
        j_kelamin = view.findViewById(R.id.j_kelamin);
        kelamin = view.findViewById(R.id.kelamin);
        btn_kembali = view.findViewById(R.id.btn_kembali);

        dialog = new ProgressDialog(getContext());

        nama.setText(preferences.getString("nama", ""));

        konsentrasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_konsentrasi.setText("" + listkonsentrasi.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tema.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_tema.setText("" + listtema.get(position).getId_tema());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dospem1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_pem_satu.setText("" + listpembimbing.get(position).getId_pem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dospem2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_pem_dua.setText("" + listpembimbing.get(position).getId_pem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        kelamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                j_kelamin.setText(kelamin.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validasi()) {
                    kirim();
                }
            }
        });

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.frm_menu, new FragmentMenuUtama())
                        .commit();
            }
        });
    }

    private boolean validasi() {
        if (nim.getText().toString().trim().isEmpty()) {
            l_nim.setErrorEnabled(true);
            l_nim.setError("NIM tidak boleh kosong!");
            return false;
        }
        if (nama2.getText().toString().trim().isEmpty()) {
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
        if (judul.getText().toString().trim().isEmpty()) {
            l_judul.setErrorEnabled(true);
            l_judul.setError("Judul tidak boleh kosong!");
            return false;
        }
        if (keterangan.getText().toString().trim().isEmpty()) {
            l_keterangan.setErrorEnabled(true);
            l_keterangan.setError("Keterangan tidak boleh kosong!");
            return false;
        }
        if (no_hp.getText().toString().trim().isEmpty()) {
            l_no_hp.setErrorEnabled(true);
            l_no_hp.setError("Nomor hp tidak boleh kosong!");
            return false;
        }
        return true;
    }

    private void kirim() {
        listjudul = new ArrayList<>();
        dialog.setMessage("Mengirim");
        dialog.show();

        kirimData = new StringRequest(Request.Method.POST, ApiUrl.POST_JUDUL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")) {
                    JSONObject postObjek = object.getJSONObject("data");

                    DataJudul judul = new DataJudul();
                    judul.setId(postObjek.getInt("id"));
                    judul.setId_tema(postObjek.getInt("id_tema"));
                    judul.setIs_active(postObjek.getString("is_active"));
                    judul.setNim(postObjek.getInt("nim"));
                    judul.setNama(postObjek.getString("nama"));
                    judul.setNo_hp(postObjek.getInt("no_hp"));
                    judul.setEmail(postObjek.getString("email"));
                    judul.setKelamin(postObjek.getString("kelamin"));
                    judul.setJurusan(postObjek.getString("jurusan"));
                    judul.setKonsentrasi(postObjek.getInt("konsentrasi"));
                    judul.setJudul(postObjek.getString("judul"));
                    judul.setPem_satu(postObjek.getInt("pem_satu"));
                    judul.setPem_dua(postObjek.getInt("pem_dua"));
                    judul.setKeterangan(postObjek.getString("keterangan"));
                    judul.setCreated_at(postObjek.getString("created_at"));

                    FragmentMenuUtama.listjudul.add(0, judul);
                    FragmentMenuUtama.rc_data.getAdapter().notifyItemInserted(0);
                    FragmentMenuUtama.rc_data.getAdapter().notifyDataSetChanged();
                    (((MenuUtama) getContext())).finish();
                    Toast.makeText(getContext(), "Data Terkirim", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Data gagal terkirim!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        }, error -> {
            error.printStackTrace();
            dialog.dismiss();
            Toast.makeText(getContext(), "Terjadi Masalah Koneksi!", Toast.LENGTH_SHORT).show();
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
                map.put("id_tema", id_tema.getText().toString().trim());
                map.put("is_active", is_active.getText().toString().trim());
                map.put("nim", nim.getText().toString().trim());
                map.put("nama", nama2.getText().toString().trim());
                map.put("nama", nama2.getText().toString().trim());
                map.put("email", email.getText().toString().trim());
                map.put("no_hp", no_hp.getText().toString().trim());
                map.put("kelamin", j_kelamin.getText().toString().trim());
                map.put("jurusan", jurusan.getText().toString().trim());
                map.put("konsentrasi", id_konsentrasi.getText().toString().trim());
                map.put("judul", judul.getText().toString().trim());
                map.put("pem_satu", id_pem_dua.getText().toString().trim());
                map.put("pem_dua", id_pem_dua.getText().toString().trim());
                map.put("keterangan", keterangan.getText().toString().trim());
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(kirimData);
    }

    private void konsentrasi() {
        listkonsentrasi = new ArrayList<>();
        getkonsetrasi = new StringRequest(Request.Method.GET, ApiUrl.GET_KONSENTRASI, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")) {
                    JSONArray dataArray = new JSONArray(object.getString("data"));
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject getObjek = dataArray.getJSONObject(i);
                        DataKonsentrasi konsentrasi = new DataKonsentrasi();
                        konsentrasi.setId(getObjek.getInt("id"));
                        konsentrasi.setNama_kons(getObjek.getString("nama_kons"));
                        listkonsentrasi.add(konsentrasi);
                    }
                    for (int i = 0; i < listkonsentrasi.size(); i++) {
                        list.add(listkonsentrasi.get(i).getNama_kons().toString());
                    }
                    ArrayAdapter<String> getDataKonsentrasi = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_dropdown_item, list);
                    getDataKonsentrasi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    konsentrasi.setAdapter(getDataKonsentrasi);
                } else {
                    Toast.makeText(getContext(), "Terjadi masalah koneksi", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
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
        queue.add(getkonsetrasi);
    }

    private void tema() {
        listtema = new ArrayList<>();
        gettema = new StringRequest(Request.Method.GET, ApiUrl.GET_TEMA, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")) {
                    JSONArray dataArray = new JSONArray(object.getString("data"));
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject getObjek = dataArray.getJSONObject(i);
                        DataTema tema = new DataTema();
                        tema.setId_tema(getObjek.getInt("id_tema"));
                        tema.setTema(getObjek.getString("tema"));
                        listtema.add(tema);
                    }
                    for (int i = 0; i < listtema.size(); i++) {
                        list2.add(listtema.get(i).getTema().toString());
                    }
                    ArrayAdapter<String> getdatatema = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_dropdown_item, list2);
                    getdatatema.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tema.setAdapter(getdatatema);
                } else {
                    Toast.makeText(getContext(), "Terjadi masalah koneksi", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
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
        queue.add(gettema);
    }

    private void pembimbing() {
        listpembimbing = new ArrayList<>();
        getpembimbing = new StringRequest(Request.Method.GET, ApiUrl.GET_PEMBIMBING, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")) {
                    JSONArray dataArray = new JSONArray(object.getString("data"));
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject getObjek = dataArray.getJSONObject(i);
                        DataPembimbing pembimbing = new DataPembimbing();
                        pembimbing.setId_pem(getObjek.getInt("id_pem"));
                        pembimbing.setNama_pem(getObjek.getString("nama_pem"));
                        listpembimbing.add(pembimbing);
                    }
                    for (int i = 0; i < listpembimbing.size(); i++) {
                        list3.add(listpembimbing.get(i).getNama_pem().toString());
                    }
                    ArrayAdapter<String> getdatapembimbing = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_dropdown_item, list3);
                    getdatapembimbing.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dospem1.setAdapter(getdatapembimbing);
                    dospem2.setAdapter(getdatapembimbing);
                } else {
                    Toast.makeText(getContext(), "Terjadi masalah koneksi", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
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
        queue.add(getpembimbing);
    }

    @Override
    public void onResume() {
        super.onResume();
        konsentrasi();
        tema();
        pembimbing();
    }
}
