package com.exam.sid.aplicacion.remote;

import com.exam.sid.aplicacion.service.UserClient;

/**
 * Created by Elias Barrientos on 12/25/2017.
 */

 public class ApiUtils {

        private ApiUtils() {}

        public static final String BASE_URL = "https://dry-forest-40048.herokuapp.com/";
       //URL principal del restApp

        public static UserClient getAPIService() {

            return RetrofitClient.getClient(BASE_URL).create(UserClient.class);
        }
 }

