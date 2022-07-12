package com.jepri.e_skripsi.network.model;

public class DataJudul {
    private int id, id_tema, nim, no_hp, konsentrasi, pem_satu, pem_dua;
    private String nama, email, kelamin, jurusan, judul, keterangan, is_active, created_at;
    private DataTema tema;
    private DataKonsentrasi nama_kons;
    private DataPembimbing nama_pem;

    public int getPem_dua() {
        return pem_dua;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public void setPem_dua(int pem_dua) {
        this.pem_dua = pem_dua;
    }

    public DataTema getTema() {
        return tema;
    }

    public void setTema(DataTema tema) {
        this.tema = tema;
    }

    public DataKonsentrasi getNama_kons() {
        return nama_kons;
    }

    public void setNama_kons(DataKonsentrasi nama_kons) {
        this.nama_kons = nama_kons;
    }

    public DataPembimbing getNama_pem() {
        return nama_pem;
    }

    public void setNama_pem(DataPembimbing nama_pem) {
        this.nama_pem = nama_pem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_tema() {
        return id_tema;
    }

    public void setId_tema(int id_tema) {
        this.id_tema = id_tema;
    }

    public int getNim() {
        return nim;
    }

    public void setNim(int nim) {
        this.nim = nim;
    }

    public int getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(int no_hp) {
        this.no_hp = no_hp;
    }

    public int getKonsentrasi() {
        return konsentrasi;
    }

    public void setKonsentrasi(int konsentrasi) {
        this.konsentrasi = konsentrasi;
    }

    public int getPem_satu() {
        return pem_satu;
    }

    public void setPem_satu(int pem_satu) {
        this.pem_satu = pem_satu;
    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
