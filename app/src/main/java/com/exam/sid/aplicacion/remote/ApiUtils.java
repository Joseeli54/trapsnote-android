package com.exam.sid.aplicacion.remote;

public class ApiUtils { //en esta clase se agrega la url

    private ApiUtils() {}

    public static final String BASE_URL = "https://dry-forest-40048.herokuapp.com/";

    /* Url de la primera entrega https://quiet-basin-87095.herokuapp.com/usuarios */

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class); // se pasa a la clase RetrofitClient
    }

}
