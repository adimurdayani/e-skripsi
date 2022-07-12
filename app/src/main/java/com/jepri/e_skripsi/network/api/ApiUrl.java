package com.jepri.e_skripsi.network.api;

public class ApiUrl {
    //ip connection wifi 192.168.43.68 ip localhost 10.0.2.2
    public static final String BASE_URL = "http://e-skripsi.tokobibit.my.id/api/";
    public static final String LOGIN = BASE_URL + "auth/login";
    public static final String LOGOUT = BASE_URL + "auth/logout";
    public static final String REGISTER = BASE_URL + "auth/register";
    public static final String GET_JUDUL = BASE_URL + "pendaftaran";
    public static final String POST_JUDUL = BASE_URL + "pendaftaran/kirim";
    public static final String GET_TEMA = BASE_URL + "tema";
    public static final String GET_KONSENTRASI = BASE_URL + "konsentrasi";
    public static final String GET_PEMBIMBING = BASE_URL + "pembimbing";
    public static final String POST_USER = BASE_URL + "user/user";
    public static final String POST_PASSWORD = BASE_URL + "user/password";
}
