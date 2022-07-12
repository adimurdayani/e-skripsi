package com.jepri.e_skripsi.network.model;

public class DataTema {
    private int id_tema, id_konsentrasi;
    private String tema, created_at;

    public int getId_tema() {
        return id_tema;
    }

    public void setId_tema(int id_tema) {
        this.id_tema = id_tema;
    }

    public int getId_konsentrasi() {
        return id_konsentrasi;
    }

    public void setId_konsentrasi(int id_konsentrasi) {
        this.id_konsentrasi = id_konsentrasi;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
