package com.jepri.e_skripsi.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jepri.e_skripsi.R;
import com.jepri.e_skripsi.activity.Login;
import com.jepri.e_skripsi.activity.MenuUtama;
import com.jepri.e_skripsi.network.adapter.DataJudulAdapter;
import com.jepri.e_skripsi.network.adapter.SliderAdapter;
import com.jepri.e_skripsi.network.api.ApiUrl;
import com.jepri.e_skripsi.network.model.DataJudul;
import com.jepri.e_skripsi.network.model.DataKonsentrasi;
import com.jepri.e_skripsi.network.model.DataPembimbing;
import com.jepri.e_skripsi.network.model.DataTema;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class FragmentMenuUtama extends Fragment {
    View view;
    SliderView sliderView;
    TextView nama;
    SwipeRefreshLayout sw_data;
    public static RecyclerView rc_data;
    ShimmerFrameLayout frameLayout;
    SearchView search_data;
    int[] images = {
            R.drawable.slide1,
            R.drawable.slide2,
            R.drawable.slide3,
            R.drawable.slide4
    };
    SharedPreferences preferences;
    private RecyclerView.LayoutManager layoutManager;
    private DataJudulAdapter adapter;
    public static ArrayList<DataJudul> listjudul;
    private StringRequest getDataJudul;

    public FragmentMenuUtama() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_utama, container, false);
        init();
        return view;
    }

    public void init() {
        preferences = getContext().getSharedPreferences("user", MODE_PRIVATE);
        sliderView = view.findViewById(R.id.image_slide);
        nama = view.findViewById(R.id.nama);
        sw_data = view.findViewById(R.id.sw_data);
        rc_data = view.findViewById(R.id.rc_data);
        search_data = view.findViewById(R.id.search_data);
        frameLayout = view.findViewById(R.id.shimmer_data);

        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rc_data.setLayoutManager(layoutManager);
        rc_data.setHasFixedSize(true);

        nama.setText(preferences.getString("nama", ""));

        sw_data.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
//        aksi pencarian data
        search_data.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getSearchData().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getData() {
        listjudul = new ArrayList<>();
        getDataJudul = new StringRequest(Request.Method.GET, ApiUrl.GET_JUDUL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")) {
                    JSONArray data = new JSONArray(object.getString("data"));
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject getData = data.getJSONObject(i);

                        DataTema tema = new DataTema();
                        tema.setId_tema(getData.getInt("id_tema"));
                        tema.setTema(getData.getString("tema"));

                        DataKonsentrasi konsentrasi = new DataKonsentrasi();
                        konsentrasi.setId(getData.getInt("id"));
                        konsentrasi.setNama_kons(getData.getString("nama_kons"));

                        DataPembimbing pembimbing = new DataPembimbing();
                        pembimbing.setNama_pem(getData.getString("nama_pem"));

                        DataJudul getJudul = new DataJudul();
                        getJudul.setId(getData.getInt("id"));
                        getJudul.setTema(tema);
                        getJudul.setIs_active(getData.getString("is_active"));
                        getJudul.setNama(getData.getString("nama"));
                        getJudul.setJudul(getData.getString("judul"));
                        getJudul.setKelamin(getData.getString("kelamin"));
                        getJudul.setNo_hp(getData.getInt("no_hp"));
                        getJudul.setEmail(getData.getString("email"));
                        getJudul.setJurusan(getData.getString("jurusan"));
                        getJudul.setNama_kons(konsentrasi);
                        getJudul.setNama_pem(pembimbing);
                        getJudul.setKeterangan(getData.getString("keterangan"));
                        getJudul.setCreated_at(getData.getString("created_at"));
                        listjudul.add(getJudul);
                    }

                    adapter = new DataJudulAdapter(getContext(), listjudul);
                    rc_data.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Terjadi masalah!", Toast.LENGTH_SHORT).show();
                }
                //            sembunyikan animasi refresh
                frameLayout.stopShimmerAnimation();
                frameLayout.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            sembunyikan animasi refresh
            sw_data.setRefreshing(false);
            frameLayout.stopShimmerAnimation();
            frameLayout.setVisibility(View.GONE);
        }, error -> {
            //            menampilkan pesan error di logcat
            error.printStackTrace();
            //            menampilkan pesan error di android
            Toast.makeText(getContext(), "Periksa koneski anda!", Toast.LENGTH_LONG).show();
//            Intent i = new Intent(getContext(), Login.class);
//            startActivity(i);
//            (((MenuUtama) getContext())).finish();
            //            sembunyikan animasi refresh
            sw_data.setRefreshing(false);
            frameLayout.stopShimmerAnimation();
            frameLayout.setVisibility(View.GONE);
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//                mengambil data token dari sharedpreferences
                String token = preferences.getString("token", "");
//                inisialisasi token ke dalam authorization bearer
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + token);
                return map;
            }
        };
        //        mengkoneksikan API ke volley
        RequestQueue koneksi = Volley.newRequestQueue(requireContext());
        koneksi.add(getDataJudul);
    }

    @Override
    public void onResume() {
        super.onResume();
        sw_data.setRefreshing(true);
        frameLayout.startShimmerAnimation();
        getData();
    }

    @Override
    public void onPause() {
        sw_data.setRefreshing(false);
        frameLayout.stopShimmerAnimation();
        super.onPause();
    }
}
