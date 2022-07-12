package com.jepri.e_skripsi.network.model;

public class DataKonsentrasi {
    private int id;
    private String nama_kons, created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_kons() {
        return nama_kons;
    }

    public void setNama_kons(String nama_kons) {
        this.nama_kons = nama_kons;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
