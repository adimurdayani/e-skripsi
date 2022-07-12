package com.jepri.e_skripsi.network.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jepri.e_skripsi.R;
import com.jepri.e_skripsi.activity.DetailJudul;
import com.jepri.e_skripsi.network.model.DataJudul;

import java.util.ArrayList;
import java.util.Collection;

public class DataJudulAdapter extends RecyclerView.Adapter<DataJudulAdapter.HolderView> {

    private Context context;
    private ArrayList<DataJudul> listJudul;

    public DataJudulAdapter(Context context, ArrayList<DataJudul> listJudul) {
        this.context = context;
        this.listJudul = listJudul;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_data, parent, false);
        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        DataJudul judul = listJudul.get(position);

        holder.id.setText(String.valueOf(judul.getId()));
        holder.id_tema.setText(judul.getTema().getTema());
        holder.nama.setText(judul.getNama());
        holder.judul.setText(judul.getJudul());
        holder.no_hp.setText(String.valueOf(judul.getNo_hp()));
        holder.email.setText(judul.getEmail());
        holder.kelamin.setText(judul.getKelamin());
        holder.jurusan.setText(judul.getJurusan());
        holder.konsentrasi.setText(judul.getNama_kons().getNama_kons());
        holder.pem_satu.setText(judul.getNama_pem().getNama_pem());
        holder.pem_dua.setText(judul.getNama_pem().getNama_pem());
        holder.is_active.setText(String.valueOf(judul.getIs_active()));
        holder.keterangan.setText(judul.getKeterangan());
        holder.tanggal.setText(judul.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return listJudul.size();
    }

    //    fungsi pencarian data
    Filter searchData = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<DataJudul> searchList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                searchList.addAll(listJudul);
            } else {
                for (DataJudul getjudul : listJudul) {
                    if (getjudul
                            .getNama()
                            .toLowerCase()
                            .contains(constraint
                                    .toString()
                                    .toLowerCase())
                            || getjudul
                            .getJudul()
                            .toLowerCase()
                            .contains(constraint
                                    .toString()
                                    .toLowerCase())) {
                        searchList.add(getjudul);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = searchList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listJudul.clear();
            listJudul.addAll((Collection<? extends DataJudul>) results.values);
            notifyDataSetChanged();
        }
    };

    public Filter getSearchData() {
        return searchData;
    }

    public class HolderView extends RecyclerView.ViewHolder {

        TextView id_tema,nama, judul, tanggal, id, keterangan, nim, email, kelamin, jurusan, konsentrasi, no_hp,
        pem_satu, pem_dua, is_active;

        public HolderView(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            nama = itemView.findViewById(R.id.nama);
            judul = itemView.findViewById(R.id.judul);
            tanggal = itemView.findViewById(R.id.tanggal);
            keterangan = itemView.findViewById(R.id.keterangan);
            nim = itemView.findViewById(R.id.nim);
            email = itemView.findViewById(R.id.email);
            kelamin = itemView.findViewById(R.id.kelamin);
            jurusan = itemView.findViewById(R.id.jurusan);
            konsentrasi = itemView.findViewById(R.id.konsentrasi);
            pem_satu = itemView.findViewById(R.id.pem_satu);
            pem_dua = itemView.findViewById(R.id.pem_dua);
            no_hp = itemView.findViewById(R.id.no_hp);
            is_active = itemView.findViewById(R.id.is_active);
            id_tema = itemView.findViewById(R.id.tema);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent getDetail = new Intent(context, DetailJudul.class);
                    getDetail.putExtra("id_tema", id_tema.getText().toString());
                    getDetail.putExtra("nim", nim.getText().toString());
                    getDetail.putExtra("nama", nama.getText().toString());
                    getDetail.putExtra("email", email.getText().toString());
                    getDetail.putExtra("no_hp", no_hp.getText().toString());
                    getDetail.putExtra("judul", judul.getText().toString());
                    getDetail.putExtra("kelamin", kelamin.getText().toString());
                    getDetail.putExtra("konsentrasi", konsentrasi.getText().toString());
                    getDetail.putExtra("jurusan", jurusan.getText().toString());
                    getDetail.putExtra("keterangan", keterangan.getText().toString());
                    getDetail.putExtra("tanggal", tanggal.getText().toString());
                    getDetail.putExtra("pem_satu", pem_satu.getText().toString());
                    getDetail.putExtra("pem_dua", pem_dua.getText().toString());
                    getDetail.putExtra("is_active", is_active.getText().toString());
                    context.startActivity(getDetail);
                }
            });
        }
    }
}
