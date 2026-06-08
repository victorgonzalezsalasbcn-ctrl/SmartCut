package com.example.smartcut;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static ApiService apiService = null;

    // La IP de mi máquina para que el móvil o el emulador vean el backend
    public static final String BASE_URL = "http://192.168.1.43:8080/";

    public static ApiService getApiService() {
        if (retrofit == null) {
            // Retrofit con GSON
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        if (apiService == null) {
            // Creo la instancia de la API una sola vez y la reutilizo
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
}