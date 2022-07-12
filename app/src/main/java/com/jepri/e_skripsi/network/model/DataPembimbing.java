package com.jepri.e_skripsi.network.model;

public class DataPembimbing {
    private int id_pem, nip;
    private String nama_pem, created_at;

    public int getId_pem() {
        return id_pem;
    }

    public void setId_pem(int id_pem) {
        this.id_pem = id_pem;
    }

    public int getNip() {
        return nip;
    }

    public void setNip(int nip) {
        this.nip = nip;
    }

    public String getNama_pem() {
        return nama_pem;
    }

    public void setNama_pem(String nama_pem) {
        this.nama_pem = nama_pem;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
